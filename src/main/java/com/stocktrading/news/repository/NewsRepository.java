package com.stocktrading.news.repository;


import com.stocktrading.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findAllByTicker(String ticker);

}
