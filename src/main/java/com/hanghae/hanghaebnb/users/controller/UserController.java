package com.hanghae.hanghaebnb.users.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.hanghaebnb.common.dto.ResponseDto;
import com.hanghae.hanghaebnb.users.dto.RequestCreateUser;
import com.hanghae.hanghaebnb.users.dto.RequestLoginUser;
import com.hanghae.hanghaebnb.users.dto.ResponseLoginUser;
import com.hanghae.hanghaebnb.users.service.KakaoService;
import com.hanghae.hanghaebnb.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;

    @PostMapping("/users/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody RequestCreateUser requestCreateUser) {
        userService.signup(requestCreateUser);
        return new ResponseEntity<>(new ResponseDto<>(200, "회원가입이 완료되었습니다.", null), HttpStatus.OK);
    }

    @PostMapping("/users/signup/email")
    public ResponseEntity<ResponseDto> checkEmail(@RequestBody RequestCreateUser requestCreateUser) {
        String email = requestCreateUser.getEmail();
        userService.checkEmail(email);
        return new ResponseEntity<>(new ResponseDto<>(200, "사용 가능한 이메일 입니다.", null), HttpStatus.OK);
    }

    @PostMapping("/users/login")
    public ResponseEntity<ResponseDto> login(@RequestBody RequestLoginUser requestLoginUser, HttpServletResponse response) {
        ResponseLoginUser responseLoginUser = userService.login(requestLoginUser, response);
        return new ResponseEntity<>(new ResponseDto<>(200, "로그인이 완료되었습니다.", responseLoginUser), HttpStatus.OK);
    }

    @PostMapping("/users/login/kakao")
    public ResponseEntity<ResponseDto> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        ResponseLoginUser responseLoginUser = kakaoService.kakaoLogin(code, response);
        return new ResponseEntity<>(new ResponseDto<>(200, "로그인이 완료되었습니다.", responseLoginUser), HttpStatus.OK);
    }
}
