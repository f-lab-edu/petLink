package com.petlink.orders.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.petlink.RestDocsSupport;
import com.petlink.orders.OrderTestUtils;
import com.petlink.orders.service.MemberOrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderQueryControllerTest extends RestDocsSupport {
    private final OrderTestUtils testUtils = new OrderTestUtils();
    @InjectMocks
    private OrderQueryController OOC;
    @Mock
    private MemberOrderService memberOrderService;

    @Override
    protected Object initController() {
        return new OrderQueryController(memberOrderService);
    }

    @Test
    @DisplayName("회원은 결제 정보를 조회 할 수 있다.")
    void getOrderInfo() throws Exception {
        // given
        var res = testUtils.getOrderDetailInfoResponse();
        // when
        when(memberOrderService.getOrderDetailInfo(any(Long.class))).thenReturn(res);
        // then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/orders/query/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentationWrapper.document("orders/query/{id}"
                                , preprocessRequest(prettyPrint())
                                , preprocessResponse(prettyPrint())
                                , pathParameters(parameterWithName("id").description("결제 정보를 조회할 주문 ID"))
                                , responseFields(fieldWithPath("orderId").description("주문 ID")
                                        , fieldWithPath("orderNumber").description("주문 번호")
                                        , fieldWithPath("fundingId").description("펀딩 ID")
                                        , fieldWithPath("memberId").description("회원 ID")
                                        , fieldWithPath("orderedRewards").description("주문한 리워드 목록")
                                        , fieldWithPath("orderStatus").description("주문 상태")
                                        , fieldWithPath("isAmountOpen").description("금액 공개 여부")
                                        , fieldWithPath("isNameOpen").description("이름 공개 여부"))
                        ));
    }
}