package com.petlink.orders.service;

import com.petlink.funding.item.service.ItemFacadeService;
import com.petlink.orders.domain.Orders;
import com.petlink.orders.dto.OrderStatus;
import com.petlink.orders.dto.request.FundingItemDto;
import com.petlink.orders.dto.request.OrderRequest;
import com.petlink.orders.dto.response.OrderDetailInfoResponse;
import com.petlink.orders.dto.response.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequest orderRequest) throws Exception;

    // 재고 감소 디폴트 메소드
    default void decreaseStock(List<FundingItemDto> items, ItemFacadeService itemFacadeService) throws Exception {
        itemFacadeService.decrease(items);
    }

    default OrderResponseDto buildCreateResponse(Orders orders, Long fundingId) {
        return OrderResponseDto.builder()
                .orderNumber(orders.getPaymentNumber())
                .orderId(orders.getId())
                .fundingId(fundingId)
                .orderStatus(OrderStatus.ORDERED)
                .recipientInfo(OrderResponseDto.RecipientInfo.of(orders.getRecipient(), orders.getAddress(), orders.getMobilePhone(), orders.getSubPhone()))
                .orderedRewards(orders.getFundingItemOrders().stream().map(fio -> fio.getFundingItem().getTitle()).toList())
                .isAmountOpen(orders.getPriceOpen())
                .isNameOpen(orders.getNameOpen())
                .build();
    }

    OrderDetailInfoResponse getOrderDetailInfo(Long id);

    default OrderDetailInfoResponse buildDetailResponse(Orders orders, List<String> itmeList) {
        return OrderDetailInfoResponse.builder()
                .orderId(orders.getId())
                .orderNumber(orders.getPaymentNumber())
                .fundingId(orders.getFunding().getId())
                .memberId(orders.getMember().getId())
                .orderStatus(orders.getOrderStatus())
                .orderedRewards(itmeList)
                .isAmountOpen(orders.getPriceOpen())
                .isNameOpen(orders.getNameOpen())
                .build();
    }

}