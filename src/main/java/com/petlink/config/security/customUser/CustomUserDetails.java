package com.petlink.config.security.customUser;

import com.petlink.manager.domain.Manager;
import com.petlink.member.domain.Member;
import com.petlink.member.domain.MemberStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.petlink.common.util.jwt.UserRole.MANAGER;
import static com.petlink.common.util.jwt.UserRole.MEMBER;

@Builder
@Getter
public class CustomUserDetails implements UserDetails {

    private Member member;
    private Manager manager;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    public CustomUserDetails(Manager manager) {
        this.manager = manager;
    }

    public CustomUserDetails(Member member, Manager manager) {
        this.member = member;
        this.manager = manager;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (manager != null) {
            authorities.add(new SimpleGrantedAuthority(MANAGER.getRole()));
        }
        if (member != null) {
            authorities.add(new SimpleGrantedAuthority(MEMBER.getRole()));
        }
        return authorities;
    }


    @Override
    public String getPassword() {
        if (manager != null) {
            return manager.getPassword();
        }
        if (member != null) {
            return member.getPassword();
        }
        throw new UsernameNotFoundException("No user found.");
    }

    @Override
    public String getUsername() {
        if (manager != null) {
            return manager.getEmail();
        }
        if (member != null) {
            return member.getEmail();
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
        if (member != null) {
            MemberStatus status = member.getStatus();
            return status.equals(MemberStatus.ACTIVE);
        }
        return manager != null;
    }
}

