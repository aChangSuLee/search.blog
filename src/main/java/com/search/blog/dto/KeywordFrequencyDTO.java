package com.search.blog.dto;

import com.search.blog.entity.KeywordFrequency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public class KeywordFrequencyDTO {
  @Setter
  @Getter
  private String keyword;
  @Setter
  @Getter
  private long frequency;
  public KeywordFrequencyDTO (KeywordFrequency keywordFrequency) {
    this.keyword = keywordFrequency.getKeyword();
    this.frequency = keywordFrequency.getFrequency();
  }
}
