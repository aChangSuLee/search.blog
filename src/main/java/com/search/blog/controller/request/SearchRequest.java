package com.search.blog.controller.request;

import com.search.blog.controller.request.validator.SortOptCheck;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor
public class SearchRequest {
  @Getter
  @NotBlank(message = "keyword parameter required")
  private String keyword;

  @Getter
  @Setter
  @Min(value = 1, message = "size is less than min")
  @Max(value = 50, message = "size is more than max")
  private Integer size = 10;

  @Getter
  @Setter
  @Min(value = 1, message = "page is less than min")
  @Max(value = 50, message = "page is more than max")
  private Integer page = 1;

  @Getter
  @Setter
  @SortOptCheck(message = "sort is not allowed option")
  private String sort = "accuracy";

  @Getter
  @Setter
  private String source = "daum";

  public void setKeyword (String keyword) {
    this.keyword = keyword.trim();
  }

  public Pageable of() {
    return PageRequest.of(this.page, this.size, Sort.by(this.sort));
  }
}
