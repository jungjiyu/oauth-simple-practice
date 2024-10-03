package com.example.oauth2_01.global.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.oauth2_01.global.response.CustomResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.oauth2_01.global.exception.ExceptionType.UNAUTHORIZED_TOKEN;
import static com.example.oauth2_01.global.response.ResponseUtil.createFailureResponse;


@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomResponseBody<Void>> businessException(CustomException e) {
        ExceptionType exceptionType = e.getExceptionType();
        return ResponseEntity.status(exceptionType.getStatus())
                .body(createFailureResponse(exceptionType));
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<CustomResponseBody<Void>> handleJwtException(JWTVerificationException e) {
        return ResponseEntity
                .status(UNAUTHORIZED_TOKEN.getStatus())
                .body(createFailureResponse(ExceptionType.UNAUTHORIZED_TOKEN));
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponseBody<Void>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String customMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity
                .status(ExceptionType.BINDING_ERROR.getStatus())
                .body(createFailureResponse(ExceptionType.BINDING_ERROR, customMessage));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomResponseBody<Void>> dataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("DataIntegrityViolationException: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(createFailureResponse(ExceptionType.DUPLICATE_VALUE_ERROR));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponseBody<Void>> exception(Exception e) {
        log.error("Exception Message : {} ", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createFailureResponse(ExceptionType.UNEXPECTED_SERVER_ERROR));
    }
}


