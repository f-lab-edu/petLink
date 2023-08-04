package com.petlink.funding.item.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FundingItemResponseDto {
    private Long failCount;
    private Long successCount;
    private List<FailedItem> failList;

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FailedItem {
        private String title;
        private String reason;

        public static FailedItem of(String title, String reason) {
            return new FailedItem(title, reason);
        }
    }
}