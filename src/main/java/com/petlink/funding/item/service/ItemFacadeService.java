package com.petlink.funding.item.service;


import com.petlink.orders.dto.request.FundingItemDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemFacadeService {

    public Boolean decrease(List<FundingItemDto> fundingItems) {
        // todo 이 부분 에서 동시성 제어 필요
        //item.decrease(quantity);
        return true;
    }
}
