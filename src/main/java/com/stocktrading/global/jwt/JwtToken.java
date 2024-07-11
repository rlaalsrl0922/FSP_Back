package com.stocktrading.global.jwt;

import lombok.Builder;

public record JwtToken(
        String grantType,
        String accessToken,
        String refreshToken
) {

    @Builder
    public JwtToken {
    }

}
