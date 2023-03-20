package com.search.blog.source;

import com.search.blog.controller.request.SearchRequest;
import com.search.blog.dto.SearchResult;

public abstract class SearchSource {
  public SearchResult search(SearchRequest request) throws Exception {
    return null;
  }
}
