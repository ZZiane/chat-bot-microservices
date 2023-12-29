package org.mql.accountservice.service.impl.user.config;

import org.mql.accountservice.entities.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String email;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Account account) {
        this.email = account.getEmail();
        this.password= account.getPassword();
        List<GrantedAuthority> auths = List.of(
                new SimpleGrantedAuthority("ADMIN"),
                new SimpleGrantedAuthority("USER")
                );
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}