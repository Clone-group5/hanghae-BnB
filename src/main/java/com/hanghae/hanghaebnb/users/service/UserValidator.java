package com.hanghae.hanghaebnb.users.service;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidator {
    public boolean validEmail(String email) {
        final String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        return Pattern.matches(regex, email);
    }

    public boolean validPassword(String password) {
        // "비밀번호는 영문, 숫자, 특수문자가 모두 포함된 8~16자리로 작성해주세요."
        final String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[a-zA-Z\\d~!@#$%^&*()+|=]{8,16}$";
        return Pattern.matches(regex, password);
    }
}
