package com.petlink.funding.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.petlink.RestDocsSupport;
import com.petlink.funding.domain.FundingCategory;
import com.petlink.funding.domain.FundingState;
import com.petlink.funding.dto.request.FundingRequestDto;
import com.petlink.funding.dto.response.FundingDetailResponse;
import com.petlink.funding.dto.response.FundingListResponseDto;
import com.petlink.funding.exception.FundingException;
import com.petlink.funding.service.FundingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.petlink.common.util.date.DateConverter.toLocalDateTime;
import static com.petlink.funding.domain.FundingCategory.CLOTHES;
import static com.petlink.funding.domain.FundingCategory.FOOD;
import static com.petlink.funding.domain.FundingState.PROGRESS;
import static com.petlink.funding.exception.FundingExceptionCode.FUNDING_NOT_FOUND;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FundingQueryControllerTest extends RestDocsSupport {

    @InjectMocks
    private FundingQueryController fundingQueryController;

    @Mock
    private FundingService fundingService;

    @Override
    protected Object initController() {
        return new FundingQueryController(fundingService);
    }

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
                    .id((long) i + 1)
                    .title("Test Title " + (i + 1))
                    .state(state)
                    .category(category)
                    .startDate(LocalDateTime.of(2023, 1, 1, 0, 0, 0))
                    .endDate(LocalDateTime.of(2023, 12, 31, 0, 0, 0))
                    .build();
        }).collect(Collectors.toList());
    }

    @Test
    @DisplayName("문서 생성 테스트")
    void getFundingListDocs() throws Exception {

        FundingRequestDto requestDto = FundingRequestDto.builder()
                .startDate("20230101")
                .endDate("20231231")
                .category(List.of(FundingCategory.values()))
                .state(List.of(FundingState.values()))
                .build();

        Pageable pageable = PageRequest.of(0, 2);

        List<FundingListResponseDto> mockDtoList = createMockDtoList(2, null, null);

        Slice<FundingListResponseDto> mockSlice = new PageImpl<>(mockDtoList, pageable, mockDtoList.size());

        when(fundingService.getFundingList(ArgumentMatchers.any(FundingRequestDto.class),
                ArgumentMatchers.any(Pageable.class))).thenReturn(
                mockSlice);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/fundings")
                        .param("startDate", "20230101")
                        .param("endDate", "20231231")
                        .param("category", "TOY, FOOD, ETC")
                        .param("state", "END , PROGRESS")
                        .param("page", "0")
                        .param("size", "2")
                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("fundings/get-list",
                        queryParameters(
                                parameterWithName("startDate").description("조회 시작일"),
                                parameterWithName("endDate").description("조회 종료일"),
                                parameterWithName("category").description("펀딩 카테고리"),
                                parameterWithName("state").description("펀딩 상태"),
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("content").type(JsonFieldType.ARRAY).description("펀딩 목록"),
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("펀딩 ID"),
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("펀딩 제목"),
                                fieldWithPath("content[].state").type(JsonFieldType.STRING).description("펀딩 상태"),
                                fieldWithPath("content[].category").type(JsonFieldType.STRING).description("펀딩 카테고리"),
                                fieldWithPath("content[].startDate").type(JsonFieldType.ARRAY).description("펀딩 시작일"),
                                fieldWithPath("content[].endDate").type(JsonFieldType.ARRAY).description("펀딩 종료일"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 정보가 비어있는지 여부"),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬되지 않은 상태인지 여부"),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬된 상태인지 여부"),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("페이지 오프셋"),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 여부"),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징 되지 않았는지 여부"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("총 요소 수"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 정보가 비어있는지 여부"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬되지 않은 상태인지 여부"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬된 상태인지 여부"),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("첫번째 페이지 여부"),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("현재 페이지의 요소 수"),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("페이지가 비어있는지 여부")
                        )));

    }

    @Test
    @DisplayName("최소한의 검색 조건으로 펀딩 목록을 조회할 수 있다.")
    void getFundingList() throws Exception {

        FundingRequestDto requestDto = FundingRequestDto.builder()
                .startDate("20230101")
                .endDate("20231231")
                .build();

        Pageable pageable = PageRequest.of(0, 5);
        List<FundingListResponseDto> mockDtoList = createMockDtoList(5, null, null);
        Slice<FundingListResponseDto> mockSlice = new PageImpl<>(mockDtoList, pageable, mockDtoList.size());

        when(fundingService.getFundingList(ArgumentMatchers.any(FundingRequestDto.class),
                ArgumentMatchers.any(Pageable.class))).thenReturn(
                mockSlice);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/fundings")
                        .param("startDate", "20230101")
                        .param("endDate", "20231231"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(5))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(5))
                .andExpect(jsonPath("$.pageable.offset").value(0));
    }

    @Test
    @DisplayName("상태(PROGRESS) 조회 조건을 가지고  조회할 수 있다.")
    void getFundingListWithStateParam() throws Exception {
        //given
        List<FundingCategory> category = List.of(FOOD, CLOTHES);
        List<FundingState> state = List.of(PROGRESS);

        Pageable pageable = PageRequest.of(0, 5);
        List<FundingListResponseDto> mockFundingList = createMockDtoList(5, category, state);
        Slice<FundingListResponseDto> mockFundingSlice = new PageImpl<>(mockFundingList, pageable,
                mockFundingList.size());

        when(fundingService.getFundingList(ArgumentMatchers.any(FundingRequestDto.class),
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
        ;
    }

    @Test
    @DisplayName("카테고리(FOOD, CLOTHES) 조회 조건을 가지고  조회할 수 있다.")
    void getFundingListWithCategoryParam() throws Exception {
        //given
        List<FundingCategory> category = List.of(FOOD, CLOTHES);
        List<FundingState> state = List.of(PROGRESS);

        Pageable pageable = PageRequest.of(0, 5);
        List<FundingListResponseDto> mockFundingList = createMockDtoList(5, category, state);
        Slice<FundingListResponseDto> mockFundingSlice = new PageImpl<>(mockFundingList, pageable,
                mockFundingList.size());

        when(fundingService.getFundingList(ArgumentMatchers.any(FundingRequestDto.class),
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
                .andExpect(jsonPath("$.content.[*].category", everyItem(in(Arrays.asList("FOOD", "CLOTHES")))));
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
        ;
    }

    @Test
    @DisplayName("필수 파라미터 누락 시 에러가 반환된다.")
    void getFundingListMissingParams() throws Exception {
        mockMvc.perform(get("/fundings")
                        .param("startDate", "20230101"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.timestamp").exists())
        ;
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
        ;
    }

    @Test
    @DisplayName("인식할 수 없는 펀딩 상태를 전달한 경우 에러가 반환된다.")
    void getFundingListUnrecognizedState() throws Exception {
        mockMvc.perform(get("/fundings")
                        .param("startDate", "20230101")
                        .param("endDate", "20231231")
                        .param("state", "UNRECOGNIZED_STATE"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.timestamp").exists())
        ;
    }

    @Test
    @DisplayName("펀딩 아이디로 펀딩 상세정보를 조회할 수 있다..")
    void getFundingById() throws Exception {
        // Given
        Long id = 1L;
        FundingDetailResponse mockResponse = FundingDetailResponse.builder()
                .id(id)
                .managerId(1L)
                .managerName("Test Manager")
                .managerEmail("test@manager.com")
                .phoneNumber("012-3456-7890")
                .title("Test Title")
                .miniTitle("Test Mini Title")
                .content("Test Content")
                .state(FundingState.PROGRESS)
                .category(FundingCategory.FOOD)
                .startDate(toLocalDateTime("20230101"))
                .endDate(toLocalDateTime("20231201"))
                .targetDonation(1000000L)
                .successDonation(500000L)
                .build();

        when(fundingService.findById(id)).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/fundings/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.managerId").value(mockResponse.getManagerId()))
                .andExpect(jsonPath("$.managerName").value(mockResponse.getManagerName()))
                .andExpect(jsonPath("$.managerEmail").value(mockResponse.getManagerEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(mockResponse.getPhoneNumber()))
                .andExpect(jsonPath("$.title").value(mockResponse.getTitle()))
                .andExpect(jsonPath("$.miniTitle").value(mockResponse.getMiniTitle()))
                .andExpect(jsonPath("$.content").value(mockResponse.getContent()))
                .andExpect(jsonPath("$.state").value(mockResponse.getState().toString()))
                .andExpect(jsonPath("$.category").value(mockResponse.getCategory().toString()))
                .andExpect(jsonPath("$.targetDonation").value(mockResponse.getTargetDonation()))
                .andExpect(jsonPath("$.successDonation").value(mockResponse.getSuccessDonation()))

                .andDo(MockMvcRestDocumentationWrapper.document("fundings/get-by-id",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("펀딩 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("펀딩 아이디"),
                                fieldWithPath("managerId").type(JsonFieldType.NUMBER).description("펀딩 관리자 아이디"),
                                fieldWithPath("managerName").type(JsonFieldType.STRING).description("펀딩 관리자 이름"),
                                fieldWithPath("managerEmail").type(JsonFieldType.STRING).description("펀딩 관리자 이메일"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("펀딩 관리자 전화번호"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("펀딩 제목"),
                                fieldWithPath("miniTitle").type(JsonFieldType.STRING).description("펀딩 부제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("펀딩 내용"),
                                fieldWithPath("state").type(JsonFieldType.STRING).description("펀딩 상태"),
                                fieldWithPath("category").type(JsonFieldType.STRING).description("펀딩 카테고리"),
                                fieldWithPath("startDate").type(JsonFieldType.ARRAY).description("펀딩 시작일"),
                                fieldWithPath("endDate").type(JsonFieldType.ARRAY).description("펀딩 종료일"),
                                fieldWithPath("targetDonation").type(JsonFieldType.NUMBER).description("목표 금액"),
                                fieldWithPath("successDonation").type(JsonFieldType.NUMBER).description("달성 금액")
                        )
                ));
    }

    @Test
    @DisplayName("존재하지 않는 펀딩 아이디로 조회할 경우 에러가 반환된다.")
    void getFundingById_WithInvalidId_ThrowsException() throws Exception {
        // Arrange
        Long invalidId = 99999L;
        when(fundingService.findById(invalidId)).thenThrow(
                new FundingException(FUNDING_NOT_FOUND));

        // Act & Assert
        mockMvc.perform(get("/fundings/" + invalidId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
