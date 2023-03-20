package com.search.blog.service;

import com.search.blog.controller.request.SearchRequest;
import com.search.blog.dto.SearchResult;
import com.search.blog.source.DaumBlog;
import com.search.blog.source.NaverBlog;
import com.search.blog.source.SearchSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

  private final DaumBlog daumBlog;

  private final NaverBlog naverBlog;

  public SearchResult search(SearchRequest request) throws Exception {
    SearchResult result;

    try {
      result = daumBlog.search(request);
    } catch (Exception e) {
      log.error("daum blog error: {}", e.getMessage());
      result = getNextSource().search(request);
    }

    return result;
  }

  private SearchSource getNextSource () {
    return this.naverBlog;
  }
}
