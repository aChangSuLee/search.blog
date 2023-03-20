package com.search.blog.controller.response;

import com.search.blog.dto.KeywordFrequencyDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class PopularResponse {
  @Setter
  @Getter
  private List<KeywordFrequencyDTO> keywords;
}
