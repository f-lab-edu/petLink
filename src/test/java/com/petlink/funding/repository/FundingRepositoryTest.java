package com.petlink.funding.repository;

import com.petlink.config.jpa.QuerydslConfiguration;
import com.petlink.funding.domain.Funding;
import com.petlink.funding.domain.FundingCategory;
import com.petlink.funding.domain.FundingState;
import com.petlink.funding.dto.request.FundingRequestDto;
import com.petlink.funding.dto.request.FundingSearchCriteriaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

import static com.petlink.common.util.date.DateConverter.toLocalDateTime;
import static com.petlink.funding.domain.FundingCategory.FOOD;
import static com.petlink.funding.domain.FundingCategory.TOY;
import static com.petlink.funding.domain.FundingState.END;
import static com.petlink.funding.domain.FundingState.PROGRESS;
import static org.assertj.core.api.Assertions.assertThat;

@Import(QuerydslConfiguration.class)
@DataJpaTest
class FundingRepositoryTest {

    @Autowired
    FundingRepository fundingRepository;

    @BeforeEach
    void setUp() {
        fundingRepository.deleteAll();
    }

    void setUpData(FundingState state, FundingCategory category, int count) {
        for (int i = 1; i <= count; i++) {
            Funding funding = Funding.builder()
                    .title("펀딩 제목 " + i)
                    .miniTitle("펀딩 부제목 " + i)
                    .content("펀딩 내용 " + i)
                    .state(state)
                    .category(category)
                    .startDate(toLocalDateTime("20230501"))
                    .endDate(toLocalDateTime("20230601"))
                    .targetDonation(10000L + i)
                    .successDonation(5000L + i)
                    .build();
            fundingRepository.save(funding);
        }
    }

    @Test
    @DisplayName("펀딩 목록을 조회할 수 있다.")
    void findAll() {
        //given
        setUpData(PROGRESS, TOY, 3);
        setUpData(END, FOOD, 5);

        FundingRequestDto dto = FundingRequestDto.builder()
                .startDate("20230501")
                .endDate("20230601")
                .build();
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        //when
        Slice<Funding> fundingList = fundingRepository.findFundingList(
                new FundingSearchCriteriaDto(dto, pageable)
        );

        //then
        assertThat(fundingList).hasSize(8);
        assertThat(fundingList
                .stream()
                .filter(funding -> funding.getCategory() == TOY)
                .count()).isEqualTo(3);
        assertThat(fundingList
                .stream()
                .filter(funding -> funding.getCategory() == FOOD)
                .count()).isEqualTo(5);
    }

    @Test
    @DisplayName("펀딩 목록을 조회할 수 있다. - 카테고리")
    void findAllByCategory() {
        //given
        setUpData(PROGRESS, TOY, 3);
        setUpData(END, FOOD, 5);
        FundingRequestDto dto = FundingRequestDto.builder()
                .startDate("20230501")
                .endDate("20230601")
                .category(List.of(TOY))
                .build();
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        //when
        Slice<Funding> fundingList = fundingRepository.findFundingList(
                new FundingSearchCriteriaDto(dto, pageable)
        );

        //then
        assertThat(fundingList).hasSize(3);
        assertThat(fundingList
                .stream()
                .filter(funding -> funding.getCategory() == TOY)
                .count()).isEqualTo(3);
    }

    @Test
    @DisplayName("펀딩 목록을 조회할 수 있다. - 상태")
    void findAllByState() {
        //given
        setUpData(PROGRESS, TOY, 3);
        setUpData(END, FOOD, 5);
        FundingRequestDto dto = FundingRequestDto.builder()
                .startDate("20230501")
                .endDate("20230601")
                .state(List.of(PROGRESS))
                .build();
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        //when
        Slice<Funding> fundingList = fundingRepository.findFundingList(
                new FundingSearchCriteriaDto(dto, pageable)

        );

        //then
        assertThat(fundingList).hasSize(3);
        assertThat(fundingList
                .stream()
                .filter(funding -> funding.getState() == PROGRESS)
                .count()).isEqualTo(3);
    }

    @Test
    @DisplayName("펀딩 목록을 조회할 수 있다. - 카테고리, 상태")
    void findAllByCategoryAndState() {
        //given
        setUpData(PROGRESS, TOY, 3);
        setUpData(END, FOOD, 5);
        FundingRequestDto dto = FundingRequestDto.builder()
                .startDate("20230501")
                .endDate("20230601")
                .category(List.of(TOY))
                .state(List.of(PROGRESS))
                .build();
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        //when
        Slice<Funding> fundingList = fundingRepository.findFundingList(
                new FundingSearchCriteriaDto(dto, pageable)
        );

        //then
        assertThat(fundingList).hasSize(3);
        assertThat(fundingList
                .stream()
                .filter(funding -> funding.getCategory() == TOY)
                .count()).isEqualTo(3);
        assertThat(fundingList
                .stream()
                .filter(funding -> funding.getState() == PROGRESS)
                .count()).isEqualTo(3);
    }
}
