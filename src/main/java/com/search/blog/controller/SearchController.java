package com.search.blog.controller;

import com.search.blog.controller.request.SearchRequest;
import com.search.blog.dto.SearchResult;
import com.search.blog.service.SearchService;
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

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<Page<Map<String, String>>> search(@Valid SearchRequest request) throws Exception {
    SearchResult searchResult = searchService.search(request);

    PageImpl<Map<String, String>> ret = new PageImpl<>(searchResult.getDocuments(), request.of(), searchResult.getTotalSize());

    return new ResponseEntity<>(ret, HttpStatus.OK);
  }
}
