package com.stocktrading.member.dto;

public record MemberSignUpRequest(
        String id,
        String username,
        String password,
        String nickname
) {
}