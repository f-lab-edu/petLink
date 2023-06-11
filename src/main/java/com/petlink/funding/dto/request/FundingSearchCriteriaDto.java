package com.petlink.funding.dto.request;

import com.petlink.funding.domain.FundingCategory;
import com.petlink.funding.domain.FundingState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.petlink.common.util.date.DateConverter.toLocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingSearchCriteriaDto {
    LocalDateTime startDate;
    LocalDateTime endDate;
    List<FundingCategory> categories;
    List<FundingState> states;
    Pageable pageable;

    public FundingSearchCriteriaDto(FundingRequestDto dto, Pageable pageable) {
        this.startDate = toLocalDateTime(dto.getStartDate());
        this.endDate = toLocalDateTime(dto.getEndDate());
        this.categories = dto.getCategory();
        this.states = dto.getState();
        this.pageable = pageable;
    }
}
