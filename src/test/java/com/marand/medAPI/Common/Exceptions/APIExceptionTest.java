package com.marand.medAPI.Common.Exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class APIExceptionTest {

  private APIException exception;
  long timeStamp = new Date().getTime();
  int status = 404;
  String message = "message";
  String url = "url";
  List<String> errors = new ArrayList<>(List.of("error1", "error2"));
  Map<String, List<String>> validationErrors = new HashMap(Map.of("name", errors));

  @BeforeEach
  private void APIExceptionTestSetup() {
    exception =
        new APIException.APIExceptionBuilder()
            .atTimestamp(timeStamp)
            .withStatus(status)
            .hasMessage(message)
            .atUrl(url)
            .withValidationErrors(validationErrors)
            .build();
  }

  @Test
  void canBeConstructedWithoutArgs() {
    exception = new APIException();
    assertNotNull(exception);
  }

  @Test
  void whenConstructedWithoutArgs_hasTimestamp() {
    exception = new APIException();
    assertNotNull(exception.getTimestamp());
  }

  @Test
  void build_returnsAPIException() {
    assertNotNull(exception);
  }

  @Test
  void canBeConstructedWithNoArgsThroughBuilder() {
    assertNotNull(new APIException.APIExceptionBuilder().build());
  }

  @Test
  void getTimeStamp_GetsTimestamp() {
    assertEquals(timeStamp, exception.getTimestamp());
  }

  @Test
  void whenConstructed_HasSetTimeStamp() {
    assertTrue(exception.getTimestamp() <= new Date().getTime());
  }

  @Test
  void atTimeStamp_setsTimeStamp() {
    long timeStamp = new Date().getTime();
    assertEquals(
        timeStamp,
        new APIException.APIExceptionBuilder().atTimestamp(timeStamp).build().getTimestamp());
  }

  @Test
  void getStatus_getsStatus() {
    assertEquals(status, exception.getStatus());
  }

  @Test
  void withStatus_setsStatus() {
    int status = 400;
    assertEquals(
        status, new APIException.APIExceptionBuilder().withStatus(status).build().getStatus());
  }

  @Test
  void getMessage_getsMessage() {
    assertEquals(message, exception.getMessage());
  }

  @Test
  void hasMessage_setsMessage() {
    String message = "newMessage";
    assertEquals(
        message, new APIException.APIExceptionBuilder().hasMessage(message).build().getMessage());
  }

  @Test
  void getUrl_getsURL() {
    assertEquals(url, exception.getUrl());
  }

  @Test
  void atUrl_setsURL() {
    String url = "newURL";
    assertEquals(url, new APIException.APIExceptionBuilder().atUrl(url).build().getUrl());
  }

  @Test
  void getValidationErrors_getsValidationErrors() {
    assertEquals(validationErrors, exception.getValidationErrors());
  }

  @Test
  void withValidationErrors_setsValidationErrors() {
    List<String> errors = new ArrayList<>(List.of("error1", "error2"));
    Map<String, List<String>> validationErrors = new HashMap(Map.of("RuntimeException", errors));
    assertEquals(
        validationErrors,
        new APIException.APIExceptionBuilder()
            .withValidationErrors(validationErrors)
            .build()
            .getValidationErrors());
  }
}
