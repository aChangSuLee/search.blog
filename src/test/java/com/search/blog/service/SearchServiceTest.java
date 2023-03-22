package com.search.blog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.blog.controller.request.SearchRequest;
import com.search.blog.dto.DaumSearchResult;
import com.search.blog.dto.NaverSearchResult;
import com.search.blog.dto.SearchResult;
import com.search.blog.source.DaumBlog;
import com.search.blog.source.NaverBlog;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SearchServiceTest {
  private static MockWebServer daumMockWebServer;
  private static MockWebServer naverMockWebServer;
  private static SearchService searchService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private DaumSearchResult daumSearchResult;

  private NaverSearchResult naverSearchResult;

  @BeforeAll
  static void setUp() throws IOException {
    daumMockWebServer = new MockWebServer();
    daumMockWebServer.start();

    naverMockWebServer = new MockWebServer();
    naverMockWebServer.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    daumMockWebServer.shutdown();
    naverMockWebServer.shutdown();
  }

  @BeforeAll
  static void initialize() {
    final String daumBaseUrl = String.format("http://localhost:%s", daumMockWebServer.getPort());
    final String naverBaseUrl = String.format("http://localhost:%s", naverMockWebServer.getPort());

    searchService = new SearchService(
        new DaumBlog(WebClient.create(daumBaseUrl)),
        new NaverBlog(WebClient.create(naverBaseUrl))
    );
  }

  @BeforeEach
  void setup() {
    daumSearchResult = new DaumSearchResult();
    daumSearchResult.setMeta(
        Map.of("is_end", false,
            "pageable_count", 750,
            "total_count", 18373268)
    );

    daumSearchResult.setDocuments(List.of(
        Map.of("blogname", "this is daum blog",
            "contents", "this is daum blog content",
            "datetime", "2023-02-11T21:47:00.000+09:00",
            "thumbnail", "https://search1.kakaocdn.net/",
            "title", "사랑합니다",
            "url", "http://darterjoo.tistory.com/"
        ),
        Map.of("blogname", "this is 2'nd daum blog",
            "contents", "this is 2'nd daum blog content",
            "datetime", "2023-02-12T21:47:00.000+09:00",
            "thumbnail", "https://search1.kakaocdn.net/",
            "title", "사랑합니다 22",
            "url", "http://darterjoo.tistory.com/"
        )
    ));

    naverSearchResult = new NaverSearchResult();
    naverSearchResult.setDisplay(10);
    naverSearchResult.setTotal(1444000);
    naverSearchResult.setStart(1);
    naverSearchResult.setLastBuildDate("Mon, 20 Mar 2023 16:32:15 +0900");

    naverSearchResult.setItems(List.of(
        Map.of("title", "this is naver blog",
        "link", "https://blog.naver.com/",
        "description", "naver blog content",
        "bloggername", "blogger name",
        "bloggerlink", "blog.naver.com/ddd",
        "postdate", "20230318"
        )
    ));
  }

  @Test
  @DisplayName("정상 응답")
  void searchTest () throws Exception {
    daumMockWebServer.enqueue(new MockResponse()
        .setBody(objectMapper.writeValueAsString(daumSearchResult))
        .setHeader("Content-Type", "application/json"));

    SearchRequest request = new SearchRequest();
    request.setKeyword("사랑");
    SearchResult result = searchService.search(request);

    assert result != null;
    assert result.getTotalSize().equals(daumSearchResult.getTotalSize());
  }

  @Test
  @DisplayName("다음 블로그 이상으로 네이버 블로그에서 검색")
  void daumSourceResponse5xxTest () throws Exception {
    daumMockWebServer.enqueue(new MockResponse()
        .setHeader("Content-Type", "application/json")
        .setResponseCode(500)
    );

    naverMockWebServer.enqueue(new MockResponse()
        .setBody(objectMapper.writeValueAsString(naverSearchResult))
        .setHeader("Content-Type", "application/json"));

    SearchRequest request = new SearchRequest();
    request.setKeyword("사랑");
    SearchResult result = searchService.search(request);

    assert result != null;
    assert result.getTotalSize().equals(naverSearchResult.getTotalSize());
  }

  @Test
  @DisplayName("네이버 블로그에서 검색")
  void naverSourceSearch () throws Exception {
    naverMockWebServer.enqueue(new MockResponse()
        .setBody(objectMapper.writeValueAsString(naverSearchResult))
        .setHeader("Content-Type", "application/json"));

    SearchRequest request = new SearchRequest();
    request.setKeyword("사랑");
    request.setSource("naver");
    SearchResult result = searchService.search(request);

    assert result != null;
    assert result.getTotalSize().equals(naverSearchResult.getTotalSize());
  }
}
