package com.stocktrading.controller.dto;

public record MemberSignUpRequest(
        String id,
        String password,
        String nickname
) {

}