package com.stocktrading.member.controller;

import com.stocktrading.member.controller.dto.GetBookmarkedRequest;
import com.stocktrading.member.controller.dto.GetBookmarkedResponse;
import com.stocktrading.member.service.MemberStockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberStockController {

    private final MemberStockService memberStockService;
    public MemberStockController(MemberStockService memberStockService) {
        this.memberStockService = memberStockService;
    }

     @PostMapping("/bookmarks")
    public void bookmark(@RequestParam("ticker") String stockTicker) {
        memberStockService.bookmark(stockTicker);
    }

    @GetMapping("/bookmark")
    public List<GetBookmarkedResponse> getBookmarked(@RequestBody GetBookmarkedRequest request) {
        return memberStockService.getBookmarked(request.memberId());
    }

}