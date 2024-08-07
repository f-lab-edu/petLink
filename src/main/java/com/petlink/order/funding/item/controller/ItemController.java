package com.petlink.order.funding.item.controller;


import com.petlink.order.funding.item.dto.request.ItemRequestDto;
import com.petlink.order.funding.item.dto.response.FundingItemResponseDto;
import com.petlink.order.funding.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
