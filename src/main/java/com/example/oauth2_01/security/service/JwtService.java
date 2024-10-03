package com.example.oauth2_01.security.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.oauth2_01.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class JwtService {

    @Value("{jwt.secretkey}")
    private String secretKey;

    @Value("{jwt.access.expiration}")
    private Long accessTokenExpiration;

    @Value("jwt.access.header")
    private String accessTokenHeader;

    @Value("jwt.refresh.expiration")
    private Long refreshTokenExpiration;

    @Value("jwt.refresh.header")
    private String refreshTokenHeader;


    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM_KEY = "email";
    private static final String BEARER = "Bearer ";

    private final UserRepository userRepository;

    /**
     * AccessToken 생성 메서드
     * email 만 클레임으로 넣어 생성
     */
    public String createAccessToken(String email){
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + accessTokenExpiration);
        return JWT.create() // JWT 토큰을 생성하는 빌더 반환
                .withSubject(ACCESS_TOKEN_SUBJECT) // 토큰의 표준클레임 (subject) 지정
                .withExpiresAt(expireDate) // 토큰 만료시간 지정
                .withClaim(EMAIL_CLAIM,email) //  key와value 쌍의 클레임 지정, 원한다면 여러개 추가 가능
                .sign(Algorithm.HMAC512(secretKey)); // HMAC512 알고리즘 & 서버의 secret key 로 토큰 암호화

    }

    /**
     * RefreshToken 생성 메서드
     * 별도의 클레임은 안넣음
     */
    public String createRefreshToken(){
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + refreshTokenExpiration);
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     *  AccessToken 헤더 설정
     */
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken){
        response.setHeader(accessTokenHeader, accessToken);
    }

    /**
     * RefreshToken 헤더 설정
     */
    public void setRefreshTokenHeader(HttpServletResponse response , String refreshToken){
        response.setHeader(refreshTokenHeader, refreshToken);
    }

    /**
     * AccessToken 전송을 위한 헤더 설정
     */
    public void sendAccessToken(HttpServletResponse response , String accessToken){
        response.setStatus(HttpServletResponse.SC_OK); // SC_OK == 200
        this.setAccessTokenHeader(response, accessToken);
    }

    /**
     * AccessToken + Refresh Token 전송을 위한 헤더 설정
     */
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken){
        response.setStatus(HttpServletResponse.SC_OK);
        this.setAccessTokenHeader(response, accessToken);
        this.setRefreshTokenHeader(response, refreshToken);
    }


    /**
     * request 헤더에서 AccessToken을 추출 - Bearer XXX 에서 Bearer 를 제외한 부분 추출
     */
    public Optional<String> extractEmail(HttpServletRequest request){
        return  Optional.ofNullable(request.getHeader(accessTokenHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken->refreshToken.replace(BEARER , ""));

    }


    /**
     * request 헤더에서 refreshToken을 추출
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request){
        return  Optional.ofNullable(request.getHeader(refreshTokenHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken->refreshToken.replace(BEARER , ""));
    }
    
    /**
     * AccessToken 에서 Email 추출
     */
    public Optional<String> extractEmail(String accessToken) {

            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build() // 반환된 빌더로 JWT verifier 생성
                    .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
                    .getClaim(EMAIL_CLAIM_KEY) // claim(Emial) 가져오기
                    .asString());
    }









}
