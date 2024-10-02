package com.example.oauth2_01.user.entity;


import com.example.oauth2_01.user.enums.Role;
import com.example.oauth2_01.user.enums.SocialType;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="Users") // H2 db 에서는 user 가 예약어임
public class User {
// 자체 로그인
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;// USER, GUEST , ADMIN

// 소셜 로그인    
    @Enumerated(EnumType.STRING)
    private SocialType socialType;// KAKAO, NAVER, GOOGLE
    private String socialId; // 로그인한 소셜 상의 식별자 값 (일반 로그인인 경우 null)
    
    private String refreshToken; //리프레시 토큰
    
    
// 기타    
    private String email;
    private String imageUrl; // 프로필 이미지
    private int age;



}
