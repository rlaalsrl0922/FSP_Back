package com.stocktrading.domain;

import com.stocktrading.global.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class MemberStock extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String memberId;

    private String stockTicker;

}