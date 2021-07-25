package com.marand.medAPI.Common.ExceptionHandlers;

import com.marand.medAPI.Common.Exceptions.APIException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

public class ControllerExceptionHandler {

  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  APIException handleValidationException(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    return new APIException.APIExceptionBuilder()
        .withStatus(400)
        .hasMessage("ValidationError")
        .atUrl(request.getServletPath())
        .withErrorMapFromException(exception.getBindingResult())
        .build();
  }

  @ExceptionHandler({EntityNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  APIException handleNotFoundException(
      EntityNotFoundException exception, HttpServletRequest request) {
    return new APIException.APIExceptionBuilder()
        .withStatus(404)
        .hasMessage("No entity with this id found")
        .atUrl(request.getServletPath())
        .build();
  }
}
