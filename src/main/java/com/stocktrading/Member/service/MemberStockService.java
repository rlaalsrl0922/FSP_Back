package com.stocktrading.member.service;

import com.stocktrading.member.domain.Member;
import com.stocktrading.member.domain.MemberStock;
import com.stocktrading.member.repository.MemberRepository;
import com.stocktrading.member.repository.MemberStockRepository;
import com.stocktrading.stock.domain.Stock;
import com.stocktrading.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberStockService {

    private final MemberRepository memberRepository;

    private final MemberStockRepository memberStockRepository;

    private final StockRepository stockRepository;

    public MemberStockService(MemberRepository memberRepository, MemberStockRepository memberStockRepository, StockRepository stockRepository) {
        this.memberRepository = memberRepository;
        this.memberStockRepository = memberStockRepository;
        this.stockRepository = stockRepository;
    }

    public void bookmark(String username, String stockTicker) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("해당하는 유저가 없습니다."));
        Long memberId = member.getId();
        stockTicker = stockTicker.toUpperCase();
        Optional<MemberStock> memberStock = memberStockRepository.findByStockTickerAndMemberId(stockTicker, memberId);
        if (memberStock.isPresent()) {
            MemberStock newMemberStock = memberStock.get().bookmark();
            memberStockRepository.save(newMemberStock);
            return;
        }
        MemberStock newMemberStock = new MemberStock(memberId, stockTicker, true);
        memberStockRepository.save(newMemberStock);
    }

    public void unBookmark(String username, String stockTicker) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("해당하는 유저가 없습니다."));
        Long memberId = member.getId();
        stockTicker = stockTicker.toUpperCase();
        Optional<MemberStock> memberStock = memberStockRepository.findByStockTickerAndMemberId(stockTicker, memberId);
        if (memberStock.isPresent()) {
            MemberStock cancelledBookmark = memberStock.get().unBookmark();
            memberStockRepository.save(cancelledBookmark);
        }
    }

    public List<Stock> getBookmarked(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("해당하는 유저가 없습니다."));
        Long memberId = member.getId();
        List<String> tickers = memberStockRepository.findAllByMemberIdAndBookmarked(memberId, true).stream()
                .map(MemberStock::getStockTicker)
                .distinct()
                .toList();
        System.out.println("즐겨찾기 목록: " + tickers);

        List<Stock> stocks = new ArrayList<>();
        for (String ticker : tickers) {
            Stock stock = stockRepository.findByStockTicker(ticker)
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 주식 정보가 없습니다."));
            stocks.add(stock);
        }
        return stocks;
    }

}
