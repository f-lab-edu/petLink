package com.petlink.funding.item.controller;


import com.petlink.funding.item.dto.request.ItemRequestDto;
import com.petlink.funding.item.dto.response.FundingItemResponseDto;
import com.petlink.funding.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/{fundingId}")
    public ResponseEntity<FundingItemResponseDto> registerItems(@PathVariable Long fundingId, @Validated @RequestBody List<ItemRequestDto> itemDtoList) {
        FundingItemResponseDto fundingItemResponseDto = itemService.registerItems(fundingId, itemDtoList);
        return ResponseEntity.ok(fundingItemResponseDto);
    }
}
