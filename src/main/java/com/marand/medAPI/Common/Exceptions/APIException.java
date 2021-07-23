package com.marand.medAPI.Common.Exceptions;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;

public class APIException extends RuntimeException {

  private long timestamp = new Date().getTime();
  private int status;
  private String message;
  private String url;
  private Map<String, String> validationErrors;

  public APIException() {}

  private APIException(APIErrorBuilder builder) {
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

  public Map<String, String> getValidationErrors() {
    return validationErrors;
  }

  @Override
  public boolean equals(Object other) {
    if (this.getClass() != other.getClass()) return false;
    return reflectionEquals(this, other);
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public static class APIErrorBuilder {
    private long timestamp = new Date().getTime();
    private int status;
    private String message;
    private String url;
    private Map<String, String> validationErrors;

    public APIErrorBuilder atTimestamp(long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public APIErrorBuilder withStatus(int status) {
      this.status = status;
      return this;
    }

    public APIErrorBuilder hasMessage(String message) {
      this.message = message;
      return this;
    }

    public APIErrorBuilder atUrl(String url) {
      this.url = url;
      return this;
    }

    public APIErrorBuilder withValidationErrors(Map<String, String> validationErrors) {
      this.validationErrors = validationErrors;
      return this;
    }

    public APIErrorBuilder withErrorMapFromException(BindingResult exceptionResult) {
      return withValidationErrors(
          exceptionResult.getFieldErrors().stream()
              .collect(Collectors.toMap(FieldError::getField, this::getDefaultMessage)));
    }

    public APIException build() {
      return new APIException(this);
    }

    private String getDefaultMessage(FieldError error) {
      return Objects.requireNonNull(error.getDefaultMessage());
    }
  }
}
