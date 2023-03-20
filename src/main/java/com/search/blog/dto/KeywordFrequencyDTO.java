package com.search.blog.dto;

import com.search.blog.entity.KeywordFrequency;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KeywordFrequencyDTO {
  private String keyword;
  private long frequency;
  public KeywordFrequencyDTO (KeywordFrequency keywordFrequency) {
    this.keyword = keywordFrequency.getKeyword();
    this.frequency = keywordFrequency.getFrequency();
  }
}
