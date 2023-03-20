package com.search.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class KeywordFrequency {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String keyword;

  private Long frequency;

  public KeywordFrequency (String keyword) {
    this.keyword = keyword;
    this.frequency = 1L;
  }

  public void updateFrequency () {
    this.frequency += 1;
  }
}
