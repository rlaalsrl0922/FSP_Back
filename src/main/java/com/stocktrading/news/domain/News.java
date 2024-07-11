package com.stocktrading.news.domain;

import com.stocktrading.global.BaseTimeEntity;
import com.stocktrading.news.controller.dto.NewsInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;
import static lombok.AccessLevel.PROTECTED;
@Entity
@ToString
@NoArgsConstructor(access = PROTECTED)
public class News extends BaseTimeEntity {
    @Id
    @Getter
    @GeneratedValue
    private Long id;
    private Long serialNumber; // 뉴스 고유 id
    private String title;
    @Lob
    private String url;

    private String faviconUrl;

    @Getter
    private LocalDateTime time;

    @Getter
    private String ticker;
    @Builder
    public News(Long serialNumber, String title, String url, String faviconUrl, LocalDateTime time, String ticker) {
        this.serialNumber = serialNumber;
        this.title = title;
        this.url = url;
        this.faviconUrl = faviconUrl;
        this.time = time;
        this.ticker = ticker;
    }

    public NewsInfo toDto() {
        return NewsInfo.builder()
                .id(serialNumber)
                .title(title)
                .url(url)
                .favicon_url(faviconUrl)
                .time(time.toString())
                .tickers(ticker)
                .build();
    }

}