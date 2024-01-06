package com.petlink.orders.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FundingItemOrderKey implements Serializable {
    private Long ordersId;
    private Long fundingItemId;
}
