package com.marand.medAPI.Common.ExceptionHandlers;

import com.marand.medAPI.Common.Exceptions.APIException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

public class ControllerExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({MethodArgumentNotValidException.class})
  APIException handleValidationException(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    return new APIException.APIErrorBuilder()
        .withStatus(400)
        .hasMessage("ValidationError")
        .atUrl(request.getServletPath())
        .withErrorMapFromException(exception.getBindingResult())
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({IllegalArgumentException.class})
  APIException handleIllegalArgumentException(
      IllegalArgumentException exception, HttpServletRequest request) {
    return new APIException.APIErrorBuilder()
        .withStatus(400)
        .hasMessage(exception.getMessage())
        .atUrl(request.getServletPath())
        .build();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({EntityNotFoundException.class})
  APIException handleNotFoundException(
      EntityNotFoundException exception, HttpServletRequest request) {
    return new APIException.APIErrorBuilder()
        .withStatus(404)
        .hasMessage("No entity with this id found")
        .atUrl(request.getServletPath())
        .build();
  }
}
