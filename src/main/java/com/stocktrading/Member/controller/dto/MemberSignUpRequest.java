package com.stocktrading.member.controller.dto;

public record MemberSignUpRequest(
        String username,
        String password,
        String nickname
) {

}
