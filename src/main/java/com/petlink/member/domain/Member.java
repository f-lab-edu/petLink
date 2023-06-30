package com.petlink.member.domain;

import com.petlink.common.domain.base.BaseTimeEntity;
import com.petlink.member.exception.MemberException;
import jakarta.persistence.*;
import lombok.*;

import static com.petlink.member.exception.MemberExceptionCode.ALREADY_WITHDRAWAL_MEMBER;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "id")
    private Long id;

    @Column(unique = true, name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "tel", nullable = false)
    private String tel;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    public void withdrawal() {
        if (this.status.equals(MemberStatus.INACTIVE)) {
            throw new MemberException(ALREADY_WITHDRAWAL_MEMBER);
        }
        this.status = MemberStatus.INACTIVE;
    }
}
