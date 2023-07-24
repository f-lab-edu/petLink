package com.petlink.funding.item.dto.request;

import com.petlink.funding.domain.Funding;
import com.petlink.funding.item.domain.FundingItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {

    @NotNull(message = "리워드 제목은 필수입니다.")
    @Size(min = 1, max = 100, message = "리워드 제목은 1자 이상 100자 이하입니다.")
    private String title;

    @NotNull(message = "리워드 내용은 필수입니다.")
    @Size(min = 1, message = "리워드 내용은 1자 이상입니다.")
    private String content;

    @NotNull(message = "재고는 필수입니다.")
    @Min(value = 1, message = "재고는 1개 이상이어야 합니다.")
    private Long stock;

    @NotNull(message = "최대 구매 수량은 필수입니다.")
    @Min(value = 1, message = "최대 구매 수량은 1개 이상이어야 합니다.")
    private Long maxBuyCount;

    public FundingItem toEntity(Funding funding) {
        return FundingItem.builder()
                .funding(funding)
                .title(this.title)
                .content(this.content)
                .stock(this.stock)
                .maxBuyCount(this.maxBuyCount)
                .build();
    }
}
