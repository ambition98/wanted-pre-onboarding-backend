package com.onboard.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class WantedAuthentication extends AbstractAuthenticationToken {
    private final Object principal;
    private final Object credentials;

    public WantedAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true);
        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() { return credentials; }

    @Override
    public Object getPrincipal() { return principal; }
}
