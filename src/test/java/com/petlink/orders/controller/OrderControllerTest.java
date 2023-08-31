package com.petlink.orders.controller;

import com.petlink.RestDocsSupport;
import com.petlink.orders.OrderTestUtils;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest extends RestDocsSupport {

    @InjectMocks
    private OrderController orderController;
    @Mock
    private GuestOrderService guestOrderService;
    @Mock
    private MemberOrderService memberOrderService;

    private OrderTestUtils testUtils = new OrderTestUtils();

    @Override
    protected Object initController() {
        return new OrderController(guestOrderService, memberOrderService);
    }

    @Test
    @DisplayName("비회원은 주문을 할 수 있다.")
    void createOrderByGuest() throws Exception {
        // given
        OrderRequest request = testUtils.getOrderRequest();
        OrderResponseDto response = testUtils.getOrderResponseDto();

        // when
        when(guestOrderService.createOrder(any(OrderRequest.class))).thenReturn(response);

        // then
        mockMvc.perform(post("/orders/guest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())

                .andDo(
                        document("orders/create-by-guest",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("fundingId").description("펀딩 ID"),
                                        fieldWithPath("memberId").description("회원 ID (비회원 주문시 NULL)"),
                                        fieldWithPath("payMethod").description("결제 방법"),
                                        fieldWithPath("amountOpen").description("금액 공개 여부"),
                                        fieldWithPath("nameOpen").description("이름 공개 여부"),
                                        fieldWithPath("fundingItems[]").description("펀딩 아이템"),
                                        fieldWithPath("fundingItems[].fundingItemId").description("펀딩 아이템 ID"),
                                        fieldWithPath("fundingItems[].quantity").description("펀딩 아이템 구매 수량"),
                                        fieldWithPath("zipCode").description("우편 번호"),
                                        fieldWithPath("address").description("주소"),
                                        fieldWithPath("detailAddress").description("상세 주소"),
                                        fieldWithPath("recipient").description("수령인"),
                                        fieldWithPath("phone").description("전화번호"),
                                        fieldWithPath("subPhone").description("보조 전화번호")
                                ),
                                responseFields(
                                        fieldWithPath("orderNumber").description("주문 번호"),
                                        fieldWithPath("orderId").description("주문 ID"),
                                        fieldWithPath("fundingId").description("펀딩 ID"),
                                        fieldWithPath("orderStatus").description("주문 상태"),
                                        fieldWithPath("recipientInfo").description("수령인 정보"),
                                        fieldWithPath("recipientInfo.name").description("수령인 이름"),
                                        fieldWithPath("recipientInfo.address").description("수령인 주소 정보"),
                                        fieldWithPath("recipientInfo.address.addressInfo").description("수령인 기본 주소"),
                                        fieldWithPath("recipientInfo.address.detailAddress").description("수령인 상세 주소"),
                                        fieldWithPath("recipientInfo.address.zipCode").description("수령인 우편번호"),
                                        fieldWithPath("recipientInfo.phone").description("수령인 전화번호"),
                                        fieldWithPath("recipientInfo.subPhone").description("수령인 보조 전화번호"),
                                        fieldWithPath("orderedRewards[]").description("주문한 리워드 제목들"),
                                        fieldWithPath("isAmountOpen").description("금액 공개 여부"),
                                        fieldWithPath("isNameOpen").description("이름 공개 여부")
                                )
                        )
                );

    }

    @Test
    @DisplayName("회원은 주문을 할 수 있다.")
    void createOrderByMember() throws Exception {

        // given
        OrderRequest request = testUtils.getOrderRequest(1L);

        OrderResponseDto response = testUtils.getOrderResponseDto();

        // when
        when(memberOrderService.createOrder(any(OrderRequest.class))).thenReturn(response);

        // then
        mockMvc.perform(post("/orders/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())

                .andDo(
                        document("orders/create-by-member",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("fundingId").description("펀딩 ID"),
                                        fieldWithPath("memberId").description("회원 ID (비회원 주문시 NULL)"),
                                        fieldWithPath("payMethod").description("결제 방법"),
                                        fieldWithPath("amountOpen").description("금액 공개 여부"),
                                        fieldWithPath("nameOpen").description("이름 공개 여부"),
                                        fieldWithPath("fundingItems[]").description("펀딩 아이템"),
                                        fieldWithPath("fundingItems[].fundingItemId").description("펀딩 아이템 ID"),
                                        fieldWithPath("fundingItems[].quantity").description("펀딩 아이템 구매 수량"),
                                        fieldWithPath("zipCode").description("우편 번호"),
                                        fieldWithPath("address").description("주소"),
                                        fieldWithPath("detailAddress").description("상세 주소"),
                                        fieldWithPath("recipient").description("수령인"),
                                        fieldWithPath("phone").description("전화번호"),
                                        fieldWithPath("subPhone").description("보조 전화번호")
                                ),
                                responseFields(
                                        fieldWithPath("orderNumber").description("주문 번호"),
                                        fieldWithPath("orderId").description("주문 ID"),
                                        fieldWithPath("fundingId").description("펀딩 ID"),
                                        fieldWithPath("orderStatus").description("주문 상태"),
                                        fieldWithPath("recipientInfo").description("수령인 정보"),
                                        fieldWithPath("recipientInfo.name").description("수령인 이름"),
                                        fieldWithPath("recipientInfo.address").description("수령인 주소 정보"),
                                        fieldWithPath("recipientInfo.address.addressInfo").description("수령인 기본 주소"),
                                        fieldWithPath("recipientInfo.address.detailAddress").description("수령인 상세 주소"),
                                        fieldWithPath("recipientInfo.address.zipCode").description("수령인 우편번호"),
                                        fieldWithPath("recipientInfo.phone").description("수령인 전화번호"),
                                        fieldWithPath("recipientInfo.subPhone").description("수령인 보조 전화번호"),
                                        fieldWithPath("orderedRewards[]").description("주문한 리워드 제목들"),
                                        fieldWithPath("isAmountOpen").description("금액 공개 여부"),
                                        fieldWithPath("isNameOpen").description("이름 공개 여부")
                                )
                        )
                );
    }

}