package com.stocktrading.member.controller.dto;

import com.stocktrading.stock.controller.dto.StockInfo;

import java.util.List;

public record GetBookmarkedResponse(
        List<StockInfo> bookmarked
) {

}
