package com.stocktrading.member.service;
import com.stocktrading.member.domain.MemberStock;
import com.stocktrading.member.repository.MemberRepository;
import com.stocktrading.member.repository.MemberStockRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class MemberStockService {
    private final MemberRepository memberRepository;
    private final MemberStockRepository memberStockRepository;
    public MemberStockService(MemberRepository memberRepository, MemberStockRepository memberStockRepository) {
        this.memberRepository = memberRepository;
        this.memberStockRepository = memberStockRepository;
    }

    public void bookmark(String stockTicker) {
        Optional<MemberStock> memberStock = memberStockRepository.findByStockTickerAndMemberId(stockTicker, memberId);
        if (memberStock.isPresent()) {
            MemberStock newMemberStock = memberStock.get().bookmark();
            memberStockRepository.save(newMemberStock);
            return;
        }
        MemberStock newMemberStock = new MemberStock(memberId, stockTicker, true);
        memberStockRepository.save(newMemberStock);
    }
}