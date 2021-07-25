package com.marand.medAPI.Common.Exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class APIExceptionTest {

    private APIException exception;
    long timeStamp = new Date().getTime();
    String message = "message";
    String url = "url";
    Map<String, String> validationErrors;

    @BeforeEach
    private void APIExceptionTestSetup() {
        exception = new APIException.APIExceptionBuilder().build();
    }


    @Test
    void whenConstructed_HasSetTimeStamp() {
        assertTrue(exception.getTimestamp() <= new Date().getTime());
    }

    @Test
    void atTimeStamp_setsTimeStamp() {
        assertEquals(timeStamp, new APIException.APIExceptionBuilder().atTimestamp(timeStamp).build().getTimestamp());
    }
}