package com.example.oauth2_01.user.service;


import com.example.oauth2_01.user.dto.request.UserRequestDto;
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

    public void signUp(UserRequestDto.UserSignUpDto userSignUpDto){

    }
}
