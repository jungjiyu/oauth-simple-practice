package com.example.oauth2_01.global.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public final class SuccessResponseBody<T> extends CustomResponseBody<T>{
    private final T data;
    public SuccessResponseBody(String code) {
        this.setCode(code);
        data = null;
    }


    public SuccessResponseBody(String code, T data){
        this.setCode(code);
        this.data = data;
    }
}
