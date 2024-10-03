package com.example.oauth2_01.user.controller;


import com.example.oauth2_01.global.response.CustomResponseBody;
import com.example.oauth2_01.global.response.ResponseUtil;
import com.example.oauth2_01.user.dto.request.UserRequestDto;
import com.example.oauth2_01.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userServcie;

    @PostMapping("/sign-up")
    public ResponseEntity<CustomResponseBody<String>> signUp(@RequestBody UserRequestDto.UserSignUpDto userSignUpDto ){
       //"회원가입 성공"
        userServcie.signUp(userSignUpDto);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse("회원가입성공"));
    }

    @GetMapping("/jwt-test")
    public ResponseEntity<CustomResponseBody<String>> jwtTest() {
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse("jwtTest 요청 성공"));
    }

}
