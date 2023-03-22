package com.search.blog.service;


import com.search.blog.dto.KeywordFrequencyDTO;
import com.search.blog.entity.KeywordFrequencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularService {
  private final KeywordFrequencyRepository keywordFrequencyRepository;

  public List<KeywordFrequencyDTO> popularKeywords () {
    return keywordFrequencyRepository.findTop10ByOrderByFrequencyDescKeywordDesc().stream()
        .map(KeywordFrequencyDTO::new)
        .toList();
  }
}
