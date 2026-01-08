package com.example.excel.config;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.excel.model.User;




public class UserPrincipal implements UserDetails {
    private final String username;
    private final String password;

    public UserPrincipal(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.emptyList(); }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
