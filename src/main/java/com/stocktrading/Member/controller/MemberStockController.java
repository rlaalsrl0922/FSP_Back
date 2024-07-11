package com.stocktrading.member.controller;

import com.stocktrading.global.jwt.JwtProvider;
import com.stocktrading.member.controller.dto.GetBookmarkedResponse;
import com.stocktrading.member.service.MemberStockService;
import com.stocktrading.stock.controller.dto.StockInfo;
import com.stocktrading.stock.domain.Stock;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
public class MemberStockController {

    private final MemberStockService memberStockService;

    private final JwtProvider jwtProvider;

    public MemberStockController(MemberStockService memberStockService, JwtProvider jwtProvider) {
        this.memberStockService = memberStockService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bookmark(@RequestParam("ticker") String stockTicker, HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization").substring(7);
        String username = jwtProvider.getUsername(bearerToken);
        memberStockService.bookmark(username, stockTicker);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unbookmark(@RequestParam("ticker") String stockTicker, HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization").substring(7);
        String username = jwtProvider.getUsername(bearerToken);
        memberStockService.unBookmark(username, stockTicker);
    }

    @GetMapping
    public GetBookmarkedResponse getBookmarked(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization").substring(7);
        String username = jwtProvider.getUsername(bearerToken);
        List<StockInfo> bookmarkedStocks =  memberStockService.getBookmarked(username).stream()
                .map(Stock::toDto)
                .toList();
        return new GetBookmarkedResponse(bookmarkedStocks);
    }

}
