package com.example.oauth2_01.user.service;


import com.example.oauth2_01.global.exception.CustomException;
import com.example.oauth2_01.global.exception.ExceptionType;
import com.example.oauth2_01.user.dto.request.UserRequestDto;
import com.example.oauth2_01.user.entity.User;
import com.example.oauth2_01.user.enums.Role;
import com.example.oauth2_01.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 자체 로그인 회원가입
     */
    public void signUp(UserRequestDto.UserSignUpDto userSignUpDto) {

        String email = userSignUpDto.getEmail();
        userRepository.findByEmail(email).ifPresent(e->{ throw new CustomException(ExceptionType.DUPLICATED_EMAIL);});

        String nickname = userSignUpDto.getNickname();
        userRepository.findByNickname(nickname).ifPresent(e->{ throw new CustomException(ExceptionType.DUPLICATED_NICKNAME);});

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .age(userSignUpDto.getAge())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder); // 받은 비밀번호를 인코딩해서 사용함
        userRepository.save(user);

    }
}
