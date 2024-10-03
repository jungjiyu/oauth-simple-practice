package com.example.oauth2_01.global.response;

import com.example.oauth2_01.global.exception.ExceptionType;

public class ResponseUtil {

    public static CustomResponseBody<Void> createSuccessResponse() {
        return new SuccessResponseBody<>("200");
    }

    public static <T> CustomResponseBody<T> createSuccessResponse(T data) {
        return new SuccessResponseBody<>("200" ,data);
    }


    public static CustomResponseBody<Void> createFailureResponse(ExceptionType exceptionType) {
        return new FailedResponseBody(
                exceptionType.getCode(),
                exceptionType.getMessage()
        );
    }

    public static CustomResponseBody<Void> createFailureResponse(ExceptionType exceptionType, String customMessage) {
        return new FailedResponseBody(
                exceptionType.getCode(),
                customMessage
        );
    }


}
