package com.stocktrading.member.controller.dto;

public record GetBookmarkedResponse(
        String stockTicker,
        String stockName,
        String imageUrl
        // 뉴스 리스트 추가해야 함
) {

}