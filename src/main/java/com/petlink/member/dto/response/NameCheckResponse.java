package com.petlink.member.dto.response;

import lombok.Getter;

@Getter
public class NameCheckResponse {
	Boolean isOk;

	public NameCheckResponse() {
		this.isOk = Boolean.TRUE;
	}
}
