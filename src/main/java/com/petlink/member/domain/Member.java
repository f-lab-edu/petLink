package com.petlink.member.domain;

import com.petlink.common.domain.Address;
import com.petlink.common.domain.base.BaseTimeEntity;
import com.petlink.member.exception.MemberException;
import com.petlink.orders.domain.Orders;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "member"
            , cascade = CascadeType.ALL // member가 삭제되면 orders도 삭제된다.
            , orphanRemoval = true      // member가 삭제되면 orders의 member를 null로 변경한다.
            , fetch = FetchType.LAZY    // member를 조회할 때 orders는 조회하지 않는다.
    )
    @Builder.Default
    private List<Orders> ordersList = new ArrayList<>();

    public void withdrawal() {
        if (this.status.equals(MemberStatus.INACTIVE)) {
            throw new MemberException(ALREADY_WITHDRAWAL_MEMBER);
        }
        this.status = MemberStatus.INACTIVE;
    }
}
