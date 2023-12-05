package com.petlink.config.security.customUser;

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

        buildUserDetails(userDetailsBuilder, email);

        UserDetails userDetails = userDetailsBuilder.build();
        if (userDetails.getAuthorities().isEmpty()) {
            throw new UsernameNotFoundException("Not found with email: " + email);
        }

        return userDetails;
    }


    private void buildUserDetails(CustomUserDetails.CustomUserDetailsBuilder userDetailsBuilder, String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            userDetailsBuilder.user(member);
        } else {
            managerRepository.findByEmail(email)
                    .ifPresentOrElse(
                            manager -> userDetailsBuilder.user(manager),
                            () -> {
                                throw new UsernameNotFoundException("존재하지 않는 계정입니다.: " + email);
                            }
                    );
        }
    }

}
