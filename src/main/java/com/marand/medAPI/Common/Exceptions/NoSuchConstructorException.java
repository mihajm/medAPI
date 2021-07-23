package com.marand.medAPI.Common.Exceptions;

public class NoSuchConstructorException extends RuntimeException {

  public NoSuchConstructorException() {
    super("No constructor with input parameter types found");
  }

  public NoSuchConstructorException(String message) {
    super(message);
  }

  public NoSuchConstructorException(Throwable e) {
    super("No constructor with input parameter types found", e);
  }

  public NoSuchConstructorException(String message, Throwable e) {
    super(message, e);
  }
}
