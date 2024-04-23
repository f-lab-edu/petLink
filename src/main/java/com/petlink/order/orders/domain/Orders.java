package com.petlink.order.orders.domain;

import com.petlink.common.domain.Address;
import com.petlink.common.domain.base.BaseEntity;
import com.petlink.order.funding.domain.Funding;
import com.petlink.order.orders.dto.OrderStatus;
import com.petlink.user.member.domain.Member;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Long id;

    @Comment("펀딩")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id", nullable = false)
    private Funding funding;

    @Comment("주문자")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Comment("결제번호")
    @Column(nullable = false)
    private String paymentNumber;

    @Comment("결제수단")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

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

    public void addFundingItemOrder(FundingItemOrder itemOrder) {
        if (fundingItemOrders == null) {
            fundingItemOrders = new ArrayList<>();
        }

        if (itemOrder != null && !fundingItemOrders.contains(itemOrder)) {
            fundingItemOrders.add(itemOrder);
            // 이미 생성 시 'Orders' 객체가 'FundingItemOrder'에 설정되어 있으므로, 여기서는 설정하지 않음
        }
    }
}
