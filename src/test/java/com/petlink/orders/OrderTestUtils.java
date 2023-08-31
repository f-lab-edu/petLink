package com.petlink.orders;

import com.petlink.common.domain.Address;
import com.petlink.orders.domain.PayMethod;
import com.petlink.orders.dto.request.FundingItemDto;
import com.petlink.orders.dto.request.OrderRequest;
import com.petlink.orders.dto.response.OrderResponseDto;

import java.util.List;

public class OrderTest�Helper {
    private final String ORDER_NUMBER = "20210701-0001";
    private final String PHONE = "01012345678";

    public OrderRequest getOrderRequest() {
        return OrderRequest.builder()
                .fundingId(1L)
                .payMethod(PayMethod.BANK_TRANSFER)
                .amountOpen(true)
                .nameOpen(true)
                .fundingItems(List.of(getFundingItemDto(1L, 1L), getFundingItemDto(2L, 1L)))
                .zipCode("12345")
                .address("서울시 강남구")
                .detailAddress("테헤란로 427")
                .recipient("홍길동")
                .phone("01012345678")
                .subPhone("01012345678")
                .build();
    }

    public OrderRequest getOrderRequest(Long memberId) {
        return OrderRequest.builder()
                .fundingId(1L)
                .memberId(memberId)
                .payMethod(PayMethod.BANK_TRANSFER)
                .amountOpen(true)
                .nameOpen(true)
                .fundingItems(List.of(getFundingItemDto(1L, 1L), getFundingItemDto(2L, 1L)))
                .zipCode("12345")
                .address("서울시 강남구")
                .detailAddress("테헤란로 427")
                .recipient("홍길동")
                .phone("01012345678")
                .subPhone("01012345678")
                .build();
    }

    public FundingItemDto getFundingItemDto(Long id, Long quantity) {
        return FundingItemDto.builder()
                .fundingItemId(id)
                .quantity(quantity)
                .build();
    }

    public OrderResponseDto getOrderResponseDto() {
        return OrderResponseDto.builder()
                .orderNumber(ORDER_NUMBER)
                .orderId(1L)
                .fundingId(1L)
                .recipientInfo(
                        OrderResponseDto.RecipientInfo.of("홍길동"
                                , Address.of("123456", "서울시", "관진구")
                                , PHONE, PHONE))
                .orderedRewards(List.of("리워드1", "리워드2", "리워드3", "리워드4"))
                .isAmountOpen(true)
                .isNameOpen(false)
                .build();
    }

}
