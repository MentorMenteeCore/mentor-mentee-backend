package com.mentormentee.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalControllerAdvice {
    /**
     * 커스텀한 예외들만 처리하는 Exception Handler입니다.
     * @return ResponseEntity
     */
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<?> customError(CustomException e, WebRequest request) {
        ExceptionResponse exceptionResponse
                = new ExceptionResponse(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatusCode.valueOf(e.getStatus()));
    }

    /**
     * 커스텀하지 못한 예외들을 처리하는 Exception Handler입니다.
     * @return ResponseEntity
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> globalError(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}