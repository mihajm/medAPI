package com.marand.medAPI.Common.Validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class StringValidatorTest {

  private static String testString = "";
  private static StringValidator validator;

  @BeforeEach
  private void setup() {
    validator = new StringValidator(testString);
  }

  @Test
  public void canBeConstructedWithoutArgs() {
      assertDoesNotThrow((ThrowingSupplier<StringValidator>) StringValidator::new);
  }


    @Test
    public void whenConstructedWithTestString_hasStringSet() {
        assertEquals(testString, validator.getString());
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
    public void givenValidPatientData_whenValidatorsCalled_DoesNotThrow() {
        assertDoesNotThrow(
                () -> {
                    validator
                            .validateString("Miha")
                            .isNotNull()
                            .isNotEmpty()
                            .isNotBlank()
                            .hasPatientNameValidChars()
                            .isLongerThan(3)
                            .isShorterThan(5);
                });
    }

  @Test
  public void givenValidString_hasLengthBetweenReturnsString() {
      String validatedString = validator.validateString("ab").hasLengthBetween(1, 5);
      assertNotNull(validatedString);
      assertEquals("ab", validatedString);
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

    @Test
    public void givenLowerCaseFirstChar_hasPatientNameValidChars() throws IllegalArgumentException {
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    validator.validateString("a").hasPatientNameValidChars();
                });
    }

    @Test
    public void givenUpperCaseOtherChar_hasPatientNameValidChars() throws IllegalArgumentException {
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    validator.validateString("AB").hasPatientNameValidChars();
                });
    }

    @Test
    public void givenNonLetterChars_hasPatientNameValidChars() throws IllegalArgumentException {
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    validator.validateString("123").hasPatientNameValidChars();
                });
    }

    @Test
    public void transformToLower_TransformsStringToLowerCase() {
      String UpperCaseStr = "ABC";
        validator.validateString(UpperCaseStr).transformToLower();
        assertEquals(UpperCaseStr.toLowerCase(Locale.ROOT), validator.getString());
    }

    @Test
    public void transformToLower_TransformsStringToUpperCase() {
        String lowerCaseStr = "abc";
        validator.validateString(lowerCaseStr).transformToUpper();
        assertEquals(lowerCaseStr.toUpperCase(Locale.ROOT), validator.getString());
    }

    @Test
    public void transformFirstToUpper_TransformsFirstCharToUpperCase() {
        String name = "abc";
        validator.validateString(name).transformFirstToUpper();
        assertEquals("Abc", validator.getString());
    }

    @Test
    public void transformFirstToLower_TransformsFirstCharToLowerCase() {
        String name = "ABC";
        validator.validateString(name).transformFirstToLower();
        assertEquals("aBC", validator.getString());
    }

}
