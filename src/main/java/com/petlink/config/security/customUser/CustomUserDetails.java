package com.petlink.config.security.customUser;

import com.petlink.manager.domain.Manager;
import com.petlink.member.domain.Member;
import com.petlink.member.domain.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.petlink.common.util.jwt.JwtRole.MANAGER;
import static com.petlink.common.util.jwt.JwtRole.MEMBER;

@Builder
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Member member;
    private Manager manager;

    public Member getMember() {
        return this.member;
    }

    public Manager getManager() {
        return this.manager;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(MEMBER.getRole()));
        authorities.add(new SimpleGrantedAuthority(MANAGER.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return manager.getPassword();
    }

    @Override
    public String getUsername() {
        return manager.getEmail();
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
        MemberStatus status = member.getStatus();
        return status.equals(MemberStatus.ACTIVE);
    }
}

