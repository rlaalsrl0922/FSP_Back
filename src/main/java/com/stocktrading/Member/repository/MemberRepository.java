package com.stocktrading.member.repository;

import com.stocktrading.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);

}
