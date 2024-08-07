package com.petlink.api.storage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ResultObject {
    String objectName;
    String imageLink;
}
