package com.petlink.orders.dto.request;

import com.petlink.orders.domain.PayMethod;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderRequest {

    @NotNull(message = "펀딩 ID는 필수 값입니다.")
    private Long fundingId;

    @NotNull(message = "결제 방법은 필수 값입니다.")
    private PayMethod payMethod;

    @Builder.Default
    private boolean amountOpen = false;

    @Builder.Default
    private boolean nameOpen = false;

    @NotEmpty(message = "펀딩 아이템은 최소 1개 이상이어야 합니다.")
    private List<FundingItemDto> fundingItems;
    @NotBlank(message = "우편 번호는 필수 값입니다.")
    private String zipCode;

    @NotBlank(message = "주소는 필수 값입니다.")
    private String address;

    @NotBlank(message = "상세 주소는 필수 값입니다.")
    private String detailAddress;

    @NotBlank(message = "수령인은 필수 값입니다.")
    private String recipient;

    @NotBlank(message = "전화번호는 필수 값입니다.")

    @Pattern(regexp = "^\\d{10,11}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phone;

    @Pattern(regexp = "^\\d{10,11}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String subPhone;

    @Getter
    @Builder
    private static class FundingItemDto {

        @NotNull(message = "펀딩 아이템 ID는 필수 값입니다.")
        private Long fundingItemId;

        @Min(value = 1, message = "구매 수량은 1 이상이어야 합니다.")
        private Integer buyCount;
    }
}
