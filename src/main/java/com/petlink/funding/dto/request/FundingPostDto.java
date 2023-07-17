package com.petlink.funding.dto.request;

import com.petlink.funding.domain.FundingCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FundingPostDto {

    @NotBlank(message = "매니저 아이디가 없습니다.")
    private Long managerId;

    @NotBlank(message = "제목이 없습니다.")
    @Size(max = 100, message = "최대 100자까지 입력 가능합니다.")
    private String title;

    @NotBlank(message = "소 제목이 없습니다.")
    @Size(max = 50, message = "최대 50자까지 입력 가능합니다.")
    private String miniTitle;

    @NotBlank(message = "내용이 없습니다.")
    private String content;

    @NotBlank(message = "카테고리가 없습니다.")
    private FundingCategory category;

    @NotBlank(message = "시작일이 없습니다.")
    private LocalDateTime startDate;

    @NotBlank(message = "종료일이 없습니다.")
    private LocalDateTime endDate;

    @NotBlank
    @Min(value = 100000, message = "최소 100,000원 이상의 목표 금액을 설정해야 합니다.")
    private Long targetDonation;
}
