package com.stocktrading.news.controller.dto;

import com.stocktrading.news.domain.News;

import java.time.LocalDateTime;

public record NewsInfo(
        Long id,
        String title,
        String url,
        String favicon_url,
        String time,
        String tickers
) {

    public News toEntity() {
        String replaced = time.replace(" ", "T");
        LocalDateTime parsedTime = LocalDateTime.parse(replaced);
        return News.builder()
                .serialNumber(id)
                .title(title)
                .url(url)
                .faviconUrl(favicon_url)
                .time(parsedTime)
                .ticker(tickers)
                .build();
    }

}