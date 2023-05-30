package com.petlink.funding.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petlink.config.filter.JwtAuthenticationFilter;
import com.petlink.funding.domain.FundingCategory;
import com.petlink.funding.domain.FundingState;
import com.petlink.funding.dto.response.FundingDetailResponseDto;
import com.petlink.funding.dto.response.FundingListDto;
import com.petlink.funding.service.FundingService;

@WebMvcTest(controllers = FundingController.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
	})
@AutoConfigureMockMvc(addFilters = false)
class FundingControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private FundingService fundingService;

	@Test
	@DisplayName("모든 펀딩 목록을 조회할 수 있다.")
	void selectFundingList() throws Exception {

		List<FundingListDto> responseDto = List.of(
			FundingListDto.builder().id(1L).title("title").miniTitle("miniTitle").build(),
			FundingListDto.builder().id(2L).title("title2").miniTitle("miniTitle2").build()
		);

		when(fundingService.findAllFundingSummaries()).thenReturn(responseDto);

		mockMvc.perform(get("/funding/list"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].id").value(1L))
			.andExpect(jsonPath("$[0].title").value("title"))
			.andExpect(jsonPath("$[0].miniTitle").value("miniTitle"))
			.andExpect(jsonPath("$[1].id").value(2L))
			.andExpect(jsonPath("$[1].title").value("title2"))
			.andExpect(jsonPath("$[1].miniTitle").value("miniTitle2"));
	}

	@Test
	void testFindById() throws Exception {
		Long id = 1L;

		FundingDetailResponseDto responseDto = FundingDetailResponseDto.builder()
			.id(id)
			.managerId(1L)
			.managerName("Manager Name")
			.managerEmail("manager@example.com")
			.phoneNumber("123-456-7890")
			.title("Test Title")
			.miniTitle("Test MiniTitle")
			.content("Test Content")
			.state(FundingState.PROGRESS)
			.category(FundingCategory.FOOD)
			.startDate(LocalDateTime.now())
			.endDate(LocalDateTime.now().plusDays(10))
			.targetDonation(10000L)
			.successDonation(5000L)
			.build();

		when(fundingService.findById(id)).thenReturn(responseDto);

		mockMvc.perform(get("/funding/" + id))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		verify(fundingService, times(1)).findById(id);
	}

}