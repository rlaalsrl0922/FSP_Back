package com.stocktrading.news.domain;

import com.stocktrading.global.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class NewsStock extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String stockTicker;

    private Long newsId;

    public NewsStock(String stockTicker, Long newsId) {
        this.stockTicker = stockTicker;
        this.newsId = newsId;
    }

}
