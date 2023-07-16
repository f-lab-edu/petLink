package com.petlink.funding.item.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingItemResponseDto {
    private Long failCount;
    private Long successCount;
    private List<FailedItem> failList;

    @Getter
    @AllArgsConstructor
    public static class FailedItem {
        private String title;
        private String reason;
    }
}