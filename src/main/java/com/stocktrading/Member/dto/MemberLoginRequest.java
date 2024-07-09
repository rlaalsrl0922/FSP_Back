package com.stocktrading.member.dto;

public record MemberLoginRequest(
        String id,
        String username,
        String password
) {

}