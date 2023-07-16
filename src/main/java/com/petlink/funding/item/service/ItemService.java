package com.petlink.funding.item.service;

import com.petlink.funding.domain.Funding;
import com.petlink.funding.exception.FundingException;
import com.petlink.funding.item.domain.FundingItem;
import com.petlink.funding.item.dto.request.FundingItemRequestDto;
import com.petlink.funding.item.repository.ItemRepository;
import com.petlink.funding.repository.FundingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petlink.funding.exception.FundingExceptionCode.FUNDING_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final FundingRepository fundingRepository;

    @Transactional
    public void registerItems(Long fundingId, List<FundingItemRequestDto> itemDtoList) {
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(() -> new FundingException(FUNDING_NOT_FOUND));

        List<FundingItem> itemsToSave = itemDtoList.stream()
                .map(dto -> dto.toEntity(funding))
                .toList();

        itemRepository.saveAll(itemsToSave);
    }
}
