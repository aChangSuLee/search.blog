package com.search.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class SearchResult {
  @Setter
  @Getter
  List<Map<String, String>> documents;

  @Setter
  @Getter
  Integer totalSize;
}
