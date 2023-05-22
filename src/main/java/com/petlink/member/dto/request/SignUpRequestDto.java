package com.petlink.member.dto.request;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.petlink.member.domain.Address;
import com.petlink.member.domain.Member;
import com.petlink.member.domain.MemberStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {

	@NotBlank(message = "닉네임은 필수입니다.")
	@Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 설정해주세요.")
	@Pattern(regexp = "^[a-zA-Z가-힣0-9]*$", message = "닉네임은 영문, 한글, 숫자만 가능합니다.")
	private String name;

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수입니다.")
	private String password;

	@NotBlank(message = "전화번호는 필수입니다.")
	@Pattern(regexp = "^[0-9]*$", message = "전화번호는 숫자만 가능합니다.")
	private String tel;

	@Size(max = 5, message = "우편번호는 최대 5자입니다.")
	private String zipCode;

	@Size(max = 50, message = "주소는 최대 50자입니다.")
	private String address;

	@Size(max = 100, message = "상세주소는 최대 100자입니다.")
	private String detailAddress;

	public Member toEntity() {
		Address addressInfo = new Address(zipCode, address, detailAddress);
		return Member.builder()
			.name(name)
			.email(email)
			.password(password)
			.tel(tel)
			.address(addressInfo)
			.status(MemberStatus.ACTIVE)
			.build();
	}

	public void encodingPassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(password);
	}
}
