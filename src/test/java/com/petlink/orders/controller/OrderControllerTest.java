package com.petlink.orders.controller;

import com.petlink.RestDocsSupport;
import com.petlink.common.domain.Address;
import com.petlink.orders.domain.PayMethod;
import com.petlink.orders.dto.request.OrderRequest;
import com.petlink.orders.dto.response.OrderResponseDto;
import com.petlink.orders.service.GuestOrderService;
import com.petlink.orders.service.MemberOrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest extends RestDocsSupport {
    private final String ORDER_NUMBER = "20210701-0001";
    private final String PHONE = "01012345678";
    @InjectMocks
    private OrderController orderController;
    @Mock
    private GuestOrderService guestOrderService;
    @Mock
    private MemberOrderService memberOrderService;

    private static OrderRequest get_build_order_request() {
        return OrderRequest.builder()
                .fundingId(1L)
                .payMethod(PayMethod.BANK_TRANSFER)
                .amountOpen(true)
                .nameOpen(true)
                .zipCode("12345")
                .address("서울시 강남구")
                .detailAddress("테헤란로 427")
                .recipient("홍길동")
                .phone("01012345678")
                .subPhone("01012345678")
                .build();
    }

    @Override
    protected Object initController() {
        return new OrderController(guestOrderService, memberOrderService);
    }

    @Test
    @DisplayName("비회원은 주문을 할 수 있다.")
    void createOrderByGuest() throws Exception {
        // given
        OrderRequest request = get_build_order_request();
        OrderResponseDto response = OrderResponseDto.builder()
                .orderNumber(ORDER_NUMBER)
                .orderId(1L)
                .fundingId(1L)
                .recipientInfo(OrderResponseDto.RecipientInfo.of("홍길동", Address.of("123456", "", ""), PHONE))
                .orderedRewards(List.of("리워드1", "리워드2", "리워드3", "리워드4"))
                .isAmountOpen(true)
                .isNameOpen(false)
                .build();
        // when
        when(guestOrderService.createOrder(any(OrderRequest.class))).thenReturn(any(OrderResponseDto.class));

        // then
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }


}