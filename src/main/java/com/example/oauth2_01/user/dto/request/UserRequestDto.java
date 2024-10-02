package com.example.oauth2_01.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequestDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSignUpDto{
        private String password;
        private String nickname;
        private String email;
        private int age;
    }
}
