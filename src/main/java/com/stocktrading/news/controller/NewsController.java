package com.stocktrading.news.controller;
import com.stocktrading.news.controller.dto.NewsInfo;
import com.stocktrading.news.controller.dto.UpdateNewsRequest;
import com.stocktrading.news.domain.News;
import com.stocktrading.news.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateNews(@RequestBody UpdateNewsRequest request) {
        List<News> newsList = request.stories().stream()
                .map(NewsInfo::toEntity)
                .toList();
        newsService.updateNews(newsList);
    }

    @GetMapping("/{ticker}")
    public List<NewsInfo> getNews(@PathVariable String ticker) {
        List<News> newsList = newsService.getNews(ticker);
        return newsList.stream()
                .map(News::toDto)
                .toList();

    }

}