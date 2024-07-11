package com.stocktrading.stock.controller.dto;

import java.util.List;

public record GetTop100StocksResponse(
        List<StockInfo> top100
) {

}
