package com.stocktrading.member.controller;

import com.stocktrading.global.jwt.JwtToken;
import com.stocktrading.member.dto.MemberLoginRequest;
import com.stocktrading.member.dto.MemberSignUpRequest;
import com.stocktrading.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/auth")
public class MemberController {

    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public void signUp(@RequestBody MemberSignUpRequest request) {
        memberService.signUp(request.username(), request.password(), request.nickname());
    }

    @PostMapping("/login")
    public JwtToken login(@RequestBody MemberLoginRequest request) {
        String username = request.username();
        String password = request.password();
        JwtToken jwtToken = memberService.login(username, password);
        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.accessToken(), jwtToken.refreshToken());
        return jwtToken;
    }

    @DeleteMapping
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }
}