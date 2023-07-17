package com.petlink.config.security.customUser;

import com.petlink.manager.domain.Manager;
import com.petlink.manager.repository.ManagerRepository;
import com.petlink.member.domain.Member;
import com.petlink.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null || email.isBlank()) {
            throw new UsernameNotFoundException("Not found with email: " + email);
        }

        CustomUserDetails.CustomUserDetailsBuilder userDetailsBuilder = CustomUserDetails.builder();

        if (email.endsWith("@petlink.co.kr")) {
            buildManagerUserDetails(userDetailsBuilder, email);
        } else {
            buildMemberUserDetails(userDetailsBuilder, email);
        }

        UserDetails userDetails = userDetailsBuilder.build();
        if (userDetails.getAuthorities().isEmpty()) {
            throw new UsernameNotFoundException("Not found with email: " + email);
        }

        return userDetails;
    }

    private void buildManagerUserDetails(CustomUserDetails.CustomUserDetailsBuilder userDetailsBuilder, String email) {
        Optional<Manager> optionalManager = managerRepository.findByEmail(email);
        optionalManager.ifPresent(userDetailsBuilder::manager);
    }

    private void buildMemberUserDetails(CustomUserDetails.CustomUserDetailsBuilder userDetailsBuilder, String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        optionalMember.ifPresent(userDetailsBuilder::member);
    }

}
