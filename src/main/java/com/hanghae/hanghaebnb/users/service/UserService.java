package com.hanghae.hanghaebnb.users.service;

import com.hanghae.hanghaebnb.common.exception.CustomException;
import com.hanghae.hanghaebnb.common.exception.ErrorCode;
import com.hanghae.hanghaebnb.common.jwt.JwtUtil;
import com.hanghae.hanghaebnb.users.dto.RequestCreateUser;
import com.hanghae.hanghaebnb.users.dto.RequestLoginUser;
import com.hanghae.hanghaebnb.users.dto.ResponseLoginUser;
import com.hanghae.hanghaebnb.users.entity.UsersRoleEnum;
import com.hanghae.hanghaebnb.users.entity.Users;
import com.hanghae.hanghaebnb.users.mapper.UsersMapper;
import com.hanghae.hanghaebnb.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UsersMapper usersMapper;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(RequestCreateUser requestCreateUser) {
        String email = requestCreateUser.getEmail();
        checkEmail(email);

        String nickname = requestCreateUser.getNickname();

        String password = requestCreateUser.getPassword();
        password = checkPassword(password);

        Users users = usersMapper.toUsers(email, nickname, password, UsersRoleEnum.USER);
        userRepository.save(users);
    }

    @Transactional
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

    @Transactional
    public String checkPassword(String password) {
        // password form validation
        boolean validPassword = userValidator.validPassword(password);
        if(!validPassword) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return passwordEncoder.encode(password);
    }

    @Transactional
    public ResponseLoginUser login(RequestLoginUser requestLoginUser, HttpServletResponse response) {
        String email = requestLoginUser.getEmail();
        String password = requestLoginUser.getPassword();

        Users users = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_MATCH_USER_INFO)
        );

        if(!passwordEncoder.matches(password, users.getPassword())) {
            throw new CustomException(ErrorCode.NOT_FOUND_MATCH_USER_INFO);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(users.getEmail(), users.getUserRole()));

        return new ResponseLoginUser(users.getEmail(), users.getNickname(), users.getUserRole());
    }
}
