package com.search.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class NaverSearchResult extends SearchResult {
  @Getter
  List<Map<String, String>> items;
  @Getter
  @Setter
  String lastBuildDate;
  @Getter
  Integer total;
  @Getter
  @Setter
  Integer start;
  @Getter
  @Setter
  Integer display;

  public void setItems (List<Map<String, String>> items) {
    this.items = items;
    this.documents = items;
  }

  public void setTotal (Integer total) {
    this.total = total;
    this.totalSize = total;
  }
}
