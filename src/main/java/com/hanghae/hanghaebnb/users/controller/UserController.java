package com.hanghae.hanghaebnb.users.controller;

import com.hanghae.hanghaebnb.common.dto.ResponseDto;
import com.hanghae.hanghaebnb.users.dto.RequestCreateUser;
import com.hanghae.hanghaebnb.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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
}
