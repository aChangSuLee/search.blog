package com.search.blog.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "블로그 검색 API",
        description = "블로그 검색 관련 API 명세",
        version = "v1"
    )
)
@Configuration
public class OpenApiConfig {
}
