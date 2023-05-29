package com.petlink.funding.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FundingCategory {
	FOOD("사료"),
	TOY("장난감"),
	CLOTHES("옷"),
	NUTRIENTS("약"),
	ETC("기타");

	private final String category;
}
