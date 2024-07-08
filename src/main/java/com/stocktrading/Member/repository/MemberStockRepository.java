package com.stocktrading.repository;

import com.stocktrading.domain.MemberStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberStockRepository extends JpaRepository<MemberStock, Long> {

}