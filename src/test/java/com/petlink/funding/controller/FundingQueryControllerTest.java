package com.petlink.funding.controller;

import static com.petlink.funding.domain.FundingCategory.*;
import static com.petlink.funding.domain.FundingState.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.web.servlet.MockMvc;

import com.petlink.config.filter.JwtAuthenticationFilter;
import com.petlink.funding.domain.FundingCategory;
import com.petlink.funding.domain.FundingState;
import com.petlink.funding.dto.request.FundingListRequestDto;
import com.petlink.funding.dto.response.FundingListResponseDto;
import com.petlink.funding.service.FundingService;

@WebMvcTest(controllers = FundingQueryController.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
	})
@AutoConfigureMockMvc(addFilters = false)
class FundingQueryControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	private FundingService fundingService;

	private <T> T getRotatingValue(List<T> list, T[] allValues, int index) {
		if (list != null && !list.isEmpty()) {
			return list.get(index % list.size());
		} else {
			return allValues[index % allValues.length];
		}
	}

	public List<FundingListResponseDto> createMockDtoList(int size, List<FundingCategory> categoryList,
		List<FundingState> stateList) {
		return IntStream.range(0, size).mapToObj(i -> {
			FundingCategory category = getRotatingValue(categoryList, FundingCategory.values(), i);
			FundingState state = getRotatingValue(stateList, FundingState.values(), i);

			return FundingListResponseDto.builder()
				.id((long)i + 1)
				.title("Test Title " + (i + 1))
				.state(state)
				.category(category)
				.startDate(LocalDateTime.of(2023, 1, 1, 0, 0, 0))
				.endDate(LocalDateTime.of(2023, 12, 31, 0, 0, 0))
				.build();
		}).collect(Collectors.toList());
	}

	@Test
	@DisplayName("최소한의 검색 조건으로 펀딩 목록을 조회한다.")
	void getFundingList() throws Exception {
		Pageable pageable = PageRequest.of(0, 5);
		List<FundingListResponseDto> mockDtoList = createMockDtoList(5, null, null);
		Slice<FundingListResponseDto> mockSlice = new PageImpl<>(mockDtoList, pageable, mockDtoList.size());

		when(fundingService.getFundingList(ArgumentMatchers.any(FundingListRequestDto.class),
			ArgumentMatchers.any(Pageable.class))).thenReturn(
			mockSlice);

		mockMvc.perform(get("/fundings")
				.param("startDate", "20230101")
				.param("endDate", "20231231"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.length()").value(5))
			.andExpect(jsonPath("$.pageable.pageNumber").value(0))
			.andExpect(jsonPath("$.pageable.pageSize").value(5))
			.andExpect(jsonPath("$.pageable.offset").value(0))
			.andDo(print());
	}

	@Test
	@DisplayName("조회 조건에 따른 상태(PROGRESS)를 검증한다.")
	void getFundingListWithStateParam() throws Exception {
		//given
		List<FundingCategory> category = List.of(FOOD, CLOTHES);
		List<FundingState> state = List.of(PROGRESS);

		Pageable pageable = PageRequest.of(0, 5);
		List<FundingListResponseDto> mockFundingList = createMockDtoList(5, category, state);
		Slice<FundingListResponseDto> mockFundingSlice = new PageImpl<>(mockFundingList, pageable,
			mockFundingList.size());

		when(fundingService.getFundingList(ArgumentMatchers.any(FundingListRequestDto.class),
			ArgumentMatchers.any(Pageable.class))).thenReturn(
			mockFundingSlice);

		mockMvc.perform(get("/fundings")
				.param("startDate", "20230101")
				.param("endDate", "20231231")
				.param("category", "FOOD,CLOTHES")
				.param("state", "PROGRESS")
				.param("page", String.valueOf(pageable.getPageNumber()))
				.param("size", String.valueOf(pageable.getPageSize())))
			.andExpect(status().isOk())
			//모든 state가 정상인지 체크
			.andExpect(jsonPath("$.content[*].state", everyItem(is("PROGRESS"))))
			.andDo(print());
	}

	@Test
	@DisplayName("조회 조건에 따른 카테고리(FOOD, CLOTHES)를 검증한다.")
	void getFundingListWithCategoryParam() throws Exception {
		//given
		List<FundingCategory> category = List.of(FOOD, CLOTHES);
		List<FundingState> state = List.of(PROGRESS);

		Pageable pageable = PageRequest.of(0, 5);
		List<FundingListResponseDto> mockFundingList = createMockDtoList(5, category, state);
		Slice<FundingListResponseDto> mockFundingSlice = new PageImpl<>(mockFundingList, pageable,
			mockFundingList.size());

		when(fundingService.getFundingList(ArgumentMatchers.any(FundingListRequestDto.class),
			ArgumentMatchers.any(Pageable.class))).thenReturn(
			mockFundingSlice);

		mockMvc.perform(get("/fundings")
				.param("startDate", "20230101")
				.param("endDate", "20231231")
				.param("category", "FOOD,CLOTHES")
				.param("state", "PROGRESS")
				.param("page", String.valueOf(pageable.getPageNumber()))
				.param("size", String.valueOf(pageable.getPageSize())))
			.andExpect(status().isOk())
			//모든 category가 FOOD, CLOTHES인지 체크
			.andExpect(jsonPath("$.content.[*].category", everyItem(in(Arrays.asList("FOOD", "CLOTHES")))))
			.andDo(print());
	}

	@Test
	@DisplayName("잘못된 파라미터로 요청했을 때 에러가 반환된다.")
	void getFundingListWithInvalidParam() throws Exception {
		mockMvc.perform(get("/fundings")
				.param("startDate", "invalid_start_date")
				.param("endDate", "invalid_end_date"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.timestamp").exists())
			.andDo(print());
	}

	@Test
	@DisplayName("필수 파라미터 누락 시 에러가 반환된다.")
	void getFundingListMissingParams() throws Exception {
		mockMvc.perform(get("/fundings")
				.param("startDate", "20230101"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.timestamp").exists())
			.andDo(print());
	}

	@Test
	@DisplayName("인식할 수 없는 카테고리를 전달한 경우 에러가 반환된다.")
	void getFundingListUnrecognizedCategory() throws Exception {
		mockMvc.perform(get("/fundings")
				.param("startDate", "20230101")
				.param("endDate", "20231231")
				.param("category", "UNRECOGNIZED_CATEGORY"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.timestamp").exists())
			.andDo(print());
	}

	@Test
	@DisplayName("인식할 수 없는 상태를 전달한 경우 에러가 반환된다.")
	void getFundingListUnrecognizedState() throws Exception {
		mockMvc.perform(get("/fundings")
				.param("startDate", "20230101")
				.param("endDate", "20231231")
				.param("state", "UNRECOGNIZED_STATE"))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.timestamp").exists())
			.andDo(print());
	}
}
