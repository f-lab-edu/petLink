package com.petlink.member.domain;

import com.petlink.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String name;

    @Column(unique = true, name = "member_email")
    private String email;

    @Column(name = "member_password")
    private String password;

    @Column(name = "member_tel")
    private String tel;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;
}