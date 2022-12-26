package com.hanghae.hanghaebnb.users.mapper;

import com.hanghae.hanghaebnb.users.entity.UserRoleEnum;
import com.hanghae.hanghaebnb.users.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public Users toUsers(String email, String nickname, String password, UserRoleEnum userRole) {
        return Users.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .userRole(userRole)
                .build();
    }
}
