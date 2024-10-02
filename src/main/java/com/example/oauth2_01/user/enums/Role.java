package com.example.oauth2_01.user.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public enum Role {
    // 스프링 시큐리티에서는 권한 코드에 항상 ROLE 접두사가 있어야..
    ADMIN("ROLE_ADMIN"),
    GUEST("ROLE_GUEST"), // OAuth 첫 로그인 시의 role
    USER("ROLE_USER");
    private final String key;
}
