package com.stocktrading.news.service;

import com.stocktrading.news.domain.News;
import com.stocktrading.news.domain.NewsStock;
import com.stocktrading.news.repository.NewsRepository;
import com.stocktrading.news.repository.NewsStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NewsService {

    private final NewsRepository newsRepository;

    private final NewsStockRepository newsStockRepository;

    public NewsService(NewsRepository newsRepository, NewsStockRepository newsStockRepository) {
        this.newsRepository = newsRepository;
        this.newsStockRepository = newsStockRepository;
    }

    public void updateNews(List<News> newsList) {
        for (News news : newsList) {
            Long newsId = newsRepository.save(news).getId();
            String ticker = news.getTicker();
            NewsStock newsStock = new NewsStock(ticker, newsId);
            newsStockRepository.save(newsStock);
        }
        log.info("뉴스 최신화 완료, news 총 갯수: {}", newsList.size());
    }

    public List<News> getNews(String ticker) {
        return newsRepository.findAllByTicker(ticker).stream()
                .sorted((a, b) -> b.getTime().compareTo(a.getTime()))
                .toList();
    }

}
