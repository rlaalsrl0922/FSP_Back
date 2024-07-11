package com.stocktrading.news.repository;

import com.stocktrading.news.domain.NewsStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsStockRepository extends JpaRepository<NewsStock, Long> {

}
