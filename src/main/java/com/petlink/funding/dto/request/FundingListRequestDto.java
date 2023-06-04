package com.petlink.funding.dto.request;

import java.util.List;

import com.petlink.funding.domain.FundingCategory;
import com.petlink.funding.domain.FundingState;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class FundingListRequestDto {

	@NotBlank(message = "시작일은 필수입니다.")
	@Pattern(regexp = "\\d{8}", message = "시작일은 yyyyMMdd 형식이어야 합니다.")
	private String startDate;

	@NotBlank(message = "종료일은 필수입니다.")
	@Pattern(regexp = "\\d{8}", message = "종료일은 yyyyMMdd 형식이어야 합니다.")
	private String endDate;

	private List<FundingCategory> category;
	private List<FundingState> state;
}
