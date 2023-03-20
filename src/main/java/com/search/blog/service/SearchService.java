package com.search.blog.service;

import com.search.blog.controller.request.SearchRequest;
import com.search.blog.dto.DaumSearchResult;
import com.search.blog.dto.SearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
  private final WebClient webClient;

  public SearchResult search(SearchRequest request) throws Exception {

    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .queryParam("query", request.getKeyword())
            .queryParam("page", request.getPage())
            .queryParam("size", request.getSize())
            .queryParam("sort", request.getSort())
            .build())
        .retrieve()
        .bodyToMono(DaumSearchResult.class)
        .block();
  }
}
