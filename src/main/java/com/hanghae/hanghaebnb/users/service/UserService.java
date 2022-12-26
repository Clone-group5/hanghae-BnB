package com.hanghae.hanghaebnb.users.service;

import com.hanghae.hanghaebnb.common.exception.CustomException;
import com.hanghae.hanghaebnb.common.exception.ErrorCode;
import com.hanghae.hanghaebnb.users.UserRepository;
import com.hanghae.hanghaebnb.users.dto.RequestCreateUser;
import com.hanghae.hanghaebnb.users.entity.UserRoleEnum;
import com.hanghae.hanghaebnb.users.entity.Users;
import com.hanghae.hanghaebnb.users.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    @Transactional
    public void signup(RequestCreateUser requestCreateUser) {
        String email = requestCreateUser.getEmail();
        checkEmail(email);

        String nickname = requestCreateUser.getNickname();

        String password = requestCreateUser.getPassword();
        checkPassword(password);

        Users users = userMapper.toUsers(email, nickname, password, UserRoleEnum.USER);
        userRepository.save(users);
    }

    public void checkEmail(String email) {
        // email form validation
        boolean validEmail = userValidator.validEmail(email);
        if (!validEmail) {
            throw new CustomException(ErrorCode.INVALID_EMAIL);
        }

        // duplicate email check
        userRepository.findByEmail(email).ifPresent(
                m -> {
                    throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
                }
        );
    }

    private void checkPassword(String password) {
        // password form validation
        boolean validPassword = userValidator.validPassword(password);
        if(!validPassword) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }
}
