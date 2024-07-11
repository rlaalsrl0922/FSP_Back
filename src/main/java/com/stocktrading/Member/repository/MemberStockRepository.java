package com.stocktrading.member.repository;

import com.stocktrading.member.domain.MemberStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberStockRepository extends JpaRepository<MemberStock, Long> {

    Optional<MemberStock> findByStockTickerAndMemberId(String stockTicker, Long memberId);

    List<MemberStock> findAllByMemberIdAndBookmarked(Long memberId, boolean bookmarked);

}
