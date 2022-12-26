package com.hanghae.hanghaebnb.users.dto;

import lombok.Getter;

@Getter
public class RequestCreateUser {
    private String email;

    private String nickname;

    private String password;
}
