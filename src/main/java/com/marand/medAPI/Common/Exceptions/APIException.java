package com.marand.medAPI.Common.Exceptions;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;

public class APIException extends RuntimeException {

  private long timestamp = new Date().getTime();
  private int status;
  private String message;
  private String url;
  private Map<String, List<String>> validationErrors;

  public APIException() {}

  private APIException(APIExceptionBuilder builder) {
    this.timestamp = builder.timestamp;
    this.status = builder.status;
    this.message = builder.message;
    this.url = builder.url;
    this.validationErrors = builder.validationErrors;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public String getUrl() {
    return url;
  }

  public Map<String, List<String>> getValidationErrors() {
    return validationErrors;
  }

  public static class APIExceptionBuilder {
    private long timestamp = new Date().getTime();
    private int status;
    private String message;
    private String url;
    private Map<String, List<String>> validationErrors;

    public APIExceptionBuilder atTimestamp(long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public APIExceptionBuilder withStatus(int status) {
      this.status = status;
      return this;
    }

    public APIExceptionBuilder hasMessage(String message) {
      this.message = message;
      return this;
    }

    public APIExceptionBuilder atUrl(String url) {
      this.url = url;
      return this;
    }

    public APIExceptionBuilder withValidationErrors(Map<String, List<String>> validationErrors) {
      this.validationErrors = validationErrors;
      return this;
    }

    public APIExceptionBuilder withErrorMapFromException(BindingResult exceptionResult) {
      Map<String, List<String>> errorMap = new HashMap();

      getUniqueFields(exceptionResult).forEach((String fieldName) -> {
        errorMap.put(fieldName, getFieldErrors(exceptionResult, fieldName));
      });

      return withValidationErrors(errorMap);
    }

    public APIException build() {
      return new APIException(this);
    }

    private List<String> getFieldErrors(BindingResult exceptionResult, String key) {
      return exceptionResult.getFieldErrors(key).stream()
              .map(FieldError::getDefaultMessage)
              .collect(Collectors.toList());
    }


    private Set<String> getUniqueFields(BindingResult exceptionResult) {
      return exceptionResult.getFieldErrors().stream()
          .map(FieldError::getField)
          .collect(Collectors.toSet());
    }
  }
}
