package com.petlink.config.security.customUser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.petlink.member.domain.Member;
import com.petlink.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(memberEmail)
			.orElseThrow(
				() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
			);
		return CustomUserDetails.builder()
			.member(member)
			.build();

	}
}
