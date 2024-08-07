package com.petlink.order.funding.item.service;

import com.petlink.order.funding.domain.Funding;
import com.petlink.order.funding.exception.FundingException;
import com.petlink.order.funding.item.domain.FundingItem;
import com.petlink.order.funding.item.dto.request.ItemRequestDto;
import com.petlink.order.funding.item.dto.response.FundingItemResponseDto;
import com.petlink.order.funding.item.repository.ItemRepository;
import com.petlink.order.funding.repository.FundingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.petlink.order.funding.exception.FundingExceptionCode.FUNDING_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final FundingRepository fundingRepository;

    @Transactional
    public FundingItemResponseDto registerItems(Long fundingId, List<ItemRequestDto> itemDtoList) {
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(() -> new FundingException(FUNDING_NOT_FOUND));

        List<FundingItemResponseDto.FailedItem> failList = new ArrayList<>();

        // 아이템 등록 성공한 아이템 수를 계산
        long successCount = itemDtoList.stream()
                .map(itemDto -> {
                    try {
                        registerItem(funding, itemDto);
                        return true;
                    } catch (Exception e) {
                        failList.add(FundingItemResponseDto.FailedItem.of(itemDto.getTitle(), e.getMessage()));
                        return false;
                    }
                })
                .filter(isSuccess -> isSuccess)
                .count();

        long failCount = itemDtoList.size() - successCount;

        // 등록 성공/실패한 아이템 수와 실패한 아이템 목록을 담은 응답 객체 반환
        return FundingItemResponseDto.builder()
                .successCount(successCount)
                .failCount(failCount)
                .failList(failList)
                .build();
    }

    // 리워드 아이템을 등록하는 별도의 메소드
    private void registerItem(Funding funding, ItemRequestDto itemDto) {
        FundingItem fundingItem = itemDto.toEntity(funding);
        itemRepository.save(fundingItem);
    }

}
