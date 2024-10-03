package com.example.oauth2_01.global.response;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public final class FailedResponseBody extends CustomResponseBody<Void> {
    private final String msg;
    public FailedResponseBody(String code, String msg) {
        this.setCode(code);
        this.msg = msg;
    }

}
