package com.petlink.funding.item.controller;


import com.petlink.funding.item.dto.request.FundingItemRequestDto;
import com.petlink.funding.item.service.ItemService;
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
    public ResponseEntity<Void> registerItems(@PathVariable Long fundingId, @Validated @RequestBody List<FundingItemRequestDto> itemDtoList) {
        itemService.registerItems(fundingId, itemDtoList);
        return ResponseEntity.ok().build();
    }


}
