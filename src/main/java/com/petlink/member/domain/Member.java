package com.petlink.member.domain;

import com.petlink.common.domain.base.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true, name = "member_id")
	private Long id;

	@Column(unique = true, name = "member_email", nullable = false)
	private String email;

	@Column(name = "member_name", nullable = false)
	private String name;

	@Column(name = "member_password", nullable = false)
	private String password;

	@Column(name = "member_tel", nullable = false)
	private String tel;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberStatus status;
}
