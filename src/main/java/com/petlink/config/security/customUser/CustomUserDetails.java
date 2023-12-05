package com.petlink.config.security.customUser;

import com.petlink.common.domain.user.BaseUser;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Getter
public class CustomUserDetails implements UserDetails {

    private BaseUser user;

    public CustomUserDetails(BaseUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user != null) {
            authorities.add(new SimpleGrantedAuthority(user.getRole().getRole()));
        }
        return authorities;
    }


    @Override
    public String getPassword() {
        throw new UsernameNotFoundException("No user found.");
    }

    @Override
    public String getUsername() {
        if (user != null) {
            return user.getEmail();
        }
        throw new UsernameNotFoundException("No user found.");
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

