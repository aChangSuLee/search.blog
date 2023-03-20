package com.search.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.blog.controller.response.PopularResponse;
import com.search.blog.dto.KeywordFrequencyDTO;
import com.search.blog.dto.SearchResult;
import com.search.blog.service.SearchService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationTests {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	private SearchService searchService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() throws Exception {
		SearchResult searchResult = new SearchResult();

		searchResult.setDocuments(List.of(
				Map.of("dummy data", "dummy data 1"),
				Map.of("dummy data", "dummy data 2")
		));
		searchResult.setTotalSize(2);

		given(searchService.search(any()))
				.willReturn(searchResult);
	}
	@Test
	void contextLoads() {
	}

	@Test
	@Order(1)
	void searchRightKeyword() throws Exception {
		List<String> searchKeywords = List.of("hope", "hope", "hope", "hope",
				"happy", "happy",
				"love");

		for(String keyword : searchKeywords) {
			this.mockMvc.perform(get("/search").queryParam("keyword", keyword))
					.andExpect(status().isOk())
					.andReturn();
		}
	}

	@Test
	@Order(2)
	void popularApiTest() throws Exception {
		PopularResponse expectedResult = new PopularResponse();
		expectedResult.setPopularKeywords(List.of(
				new KeywordFrequencyDTO("hope", 4),
				new KeywordFrequencyDTO("happy", 2),
				new KeywordFrequencyDTO("love", 1)
		));

		MvcResult mvcResult = this.mockMvc.perform(get("/popular"))
				.andExpect(status().isOk())
				.andReturn();

		PopularResponse actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PopularResponse.class);

		for (int idx = 0; idx < expectedResult.getPopularKeywords().size(); idx++) {
			assert expectedResult.getPopularKeywords().get(idx).getKeyword()
					.equals(actualResult.getPopularKeywords().get(idx).getKeyword());
			assert expectedResult.getPopularKeywords().get(idx).getFrequency()
					== actualResult.getPopularKeywords().get(idx).getFrequency();
		}
	}

}
