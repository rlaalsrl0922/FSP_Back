package com.stocktrading.stock.controller;

import com.stocktrading.stock.controller.dto.GetTop100StocksResponse;
import com.stocktrading.stock.controller.dto.StockInfo;
import com.stocktrading.stock.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }
    @GetMapping("/{stockTicker}")
    public ResponseEntity<Void> getStockInfo(@PathVariable String stockTicker) {
        stockService.findStock(stockTicker);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/top100")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void initTop100Stocks(@RequestBody List<StockInfo> stockInfos) {
        stockService.initTop100(stockInfos);
    }

    @GetMapping("/top100")
    public GetTop100StocksResponse getTop100Stocks() {
        List<StockInfo> stockInfos = stockService.getTop100();
        return new GetTop100StocksResponse(stockInfos);
    }

}