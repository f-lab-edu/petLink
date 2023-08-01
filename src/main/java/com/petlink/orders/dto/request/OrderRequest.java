package com.petlink.orders.dto.request;

import com.petlink.orders.domain.PayMethod;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderRequest {
    private Long fundingId;
    private Long memberId;
    private PayMethod payMethod;
    private boolean amountOpen;
    private boolean nameOpen;
    private List<FundingItemDto> fundingItems;
    private String zipCode;
    private String address;
    private String detailAddress;
    private String recipient;
    private String phone;
    private String subPhone;

    @Getter
    @Builder
    private static class FundingItemDto {
        private Long fundingItemId;
        private Integer buyCount;
    }
}
