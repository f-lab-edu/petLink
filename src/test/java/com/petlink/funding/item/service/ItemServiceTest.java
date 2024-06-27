package com.petlink.funding.item.service;

import com.petlink.funding.domain.Funding;
import com.petlink.funding.exception.FundingException;
import com.petlink.funding.item.domain.FundingItem;
import com.petlink.funding.item.dto.request.ItemRequestDto;
import com.petlink.funding.item.dto.response.FundingItemResponseDto;
import com.petlink.funding.item.repository.ItemRepository;
import com.petlink.funding.repository.FundingRepository;
import org.junit.jupiter.api.BeforeEach;
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

    private Long fundingId;
    private ItemRequestDto itemDto1, itemDto2, itemDto3;
    private Funding funding;

    @BeforeEach
    public void setup() {
        fundingId = 1L;
        itemDto1 = new ItemRequestDto("title1", "content1", 10L, 5L);
        itemDto2 = new ItemRequestDto("title2", "content2", 20L, 10L);
        itemDto3 = new ItemRequestDto("title3", "content3", 30L, 15L);
        funding = Funding.builder().id(fundingId).build();

        when(fundingRepository.findById(anyLong())).thenReturn(Optional.of(funding));
    }

    @Test
    @DisplayName("펀딩 아이템 등록 시 정상적일 경우 모든 아이템이 성공적으로 등록된다.")
    public void registerItemSuccessTest() {
        // Given
        List<ItemRequestDto> itemDtoList = Collections.singletonList(itemDto1);

        when(itemRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

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
    @DisplayName("펀딩 아이템 등록 시 예외 발생으로 모든 아이템 등록이 실패할 수 있다.")
    public void registerItemFailureTest() {
        // Given
        List<ItemRequestDto> itemDtoList = Collections.singletonList(itemDto1);

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
    @DisplayName("펀딩 아이템 등록 시 일부 아이템은 성공하고 일부 아이템은 실패할 수도 있다.")
    public void registerItems_oneFail_twoSuccess() {
        // Given
        List<ItemRequestDto> itemDtoList = Arrays.asList(itemDto1, itemDto2, itemDto3);

        when(itemRepository.save(any()))
                .thenAnswer(i -> i.getArguments()[0])
                .thenAnswer(i -> i.getArguments()[0])
                .thenThrow(new RuntimeException("Exception occurred"));

        // When
        FundingItemResponseDto result = itemService.registerItems(fundingId, itemDtoList);

        // Then
        assertEquals(2, result.getSuccessCount());
        assertEquals(1, result.getFailCount());
        assertFalse(result.getFailList().isEmpty());
        assertEquals(itemDto3.getTitle(), result.getFailList().get(0).getTitle());
        assertEquals("Exception occurred", result.getFailList().get(0).getReason());

        verify(fundingRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(3)).save(any(FundingItem.class));
    }

    @Test
    @DisplayName("존재하지 않는 펀딩에 아이템을 등록하려 할 때 예외가 발생한다")
    public void registerItemToFundingNotFoundTest() {
        // Given
        List<ItemRequestDto> itemDtoList = Collections.singletonList(itemDto1);

        when(fundingRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThrows(FundingException.class, () -> itemService.registerItems(fundingId, itemDtoList));
        verify(fundingRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(0)).save(any(FundingItem.class));
    }
}
