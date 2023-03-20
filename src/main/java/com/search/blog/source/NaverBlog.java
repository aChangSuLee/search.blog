package com.search.blog.source;

import com.search.blog.controller.request.SearchRequest;
import com.search.blog.dto.NaverSearchResult;
import com.search.blog.dto.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class NaverBlog extends SearchSource {

  private final WebClient naverBlogClient;

  @Override
  public SearchResult search(SearchRequest request) {
    String sortOpt = request.getSort().equals("accuracy") ? "sim" : "date";

    return naverBlogClient.get()
        .uri(uriBuilder -> uriBuilder
            .queryParam("query", request.getKeyword())
            .queryParam("start", request.getPage())
            .queryParam("display", request.getSize())
            .queryParam("sort", sortOpt)
            .build())
        .retrieve()
        .bodyToMono(NaverSearchResult.class)
        .block();
  }
}
