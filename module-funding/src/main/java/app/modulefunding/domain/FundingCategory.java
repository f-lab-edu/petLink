package app.modulefunding.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FundingCategory {

	FOOD("사료"),
	TOY("장난감"),
	CLOTHES("옷"),
	NUTRIENTS("영양제"),
	ETC("기타");

	private final String category;

}
