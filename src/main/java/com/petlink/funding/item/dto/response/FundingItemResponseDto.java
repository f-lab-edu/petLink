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
    //등록 성공 갯수 여부
    private Long failCount;
    //등록 실패 갯수 여부
    private Long successCount;
    //등록 실패한 아이템 목록
    private List<String> failList;
}