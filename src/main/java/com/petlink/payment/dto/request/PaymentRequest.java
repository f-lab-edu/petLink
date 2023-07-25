package com.petlink.payment.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentRequest {
    private Long memberId; // 회원 번호
    private Long fundingId; // 펀딩번호
    private Long parcelId; // 택배 번호
    private Long paymentNumber; // 결제번호
    private String paymentMethod; // 결제수단
    private Boolean priceOpen; // 서포팅 금액 공개여부
    private Boolean nameOpen; // 서포터 이름 공개여부
    private String recipient; // 수령인 이름
    private String orderAddress; // 배송 주소
    private String orderDetailAddress; // 수령인 상세주소
    private String orderZipCode; // 수령인 우편번호
    private String orderMobilePhone; // 휴대전화1
    private String orderPhone; // 휴대전화2
    private String payDate; // 결제일시
}
