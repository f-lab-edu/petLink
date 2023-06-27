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

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElse(null);
        Manager manager = managerRepository.findByEmail(email).orElse(null);

        if (member == null && manager == null) {
            throw new UsernameNotFoundException("Not found with email: " + email);
        }

        return CustomUserDetails.builder()
                .member(member)
                .manager(manager)
                .build();
    }
}
