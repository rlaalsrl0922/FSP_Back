package com.stocktrading.news.controller;

import com.stocktrading.news.controller.dto.NewsInfo;
import com.stocktrading.news.controller.dto.UpdateNewsRequest;
import com.stocktrading.news.domain.News;
import com.stocktrading.news.service.NewsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public void updateNews(@RequestBody UpdateNewsRequest request) {
        List<News> newsList = request.stories().stream()
                .map(NewsInfo::toEntity)
                .toList();
        newsService.updateNews(newsList);
    }

}