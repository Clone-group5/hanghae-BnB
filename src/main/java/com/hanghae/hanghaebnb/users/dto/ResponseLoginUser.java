package com.hanghae.hanghaebnb.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseLoginUser {
    private String email;
    private String nickname;
}
