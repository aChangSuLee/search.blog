package com.search.blog.aop;

import com.search.blog.controller.request.SearchRequest;
import com.search.blog.entity.KeywordFrequency;
import com.search.blog.entity.KeywordFrequencyRepository;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@AllArgsConstructor
public class KeywordCounter {

  private final KeywordFrequencyRepository keywordFrequencyRepository;

  @Pointcut("execution(* com.search.blog..SearchController.search(..))")
  private void cut() {}

  @AfterReturning(value = "cut()")
  public void afterReturning (JoinPoint joinPoint) {
    Arrays.stream(joinPoint.getArgs())
        .findFirst()
        .ifPresent(req -> {
          String keyword = ((SearchRequest) req).getKeyword();
          if (!keyword.isBlank()) {
            KeywordFrequency keywordFrequency = keywordFrequencyRepository.findByKeyword(keyword);
            if (keywordFrequency == null) {
              keywordFrequency = new KeywordFrequency(keyword);
            } else {
              keywordFrequency.updateFrequency();
            }
            keywordFrequencyRepository.save(keywordFrequency);
          }
        });
  }
}
