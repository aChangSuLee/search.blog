package com.search.blog.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

  @Value("${source.blog.daum.url}")
  private String daumBlogUrl;

  @Value("${source.blog.daum.apiKey}")
  private String daumApiKey;

  @Bean
  public WebClient webClient() {

    return WebClient.builder()
        .baseUrl(daumBlogUrl)
        .clientConnector(
            new ReactorClientHttpConnector(
                HttpClient.create()
                    .responseTimeout(Duration.ofSeconds(3))
            )
        )
        .defaultHeader("Authorization", daumApiKey)
        .build();
  }
}
