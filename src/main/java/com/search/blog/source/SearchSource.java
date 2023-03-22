package com.search.blog.source;

import com.search.blog.controller.request.SearchRequest;
import com.search.blog.dto.SearchResult;
import lombok.Getter;
import lombok.Setter;

public abstract class SearchSource {

  @Setter
  @Getter
  private String sourceName = null;
  public SearchResult search(SearchRequest request) throws Exception {
    return null;
  }
}
