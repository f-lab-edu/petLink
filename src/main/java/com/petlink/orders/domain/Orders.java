package com.petlink.orders.domain;

import com.petlink.common.domain.Address;
import com.petlink.common.domain.base.BaseEntity;
import com.petlink.funding.domain.Funding;
import com.petlink.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //주문번호

    @Comment("펀딩")
    @ManyToOne
    @JoinColumn(name = "funding_id", nullable = false)
    private Funding funding;   //펀딩 정보

    @Comment("주문자")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Comment("결제번호")
    @Column(nullable = false)
    private String paymentNumber;

    @Comment("PG사 결제번호")
    @Column(nullable = false)
    private String pgPaymentNumber;

    @Comment("결제수단")
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @Comment("금액 공개 여부")
    private Boolean priceOpen;
    @Comment("이름 공개 여부")
    private Boolean nameOpen;

    @Comment("수령인")
    private String recipient;

    @Comment("배송지")
    @Embedded
    private Address address;

    @Comment("휴대폰번호_1")
    private String mobilePhone;
    @Comment("휴대폰번호_2")
    private String subPhone;

    @Comment("송장번호")
    private String parcelCode;
    @Comment("택배사")
    private String parcelCompany;

    @Builder.Default
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FundingItemOrder> fundingItemOrders = new ArrayList<>(); //주문 리워드 내역

}
