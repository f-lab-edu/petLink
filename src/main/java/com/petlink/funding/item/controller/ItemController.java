package com.petlink.funding.item.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fundings/items")
@RequiredArgsConstructor
public class ItemController {

    @GetMapping
    public String selectFundingItems(Long fundingId) {

        return "fundingId";
    }
}
