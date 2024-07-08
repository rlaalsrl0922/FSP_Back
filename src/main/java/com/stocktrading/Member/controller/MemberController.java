package com.stocktrading.controller;

import com.stocktrading.controller.dto.MemberLoginRequest;
import com.stocktrading.controller.dto.MemberSignUpRequest;
import com.stocktrading.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody MemberSignUpRequest request) {
        boolean isSignUpSuccess = memberService.signUp(request.id(), request.password(), request.nickName());
        if (isSignUpSuccess) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody MemberLoginRequest request) {
        boolean isLoginSuccess = memberService.login(request.id(), request.password());
        if (isLoginSuccess) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}