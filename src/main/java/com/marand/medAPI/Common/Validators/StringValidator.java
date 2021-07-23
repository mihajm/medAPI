package com.marand.medAPI.Common.Validators;

import java.util.Locale;
import java.util.regex.Pattern;

public class StringValidator {

  private String string = "";
  private boolean valid = false;

  public StringValidator() {}

  public StringValidator(String string) {
    this.string = string;
  }

  public StringValidator(String string, boolean valid) {
    this.string = string;
    this.valid = valid;
  }

  private void throwException(String message) throws IllegalArgumentException {
    throw new IllegalArgumentException(message);
  }

  public StringValidator validateString(String string) {
    this.string = string;
    return this;
  }

  public StringValidator isNotNull() {
    if (string == null) throwException(string + " cannot be null");
    return this;
  }

  public StringValidator isNotEmpty() {
    if (string.isEmpty()) throwException(string + " cannot be empty");
    return this;
  }

  public StringValidator isNotBlank() {
    if (string.isBlank()) throwException(string + " cannot be blank");
    return this;
  }

  public StringValidator hasDiseaseValidChars() {
    return matches("[a-z-_]+");
  }

  public StringValidator hasPatientNameValidChars() {
    return matches("^[A-Z][a-z]+");
  }

  public StringValidator matches(String regex) {
    if (!Pattern.compile(regex).matcher(string).find())
      throwException(string + " must match regex: " + regex);
    return this;
  }

  public StringValidator isShorterThan(int max) {
    if (string.length() >= max)
      throwException(string + " must be shorter than " + String.valueOf(max));
    return this;
  }

  public StringValidator isLongerThan(int min) {
    if (string.length() <= min)
      throwException(string + " must be longer than " + String.valueOf(min));
    return this;
  }

  public void transformToLower() {
    string = string.toLowerCase(Locale.ROOT);
  }

  public void transformToUpper() {
    string = string.toUpperCase(Locale.ROOT);
  }

  public void transformFirstToUpper() {
    string = string.substring(0, 1).toUpperCase(Locale.ROOT) + string.substring(1);
  }

  public void transformFirstToLower() {
    string = string.substring(0, 1).toLowerCase(Locale.ROOT) + string.substring(1);
  }

  public String getString() {
    return string;
  }

  public String validateDiseaseName() {
    isValidString();
    transformToLower();
    hasDiseaseValidChars();
    return getString();
  }

  public String validatePatientName() {
    isValidString();
    transformToLower();
    transformFirstToUpper();
    hasPatientNameValidChars();
    return getString();
  }

  public String validateDepartment() {
    return validateDiseaseName();
  }

  public String isValidString() {
    hasValue();
    hasLengthBetween(1, 255);
    return getString();
  }

  public String hasLengthBetween(int min, int max) {
    isLongerThan(min);
    isShorterThan(max);
    return getString();
  }

  public String hasValue() {
    isNotNull();
    isNotEmpty();
    isNotBlank();
    return getString();
  }
}
