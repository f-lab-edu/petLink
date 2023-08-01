package com.petlink.order.domain;

import com.petlink.common.domain.Address;
import com.petlink.common.domain.base.BaseTimeEntity;
import com.petlink.funding.domain.Funding;
import com.petlink.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Orders extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //주문번호

    @ManyToOne
    @JoinColumn(name = "funding_id", nullable = false)
    private Funding funding;   //펀딩 정보

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; //주문자 정보 (비회원일 경우 null)

    @Builder.Default
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FundingItemOrder> fundingItemOrders = new ArrayList<>(); //주문 리워드 내역
    private String paymentNumber;   //결제번호

    private String parcelCode;  //송장코드
    private String parcelCompany;   //택배사

    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;   //결제수단
    private Boolean priceOpen;  //금액공개여부
    private Boolean nameOpen;    //이름공개여부
    private String recipient;   //수령인

    @Embedded
    private Address address;    //주소
    private String mobilePhone; //휴대폰번호_1
    private String subPhone;    //휴대폰번호_2
}
