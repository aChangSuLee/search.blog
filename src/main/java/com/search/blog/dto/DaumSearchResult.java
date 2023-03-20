package com.search.blog.dto;

import lombok.Getter;

import java.util.Map;

public class DaumSearchResult extends SearchResult {
  @Getter
  Map<String, Object> meta;

  public void setMeta(Map<String, Object> meta) {
    this.meta = meta;
    this.totalSize = (Integer) meta.get("total_count");
  }
}
