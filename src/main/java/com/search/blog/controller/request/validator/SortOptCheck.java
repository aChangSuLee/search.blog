package com.search.blog.controller.request.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SortValidator.class)
public @interface SortOptCheck {
  String message() default "Illegal Arguments";
  Class[] groups() default {};
  Class[] payload() default {};
}
