package com.onboard.util;

import com.onboard.auth.JwtState;
import com.onboard.auth.UserRole;
import com.onboard.auth.onboardingAuthentication;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

// jwt 생성, 조회, HttpServletResponse 에 쿠키 저장, Authentication 발급 등
// 역할이 너무 많아서 클래스 분할이 필요한 것 같다. (SOLID - SRP)
@Component
@Slf4j
public class JwtUtil {

    private final CookieUtil cookieUtil;
    private final Key key;
    private final JwtParser jwtParser;
//    private final long accessTokenExpiryMs = 30 * 60 * 1000; // 30분
    private final long accessTokenExpiryMs = 30 * 1000; // 30초, 디버그용
    private final String authoritiesKey = "role";
    private final String cookieName = "tk";

    @Autowired
    public JwtUtil(CookieUtil cookieUtil) {
         /*
             JWT signature 에 사용하는 key 를 랜덤 생성
             따라서 서비스가 재시작되면 지금까지 발급된 JWT 는 Invalid 된다.
             분산 시스템이 아니기에 가능한 방식이다.
             분산 시스템에서 인증이 가능하다는 JWT 의 이점을 이용하지 못하는 좋지 못한 방식
         */
        String keyString = generateRandomString();
//        log.info("JWT keyString: " + keyString);
        this.cookieUtil = cookieUtil;
        this.key = Keys.hmacShaKeyFor(keyString.getBytes(StandardCharsets.UTF_8));
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String makeUserToken(String id) {
        return generateToken(id, "Access Token", UserRole.USER, accessTokenExpiryMs);
    }

    public void setTokenToCookie(HttpServletResponse resp, String id) {
        cookieUtil.setCookie(resp, cookieName, makeUserToken(id));
    }

    public String getTokenFromCookie(HttpServletRequest req) {
        Cookie cookie = cookieUtil.getCookie(req, cookieName);
        if (cookie == null) return null;

        return cookie.getValue();
    }

    public Authentication makeAuthentication(String token) {
        try {
            if (token == null || token.isEmpty()) {
                throw new Exception(); // Go to catch()
            }

            Claims claims = getTokenClaim(token);
            List<GrantedAuthority> authorities
                    = Collections.singletonList(new SimpleGrantedAuthority(claims.get(authoritiesKey).toString()));
            Object id = claims.getId();
            return new onboardingAuthentication(id, token, authorities);
        } catch (Exception e) {
            return new AnonymousAuthenticationToken(
                    UUID.randomUUID().toString(), "anonymousUser",
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))
            );
        }
    }

    public String getId(String token) {
        try {
            return getTokenClaim(token).getId();
        } catch (Exception e) {
            return null;
        }
    }

    public JwtState getState(String token) {
        if (token == null || token.isEmpty()) return JwtState.INVALID;

        try {
            getTokenClaim(token);
            return JwtState.OK;
        } catch (ExpiredJwtException e) {
            return JwtState.EXPIRED; // Refresh 필요
        } catch (Exception e) {
            return JwtState.INVALID; // 재발급 필요
        }
    }

    private String generateToken(String id, String subject, UserRole role, long expiryMs) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .setId(id)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(new Date(now.getTime() + expiryMs))
                .claim(authoritiesKey, "ROLE_" + role.getKey())
                .compact();
    }

    /*
        Throws:
        UnsupportedJwtException – if the claimsJws argument does not represent an Claims JWS
        MalformedJwtException – if the claimsJws string is not a valid JWS
        SignatureException – if the claimsJws JWS signature validation fails
        ExpiredJwtException – if the specified JWT is a Claims JWT and the Claims has an expiration time before the time this method is invoked.
        IllegalArgumentException – if the claimsJws string is null or empty or only whitespace
    */
    private Claims getTokenClaim(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    private String generateRandomString() {
        char[] chars = new char[32];
        byte min = 97;
        byte max = 122;

        for (int i=0; i<chars.length; i++) {
            chars[i] = (char) ((Math.random() * (max - min)) + min);
        }

        return new String(chars);
    }
}
