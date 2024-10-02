package com.example.oauth2_01.user.repository;

import com.example.oauth2_01.user.entity.User;
import com.example.oauth2_01.user.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickName(String nickname);
    Optional<User> findByRefreshToken(String refreshToken);

    /**
     * 소셜 타입과 소셜의 식별자값으로 회원을 찾는 메서드.
     * 소셜을 통한 기본적인 정보로의 회원가입 이후 추가정보(도시, 나이 등)을 입력받기 위해 필요
     */
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

}
