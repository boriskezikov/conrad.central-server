package com.conrad.centralserver.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ControllerExceptionInterceptor {

    @ResponseBody
    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorDTO handleApiException(EntityNotFoundException ex) {
        return new ErrorDTO(404, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorDTO handleApiException(Exception ex) {
        return new ErrorDTO(500, ex.getMessage());
    }

    @Data
    @AllArgsConstructor
    public static class ErrorDTO {
        private Integer code;
        private String message;
    }
}
