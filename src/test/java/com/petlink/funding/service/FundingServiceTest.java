package com.petlink.funding.service;

import static com.petlink.common.util.date.DateConverter.*;
import static com.petlink.funding.domain.FundingCategory.*;
import static com.petlink.funding.domain.FundingState.*;
import static com.petlink.funding.exception.FundingExceptionCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.petlink.common.exception.CommonException;
import com.petlink.funding.domain.Funding;
import com.petlink.funding.dto.request.FundingListRequestDto;
import com.petlink.funding.dto.response.DetailInfoResponse;
import com.petlink.funding.dto.response.FundingListResponseDto;
import com.petlink.funding.exception.FundingException;
import com.petlink.funding.repository.FundingRepository;
import com.petlink.manager.domain.Manager;

@ExtendWith(MockitoExtension.class)
class FundingServiceTest {
	@Mock
	private FundingRepository fundingRepository;

	@InjectMocks
	private FundingService fundingService;

	@Test
	@DisplayName("펀딩 목록 조회")
	void testGetFundingList() {
		FundingListRequestDto requestDto = FundingListRequestDto.builder()
			.startDate("20230101")
			.endDate("20231231")
			.build();

		Pageable pageable = mock(Pageable.class);

		// Mock fundingList 데이터
		List<Funding> fundingListData = Collections.singletonList(
			Funding.builder()
				.id(1L)
				.title("펀딩 제목")
				.state(PROGRESS)
				.category(TOY)
				.startDate(toLocalDateTime("20230501"))
				.endDate(toLocalDateTime("20230731"))
				.build());

		Slice<Funding> fundingList = new SliceImpl<>(fundingListData, pageable, false);

		when(fundingRepository.findFundingList(
			any(), any(), any(), any(), any(Pageable.class)))
			.thenReturn(fundingList);

		Slice<FundingListResponseDto> result = fundingService.getFundingList(requestDto, pageable);

		assertEquals(fundingListData.size(), result.getContent().size());
		assertEquals(fundingListData.get(0).getId(), result.getContent().get(0).getId());
		assertEquals(fundingListData.get(0).getTitle(), result.getContent().get(0).getTitle());
		assertEquals(fundingListData.get(0).getState(), result.getContent().get(0).getState());
	}

	@Test
	@DisplayName("펀딩 목록 조회 - 검색 결과 없음")
	void testGetFundingList_NoSearchResultsFound() {
		FundingListRequestDto requestDto = FundingListRequestDto.builder()
			.startDate("20230101")
			.endDate("20231231")
			.build();

		Pageable pageable = mock(Pageable.class);

		when(fundingRepository.findFundingList(
			any(), any(), any(), any(), any(Pageable.class)))
			.thenThrow(new FundingException(NO_SEARCH_RESULTS_FOUND));

		assertThrows(FundingException.class, () -> fundingService.getFundingList(requestDto, pageable));
	}

	@Test
	@DisplayName("펀딩 목록 조회 - 시작일 혹은 종요일이 Null 경우 예외가 발생한다.")
	void testGetFundingList_StartDateIsNull() {
		Pageable pageable = mock(Pageable.class);
		FundingListRequestDto requestDtoA = FundingListRequestDto.builder().endDate("20231231").build();
		FundingListRequestDto requestDtoB = FundingListRequestDto.builder().endDate("20231231").build();

		assertThrows(CommonException.class, () -> fundingService.getFundingList(requestDtoA, pageable));
		assertThrows(CommonException.class, () -> fundingService.getFundingList(requestDtoB, pageable));
	}

	@Test
	@DisplayName("엔티티는 응답객체로 변경되어 반환된다.")
	void testEntityToResponse() {
		//given
		Funding funding = Funding.builder()
			.id(1L)
			.title("펀딩 제목")
			.miniTitle("펀딩 부제목")
			.startDate(toLocalDateTime("20230501"))
			.endDate(toLocalDateTime("20230731"))
			.state(PROGRESS)
			.category(TOY)
			.build();

		FundingListRequestDto requestDto = FundingListRequestDto.builder()
			.startDate("20230501")
			.endDate("20230731")
			.category(List.of(TOY))
			.state(List.of(PROGRESS))
			.build();

		Pageable pageable = mock(Pageable.class);

		List<Funding> fundingList = Collections.singletonList(funding);
		Slice<Funding> fundingSlice = new SliceImpl<>(fundingList, pageable, true);

		//when
		when(fundingRepository.findFundingList(any(), any(), any(), any(), any()))
			.thenReturn(fundingSlice);

		Slice<FundingListResponseDto> resultSlice = fundingService.getFundingList(requestDto, pageable);
		FundingListResponseDto result = resultSlice.getContent().get(0);

		//then
		assertEquals(funding.getId(), result.getId());
		assertEquals(funding.getTitle(), result.getTitle());
		assertEquals(funding.getState(), result.getState());
		assertEquals(funding.getCategory(), result.getCategory());
		assertEquals(funding.getStartDate(), result.getStartDate());
		assertEquals(funding.getEndDate(), result.getEndDate());
	}

	@Test
	@DisplayName("펀딩 상세 정보 조회")
	void testFindById() {
		//given
		Long id = 1L;
		Funding funding = Funding.builder()
			.id(id)
			.title("펀딩 제목")
			.miniTitle("펀딩 부제목")
			.startDate(toLocalDateTime("20230501"))
			.endDate(toLocalDateTime("20230731"))
			.state(PROGRESS)
			.category(TOY)
			.targetDonation(1000L)
			.successDonation(500L)
			.manager(Manager.builder()
				.id(1L)
				.name("managerName")
				.email("managerEmail")
				.phoneNumber("1234567890")
				.build())
			.build();

		//when
		when(fundingRepository.findById(id)).thenReturn(Optional.of(funding));

		DetailInfoResponse result = fundingService.findById(id);

		//then
		assertEquals(funding.getId(), result.getId());
		assertEquals(funding.getTitle(), result.getTitle());
		assertEquals(funding.getState(), result.getState());
		assertEquals(funding.getCategory(), result.getCategory());
		assertEquals(funding.getStartDate(), result.getStartDate());
		assertEquals(funding.getEndDate(), result.getEndDate());
		assertEquals(funding.getTargetDonation(), result.getTargetDonation());
		assertEquals(funding.getSuccessDonation(), result.getSuccessDonation());
		assertEquals(funding.getManager().getId(), result.getManagerId());
		assertEquals(funding.getManager().getName(), result.getManagerName());
		assertEquals(funding.getManager().getEmail(), result.getManagerEmail());
		assertEquals(funding.getManager().getPhoneNumber(), result.getPhoneNumber());
	}

	@Test
	@DisplayName("펀딩 상세 정보 조회 - 펀딩이 존재하지 않음")
	void testFindById_FundingNotFound() {
		//given
		Long id = 1L;

		//when
		when(fundingRepository.findById(id)).thenReturn(Optional.empty());
		//then
		assertThrows(FundingException.class, () -> fundingService.findById(id));
	}

}
