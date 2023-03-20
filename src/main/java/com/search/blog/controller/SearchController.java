package com.search.blog.controller;

import com.search.blog.controller.request.SearchRequest;
import com.search.blog.dto.SearchResult;
import com.search.blog.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

  private final SearchService searchService;

  @Operation(
      summary = "블로그 글 검색",
      description = "블로그 글을 검색합니다. daum 블로그에서 먼저 검색하고 에러 발생시 naver 블로그에서 검색합니다."
  )
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<Page<Map<String, String>>> search(
      @Valid SearchRequest request
  ) throws Exception {
    SearchResult searchResult = searchService.search(request);

    PageImpl<Map<String, String>> ret = new PageImpl<>(searchResult.getDocuments(), request.of(), searchResult.getTotalSize());

    return new ResponseEntity<>(ret, HttpStatus.OK);
  }
}
