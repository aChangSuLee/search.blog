package com.search.blog.source;

import com.search.blog.controller.request.SearchRequest;
import com.search.blog.dto.DaumSearchResult;
import com.search.blog.dto.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class DaumBlog extends SearchSource {

  private final WebClient daumBlogClient;

  @Override
  public SearchResult search(SearchRequest request) throws Exception {
    return daumBlogClient.get()
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
