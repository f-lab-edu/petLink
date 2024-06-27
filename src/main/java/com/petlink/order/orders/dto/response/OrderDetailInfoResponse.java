package com.petlink.order.orders.dto.response;

import com.petlink.order.orders.dto.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderDetailInfoResponse {
    private Long orderId;
    private String orderNumber;
    private Long fundingId;
    private Long memberId;
    private List<String> orderedRewards;
    private OrderStatus orderStatus;
    private Boolean isAmountOpen;
    private Boolean isNameOpen;
}

