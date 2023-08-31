package com.petlink.orders.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petlink.orders.domain.PayMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class OrderRequest {

    @NotNull(message = "펀딩 ID는 필수 값입니다.")
    private Long fundingId;

    private Long memberId;

    @NotNull(message = "결제 방법은 필수 값입니다.")
    private PayMethod payMethod;

    private boolean amountOpen;

    private boolean nameOpen;

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

    @JsonIgnore
    public boolean isMemberIdExist() {
        return memberId != null;
    }
}
