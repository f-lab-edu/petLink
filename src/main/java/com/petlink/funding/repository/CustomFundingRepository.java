package com.petlink.funding.repository;

import com.petlink.funding.domain.Funding;
import com.petlink.funding.dto.request.FundingSearchCriteriaDto;
import org.springframework.data.domain.Slice;

public interface CustomFundingRepository {

    Slice<Funding> findFundingList(FundingSearchCriteriaDto criteriaDto);
}
