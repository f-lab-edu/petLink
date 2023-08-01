package com.petlink.orders.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderRequest {
    private Long memberId;                  // 회원 번호
    private Long fundingId;                 // 펀딩번호
    private Boolean priceOpen;              // 서포팅 금액 공개여부
    private Boolean nameOpen;               // 서포터 이름 공개여부
    private String recipient;               // 수령인 이름
    private String orderZipCode;            // 수령인 우편번호
    private String orderAddress;            // 배송 주소
    private String orderDetailAddress;      // 수령인 상세주소
    private String orderMobilePhone;        // 휴대전화1
    private String orderPhone;              // 휴대전화2
}
