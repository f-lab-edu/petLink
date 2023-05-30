package com.petlink.funding.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FundingListDto {
	private final Long id;
	private final String title;
	private final String miniTitle;

	/** JPA 매핑을 위한 기본 생성자
	 */
	public FundingListDto(Long id, String title, String miniTitle) {
		this.id = id;
		this.title = title;
		this.miniTitle = miniTitle;
	}
}
