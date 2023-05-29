package com.petlink.funding.dto.response;

import lombok.Getter;

@Getter
public class FundingListDto {
	private final Long id;
	private final String title;
	private final String miniTitle;

	public FundingListDto(Long id, String title, String miniTitle) {
		this.id = id;
		this.title = title;
		this.miniTitle = miniTitle;
	}
}
