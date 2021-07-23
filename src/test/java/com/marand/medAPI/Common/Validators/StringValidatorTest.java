package com.marand.medAPI.Common.Validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValidatorTest {

  private static String testString = "";
  private static StringValidator validator;

  @BeforeEach
  private void setup() {
    validator = new StringValidator(testString);
  }

  @Test
  public void givenValidData_whenValidatorsCalled_DoesNotThrow() {
    assertDoesNotThrow(
            () -> {
              validator
                      .validateString("test")
                      .isNotNull()
                      .isNotEmpty()
                      .isNotBlank()
                      .hasDiseaseValidChars()
                      .isLongerThan(3)
                      .isShorterThan(5);
            });
  }

  @Test
  public void givenValidData_whenDefaultValidatorsCalled_DoesNotThrow() {
    assertDoesNotThrow(
            () -> {
              validator.validateString("test").validateDiseaseName();
            });
  }

  @Test
  public void givenValidData_whenDiseaseDefaultValidatorsCalled_ReturnsString() {
    String string = "test";
    assertEquals(string, validator.validateString("test").validateDiseaseName());
  }

  @Test
  public void whenGivenNullValue() throws IllegalArgumentException {
    assertThrows(
            IllegalArgumentException.class,
            () -> {
              validator.validateString(null).isNotNull();
            });
  }

  @Test
  public void whenGivenEmptyValue() throws IllegalArgumentException {
    assertThrows(
            IllegalArgumentException.class,
            () -> {
              validator.isNotEmpty();
            });
  }

  @Test
  public void whenGivenBlankValue() throws IllegalArgumentException {
    assertThrows(
            IllegalArgumentException.class,
            () -> {
              validator.validateString("  ").isNotBlank();
            });
  }

  @Test
  public void whenGivenUpperCase() throws IllegalArgumentException {
    assertThrows(
            IllegalArgumentException.class,
            () -> {
              validator.validateString("A").hasDiseaseValidChars();
            });
  }

  @Test
  public void whenGivenTooShortString() throws IllegalArgumentException {
    assertThrows(
            IllegalArgumentException.class,
            () -> {
              validator.validateString("aaa").isShorterThan(2);
            });
  }

  @Test
  public void whenGivenTooLongString() throws IllegalArgumentException {
    assertThrows(
            IllegalArgumentException.class,
            () -> {
              String string = "a";
              validator.validateString(string).isLongerThan(2);
            });
  }
}
