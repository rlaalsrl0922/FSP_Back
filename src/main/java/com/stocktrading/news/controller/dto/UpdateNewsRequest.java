package com.stocktrading.news.controller.dto;

import java.util.List;

public record UpdateNewsRequest(
        List<NewsInfo> stories
) {

}
