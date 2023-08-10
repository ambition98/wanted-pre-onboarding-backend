package com.onboard.util;

import com.onboard.auth.UserRole;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt-config.key-string}")
    private String keyString;
    private final CookieUtil cookieUtil;
    private final Key key;
    private final JwtParser jwtParser;
    private final long accessTokenExpiryMs = 1_800_000L;
    private final long refreshTokenExpiryMs = 259_200_000L;
    private final String authoritiesKey = "role";
    private final String cookieName = "tk";

    @Autowired
    public JwtUtil(CookieUtil cookieUtil) {
        this.cookieUtil = cookieUtil;
        this.key = Keys.hmacShaKeyFor(keyString.getBytes(StandardCharsets.UTF_8));
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    private String generateToken(String id, String subject, UserRole role, long expiryMs) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .setId(id)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(new Date(now.getTime() + expiryMs))
                .claim(authoritiesKey, "Role_" + role)
                .compact();
    }

}
