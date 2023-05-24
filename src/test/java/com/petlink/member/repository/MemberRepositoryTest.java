package com.petlink.member.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.petlink.member.domain.Address;
import com.petlink.member.domain.Member;
import com.petlink.member.domain.MemberStatus;

@DataJpaTest
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Test
	@DisplayName("회원 생성 테스트")
	void createMember() {
		// given
		Member member = Member.builder()
			.name("홍길동")
			.email("test@email.com")
			.password("1234")
			.tel("010-1234-5678")
			.status(MemberStatus.ACTIVE)
			.address(new Address("서울", "강남구", "12345"))
			.build();
		// when
		Member savedMember = memberRepository.save(member);
		// then
		assertEquals(member.getName(), savedMember.getName());
	}

	@Test
	@DisplayName("회원 생성 실패 테스트")
	void createMemberFailure() {
		// given
		Member member = Member.builder()
			.name("홍길동")
			.password("1234")
			.tel("010-1234-5678")
			.status(MemberStatus.ACTIVE)
			.address(new Address("서울", "강남구", "12345"))
			.build();
		// when
		assertThrows(Exception.class, () -> {
			memberRepository.save(member); // email이 null이므로 실패
		});
	}

	@Test
	@DisplayName("회원 이름 중복 체크 테스트")
	void checkName() {
		// given
		Member member = Member.builder()
			.name("홍길동")
			.email("test@email.com")
			.password("1234")
			.tel("010-1234-5678")
			.status(MemberStatus.ACTIVE)
			.address(new Address("서울", "강남구", "12345"))
			.build();
		memberRepository.save(member);
		// when
		Boolean exists = memberRepository.existsByName("홍길동");
		// then
		assertTrue(exists);
	}
}
