package com.stocktrading.stock.controller.dto;

public record StockInfo(
        String name,
        String ticker,
        String logoUrl,
        String price,
        String today
) {

}
