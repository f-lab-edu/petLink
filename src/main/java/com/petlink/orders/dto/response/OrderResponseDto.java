package com.petlink.orders.dto.response;

import com.petlink.common.domain.Address;
import com.petlink.orders.dto.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderResponseDto {
    private String orderNumber; // 주문번호
    private Long orderId;     // 주문아이디
    private Long fundingId;   // 펀딩번호
    private RecipientInfo recipientInfo;    // 주문자 정보
    private List<String> orderedRewards; // 주문한 리워드 제목들

    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ORDERED;

    private Boolean isAmountOpen;  // 금액공개여부
    private Boolean isNameOpen;   // 이름공개여부


    // RecipientInfo 내부 클래스
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class RecipientInfo {
        private String name;    // 수령인
        private Address address; // 주소
        private String phone;   // 전화번호
        private String subPhone;    // 보조전화번호

        public static RecipientInfo of(String name, Address address, String phone, String subPhone) {
            return RecipientInfo.builder()
                    .name(name)
                    .address(address)
                    .phone(phone)
                    .subPhone(subPhone)
                    .build();
        }
    }

}

