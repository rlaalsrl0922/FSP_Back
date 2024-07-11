package com.stocktrading.stock.domain;

import com.stocktrading.global.BaseTimeEntity;
import com.stocktrading.stock.controller.dto.StockInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
public class Stock extends BaseTimeEntity {

    @Id
    @Getter
    @GeneratedValue
    private Long id; // 순위

    private String stockTicker;

    private String name;

    private Double price;

    private Float today;

    private String logoUrl;

    @Builder
    public Stock(String stockTicker, String name, Double price, Float today, String logoUrl) {
        this.stockTicker = stockTicker;
        this.name = name;
        this.price = price;
        this.today = today;
        this.logoUrl = logoUrl;
    }

    public StockInfo toDto() {
        return new StockInfo(
                name,
                stockTicker,
                logoUrl,
                "$" + price,
                today >= 0 ? "+" + today + "%" : today + "%"
        );
    }

}
