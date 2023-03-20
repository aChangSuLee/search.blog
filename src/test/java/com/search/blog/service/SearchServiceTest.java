package com.search.blog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.blog.controller.request.SearchRequest;
import com.search.blog.dto.DaumSearchResult;
import com.search.blog.dto.SearchResult;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SearchServiceTest {
  private static MockWebServer mockWebServer;
  private SearchService searchService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeAll
  static void setUp() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    mockWebServer.shutdown();
  }

  @BeforeEach
  void initialize() {
    final String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
    final WebClient webClient = WebClient.create(baseUrl);
    searchService = new SearchService(webClient);
  }

  @Test
  void searchTest () throws Exception {
    DaumSearchResult daumSearchResult = new DaumSearchResult();
    daumSearchResult.setMeta(
        Map.of("is_end", false,
            "pageable_count", 750,
        "total_count", 18373268)
    );

    daumSearchResult.setDocuments(List.of(
        Map.of("blogname", "this is blog name1",
            "contents", "this is content1",
            "datetime", "2023-02-11T21:47:00.000+09:00",
            "thumbnail", "https://search1.kakaocdn.net/argon/130x130_85_c/Ka4CK5hzXre",
            "title", "사랑합니다",
            "url", "http://darterjoo.tistory.com/17069784"
            ),
        Map.of("blogname", "this is blog name2",
            "contents", "this is content2",
            "datetime", "2023-02-12T21:47:00.000+09:00",
            "thumbnail", "https://search1.kakaocdn.net/argon/130x130_85_c/Ka4CK5hzXre",
            "title", "사랑합니다 22",
            "url", "http://darterjoo.tistory.com/17069784"
        )
    ));

    mockWebServer.enqueue(new MockResponse()
        .setBody(objectMapper.writeValueAsString(daumSearchResult))
        .addHeader("Content-Type", "application/json"));

    SearchRequest request = new SearchRequest();
    request.setKeyword("사랑");
    SearchResult result = searchService.search(request);

    assert result != null;
  }
}
