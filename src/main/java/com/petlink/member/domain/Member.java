package com.petlink.member.domain;

import com.petlink.common.domain.Address;
import com.petlink.common.domain.user.BaseUser;
import com.petlink.member.exception.MemberException;
import com.petlink.orders.domain.Orders;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.petlink.member.exception.MemberExceptionCode.ALREADY_WITHDRAWAL_MEMBER;

@Getter
@Entity
@Table(name = "member")
public class Member extends BaseUser {

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

    private List<Orders> ordersList = new ArrayList<>();

    public void withdrawal() {
        if (this.status.equals(MemberStatus.INACTIVE)) {
            throw new MemberException(ALREADY_WITHDRAWAL_MEMBER);
        }
        this.status = MemberStatus.INACTIVE;
    }

    protected Member() {
    }

    private Member(MemberBuilder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.name = builder.name;
        this.password = builder.password;
        this.tel = builder.tel;
        this.address = builder.address;
        this.status = builder.status;
    }

    // 빌더 인스턴스 생성 메소드
    public static MemberBuilder builder() {
        return new MemberBuilder();
    }

    // MemberBuilder 정의
    public static class MemberBuilder {
        // BaseUser 및 Member 필드
        protected Long id;
        protected String email;
        protected String name;
        private String password;
        private String tel;
        private Address address;
        private MemberStatus status;

        // 필드 설정 메소드들
        public MemberBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MemberBuilder email(String email) {
            this.email = email;
            return this;
        }

        public MemberBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MemberBuilder password(String password) {
            this.password = password;
            return this;
        }

        public MemberBuilder tel(String tel) {
            this.tel = tel;
            return this;
        }

        public MemberBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public MemberBuilder status(MemberStatus status) {
            this.status = status;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }
}
