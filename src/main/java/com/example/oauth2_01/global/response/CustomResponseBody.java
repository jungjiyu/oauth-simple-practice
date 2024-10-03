package com.example.oauth2_01.global.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public sealed abstract class CustomResponseBody<T> permits SuccessResponseBody, FailedResponseBody {
    private String code;
}