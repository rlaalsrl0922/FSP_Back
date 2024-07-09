package com.stocktrading.member.dto;

public record MemberSignUpRequest(
        String username,
        String password,
        String nickname
) {
}