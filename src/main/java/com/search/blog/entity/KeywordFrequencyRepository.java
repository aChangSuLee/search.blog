package com.search.blog.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordFrequencyRepository extends JpaRepository<KeywordFrequency, Long> {
  KeywordFrequency findByKeyword(String keyword);
  List<KeywordFrequency> findTop10ByOrderByFrequencyDescKeywordDesc();
}
