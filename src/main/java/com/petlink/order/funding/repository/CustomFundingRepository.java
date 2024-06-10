package com.petlink.order.funding.repository;

import com.petlink.order.funding.domain.Funding;
import com.petlink.order.funding.dto.request.FundingSearchCriteriaDto;
import org.springframework.data.domain.Slice;

public interface CustomFundingRepository {

    Slice<Funding> findFundingList(FundingSearchCriteriaDto criteriaDto);
}
