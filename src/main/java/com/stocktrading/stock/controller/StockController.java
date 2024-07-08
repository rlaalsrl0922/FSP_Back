package com.stocktrading.stock.controller;
import com.stocktrading.stock.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}