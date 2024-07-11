package com.stocktrading.stock.service;

import com.stocktrading.stock.controller.dto.StockInfo;
import com.stocktrading.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void findStock(String stockTicker) {
        try {
            Stock stock = YahooFinance.get(stockTicker);
            stock.print();
        } catch (IOException e) {
            throw new IllegalStateException("주식 정보를 가져오는 데 실패했음.");
        }
    }

    public void initTop100(final List<StockInfo> stockInfos) {
        stockRepository.deleteAllInBatch();
        List<com.stocktrading.stock.domain.Stock> stocks = new ArrayList<>();
        for (StockInfo stockInfo : stockInfos) {
            String parsedPrice = stockInfo.price().substring(1).replace(",", "");
            String parsedToday = stockInfo.today().substring(1, stockInfo.today().length() - 1);
            var newStock = com.stocktrading.stock.domain.Stock.builder()
                    .name(stockInfo.name())
                    .stockTicker(stockInfo.ticker())
                    .logoUrl(stockInfo.logoUrl())
                    .price(Double.parseDouble(parsedPrice))
                    .today(stockInfo.today().charAt(0) == '+' ? Float.parseFloat(parsedToday) : - Float.parseFloat(parsedToday))
                    .build();
            stocks.add(newStock);
        }
        stockRepository.saveAll(stocks);
    }

    public List<StockInfo> getTop100() {
        return stockRepository.findAll().stream()
                .sorted(Comparator.comparingLong(com.stocktrading.stock.domain.Stock::getId))
                .map(com.stocktrading.stock.domain.Stock::toDto)
                .toList();
    }

}
