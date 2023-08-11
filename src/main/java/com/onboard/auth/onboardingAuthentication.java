package com.onboard.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class onboardingAuthentication extends AbstractAuthenticationToken {
    private final Object principal;
    private final Object credentials;

    public onboardingAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
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
