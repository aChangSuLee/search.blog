package com.search.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.blog.service.SearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SearchController.class)
@DisplayName("search controller test")
public class SearchControllerTest {
  @Autowired
  protected MockMvc mockMvc;

  @MockBean
  private SearchService searchService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("invalid args - keyword is empty")
  void whenEmptyKeyword_thenReturn400() throws Exception {
    String emptyKeyword = "";
    String expectedMessage = "InvalidArgument";
    Set<String> expectedMessageSet = Set.of("keyword parameter required");

    MvcResult mvcResult = this.mockMvc.perform(get("/search").queryParam("keyword", emptyKeyword))
        .andExpect(status().is4xxClientError())
        .andReturn();

    Map<String, String> actualResponseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);

    assert actualResponseBody.get("errorType").equals(expectedMessage);
    assert Arrays.stream(actualResponseBody.get("message").split(", ")).collect(Collectors.toSet())
        .equals(expectedMessageSet);
  }

  @Test
  @DisplayName("invalid args - size is over than max")
  void whenOverSizeLimit_thenReturn400() throws Exception {
    String keyword = "사랑";
    String wrongSize = "100";

    String expectedMessage = "InvalidArgument";
    Set<String> expectedMessageSet = Set.of("size is more than max");

    MvcResult mvcResult = this.mockMvc.perform(get("/search")
            .queryParam("keyword", keyword)
            .queryParam("size", wrongSize)
        )
        .andExpect(status().is4xxClientError())
        .andReturn();

    Map<String, String> actualResponseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);

    assert actualResponseBody.get("errorType").equals(expectedMessage);
    assert Arrays.stream(actualResponseBody.get("message").split(", ")).collect(Collectors.toSet())
        .equals(expectedMessageSet);
  }

  @Test
  @DisplayName("invalid args - page, size, keyword are wrong")
  void whenMultiArgsAreInvalid_thenReturn400() throws Exception {
    String emptyKeyword = "";
    String wrongSize = "100";
    String wrongPage = "-1";

    String expectedMessage = "InvalidArgument";
    Set<String> expectedMessageSet = Set.of(
        "size is more than max",
        "page is less than min",
        "keyword parameter required");

    MvcResult mvcResult = this.mockMvc.perform(get("/search")
            .queryParam("keyword", emptyKeyword)
            .queryParam("size", wrongSize)
            .queryParam("page", wrongPage))
        .andExpect(status().is4xxClientError())
        .andReturn();

    Map<String, String> actualResponseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);

    assert actualResponseBody.get("errorType").equals(expectedMessage);
    assert Arrays.stream(actualResponseBody.get("message").split(", ")).collect(Collectors.toSet())
        .equals(expectedMessageSet);
  }
}
