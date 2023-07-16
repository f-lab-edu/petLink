package com.petlink.funding.item.service;

import com.petlink.funding.domain.Funding;
import com.petlink.funding.exception.FundingException;
import com.petlink.funding.item.domain.FundingItem;
import com.petlink.funding.item.dto.request.FundingItemRequestDto;
import com.petlink.funding.item.dto.response.FundingItemResponseDto;
import com.petlink.funding.item.repository.ItemRepository;
import com.petlink.funding.repository.FundingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private FundingRepository fundingRepository;

    @Test
    @DisplayName("펀딩 아이템이 정상적으로 등록된다")
    public void registerItemSuccessTest() {
        // Given
        Long fundingId = 1L;
        FundingItemRequestDto fundingItemRequestDto = new FundingItemRequestDto("title1", "content1", 10L, 5L);
        List<FundingItemRequestDto> itemDtoList = Collections.singletonList(fundingItemRequestDto);
        Funding funding = Funding.builder()
                .id(fundingId)
                .build();

        when(fundingRepository.findById(anyLong())).thenReturn(Optional.of(funding));
        when(itemRepository.save(any())).thenReturn(null);

        // When
        FundingItemResponseDto result = itemService.registerItems(fundingId, itemDtoList);

        // Then
        assertEquals(1, result.getSuccessCount());
        assertEquals(0, result.getFailCount());
        assertTrue(result.getFailList().isEmpty());

        verify(fundingRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(1)).save(any(FundingItem.class));
    }

    @Test
    @DisplayName("펀딩 아이템 등록 중 예외가 발생하면 등록이 실패한다")
    public void registerItemFailureTest() {
        // Given
        Long fundingId = 1L;
        FundingItemRequestDto fundingItemRequestDto = new FundingItemRequestDto("title1", "content1", 10L, 5L);
        List<FundingItemRequestDto> itemDtoList = Collections.singletonList(fundingItemRequestDto);
        Funding funding = Funding.builder()
                .id(fundingId)
                .build();


        when(fundingRepository.findById(anyLong())).thenReturn(Optional.of(funding));
        when(itemRepository.save(any())).thenThrow(new RuntimeException("Exception occurred"));

        // When
        FundingItemResponseDto result = itemService.registerItems(fundingId, itemDtoList);

        // Then
        assertEquals(0, result.getSuccessCount());
        assertEquals(1, result.getFailCount());
        assertFalse(result.getFailList().isEmpty());

        verify(fundingRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(1)).save(any(FundingItem.class));
    }

    @Test
    @DisplayName("펀딩 아이템 중 하나가 실패하고, 나머지 두 개가 성공한다")
    public void registerItems_oneFail_twoSuccess() {
        // Given
        Long fundingId = 1L;
        FundingItemRequestDto item1 = new FundingItemRequestDto("title1", "content1", 10L, 5L);
        FundingItemRequestDto item2 = new FundingItemRequestDto("title2", "content2", 20L, 10L);
        FundingItemRequestDto item3 = new FundingItemRequestDto("title3", "content3", 30L, 15L);
        List<FundingItemRequestDto> itemDtoList = Arrays.asList(item1, item2, item3);

        Funding funding = Funding.builder()
                .id(fundingId)
                .build();

        when(fundingRepository.findById(anyLong())).thenReturn(Optional.of(funding));

        when(itemRepository.save(any()))
                .thenAnswer(i -> null)
                .thenAnswer(i -> null)
                .thenThrow(new RuntimeException("Exception occurred"));

        // When
        FundingItemResponseDto result = itemService.registerItems(fundingId, itemDtoList);

        // Then
        // 성공한 아이템의 수가 2개인지 확인합니다.
        assertEquals(2, result.getSuccessCount());

        // 실패한 아이템의 수가 1개인지 확인합니다.
        assertEquals(1, result.getFailCount());

        // 실패한 아이템 목록이 비어있지 않은지(즉, 실패한 아이템이 하나라도 있는지) 확인합니다.
        assertFalse(result.getFailList().isEmpty());

        // 첫 번째 실패 아이템의 제목이 기대하는 제목인 item3의 제목과 같은지 확인합니다.
        assertEquals(item3.getTitle(), result.getFailList().get(0).getTitle());

        // 첫 번째 실패 아이템의 실패 이유가 "Exception occurred"인지 확인합니다.
        assertEquals("Exception occurred", result.getFailList().get(0).getReason());

        // fundingRepository에서 findById 메서드가 정확히 한 번 호출되었는지 확인합니다.
        verify(fundingRepository, times(1)).findById(anyLong());

        // itemRepository에서 save 메서드가 정확히 세 번 호출되었는지 확인합니다.
        verify(itemRepository, times(3)).save(any(FundingItem.class));

    }


    @Test
    @DisplayName("존재하지 않는 펀딩에 아이템을 등록하려고 하면 예외가 발생한다")
    public void registerItemToFundingNotFoundTest() {
        // Given
        Long fundingId = 1L;
        FundingItemRequestDto fundingItemRequestDto = new FundingItemRequestDto("title1", "content1", 10L, 5L);
        List<FundingItemRequestDto> itemDtoList = Collections.singletonList(fundingItemRequestDto);

        when(fundingRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThrows(FundingException.class, () -> itemService.registerItems(fundingId, itemDtoList));
        verify(fundingRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(0)).save(any(FundingItem.class));
    }
}
