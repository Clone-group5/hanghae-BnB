package com.hanghae.hanghaebnb.users.mapper;

import com.hanghae.hanghaebnb.users.entity.UsersRoleEnum;
import com.hanghae.hanghaebnb.users.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {
    public Users toUsers(String email, String nickname, String password, UsersRoleEnum userRole) {
        return Users.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .userRole(userRole)
                .build();
    }
}
