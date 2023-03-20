package com.search.blog.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

  @Value("${source.blog.daum.url}")
  private String daumBlogUrl;

  @Value("${source.blog.daum.apiKey}")
  private String daumApiKey;

  @Value("${source.blog.naver.url}")
  private String naverBlogUrl;

  @Value("${source.blog.naver.client-id}")
  private String naverClientId;

  @Value("${source.blog.naver.client-secret}")
  private String naverClientSecret;

  HttpClient httpClient() {
    return HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .responseTimeout(Duration.ofMillis(5000))
        .doOnConnected(conn ->
            conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));
  }

  @Bean
  public WebClient webClient() {

    return WebClient.builder()
        .baseUrl(daumBlogUrl)
        .clientConnector(new ReactorClientHttpConnector(httpClient()))
        .defaultHeader("Authorization", daumApiKey)
        .build();
  }

  @Bean
  public WebClient daumBlogClient() {

    return WebClient.builder()
        .baseUrl(daumBlogUrl)
        .clientConnector(new ReactorClientHttpConnector(httpClient()))
        .defaultHeader("Authorization", daumApiKey)
        .build();
  }

  @Bean
  public WebClient naverBlogClient() {

    return WebClient.builder()
        .baseUrl(naverBlogUrl)
        .clientConnector(new ReactorClientHttpConnector(httpClient()))
        .defaultHeaders(headers -> {
          headers.set("X-Naver-Client-Id", naverClientId);
          headers.set("X-Naver-Client-Secret", naverClientSecret);
        })
        .build();
  }
}
