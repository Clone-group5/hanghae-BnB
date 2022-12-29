package com.hanghae.hanghaebnb.users.dto;

import com.hanghae.hanghaebnb.users.entity.UsersRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseLoginUser {
    private String email;
    private String nickname;
    private UsersRoleEnum role;
}
