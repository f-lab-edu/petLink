package com.petlink.order.domain;

import com.petlink.common.domain.base.BaseTimeEntity;
import com.petlink.member.domain.Member;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //하나의 멤버는 여러 주문 내역을 가질 수 있다.
    @JoinColumn(name = "member_id")
    //주문자 정보가 없을 수도 있다.
    private Member member;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FundingItemOrder> fundingItemOrders = new ArrayList<>();
}


// member_id
// funding_id
// parcel_id
// payment_number
// payment_method
// price_opne
// name_open
// recipient
// order_address
// order_detail_adress
// order_zip_code
// order_mobile_phone
// order_phone
// pay_date
// created_date
// last_modified_date