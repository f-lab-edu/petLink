package com.petlink.orders.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class FundingItemDto {
    @NotNull(message = "펀딩 아이템 ID는 필수 값입니다.")
    private Long fundingItemId;
    @Min(value = 1, message = "구매 수량은 1 이상이어야 합니다.")
    private Long quantity;
}
