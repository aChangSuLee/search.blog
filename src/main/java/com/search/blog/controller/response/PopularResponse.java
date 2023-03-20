package com.search.blog.controller.response;

import com.search.blog.dto.KeywordFrequencyDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class PopularResponse {
  @Setter
  @Getter
  private List<KeywordFrequencyDTO> popularKeywords;
}
