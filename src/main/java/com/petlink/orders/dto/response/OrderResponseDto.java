package com.petlink.orders.dto.response;

import com.petlink.orders.dto.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderResponseDto {
    private Long orderNumber; // 주문번호
    private Long orderId;     // 주문아이디
    private Long fundingId;   // 펀딩번호
    private Long memberId;    // 회원번호
    private List<String> orderedRewards; // 주문한 리워드 제목들

    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ORDERED;

    private Boolean isAmountOpen;  // 금액공개여부
    private Boolean isNameOpen;   // 이름공개여부
}

