package com.search.blog.controller;

import com.search.blog.controller.response.PopularResponse;
import com.search.blog.service.PopularService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/popular")
@RequiredArgsConstructor
@Slf4j
public class PopularController {
  private final PopularService popularService;

  @Operation(
      summary = "인기 검색어 조회",
      description = "많이 검색한 순서대로 상위 10 개의 키워드를 응답합니다."
  )
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<PopularResponse> popular () {
    PopularResponse response = new PopularResponse(popularService.popularKeywords());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
