package com.stocktrading.stock.domain;
import com.stocktrading.global.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import static lombok.AccessLevel.PROTECTED;
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Stock extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id; // 순위
    private String stockTicker;
    private String name;

    private Long price;

    private String imageUrl;

}