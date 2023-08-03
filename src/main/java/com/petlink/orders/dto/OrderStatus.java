package com.petlink.orders.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatus {
    ORDERED("주문완료"),
    PAYMENT_COMPLETED("결제완료"),
    DELIVERY_PREPARE("배송준비중"),
    DELIVERY_COMPLETED("배송완료"),
    CANCEL("주문취소");
    private final String status;
}
