package com.search.blog.controller.request.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

public class SortValidator implements ConstraintValidator<SortOptCheck, String> {

  private final Set<String> allowedOption = new HashSet<>();
  {
    allowedOption.add("accuracy");
    allowedOption.add("recency");
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return allowedOption.contains(value);
  }
}
