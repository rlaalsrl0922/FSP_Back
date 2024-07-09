package com.stocktrading.member.service;

import com.stocktrading.global.jwt.JwtProvider;
import com.stocktrading.global.jwt.JwtToken;
import com.stocktrading.member.domain.Member;
import com.stocktrading.member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final Logger log = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    public MemberService(MemberRepository memberRepository, AuthenticationManagerBuilder authenticationManagerBuilder, PasswordEncoder passwordEncoder1, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.passwordEncoder = passwordEncoder1;
        this.jwtProvider = jwtProvider;
    }

    public boolean login(String id, String password) {
        Member member = memberRepository.findByMemberId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        return member.login(password);
    public JwtToken login(String username, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return jwtProvider.generateToken(authentication);
    }

    public boolean signUp(String id, String password, String nickName) {
        Member member = new Member(id, password, nickName);
        if (id.isBlank()) {
    public void signUp(String username, String password, String nickName) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        if (memberRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        Member member = new Member(username,
                encodedPassword,
                nickName,
                roles);
        memberRepository.save(member);
        return true;
        log.info("username: {}, password: {}", username, password);
    }

}