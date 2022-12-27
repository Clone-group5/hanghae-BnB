package com.hanghae.hanghaebnb.common.security;

import com.hanghae.hanghaebnb.common.exception.CustomException;
import com.hanghae.hanghaebnb.common.exception.ErrorCode;
import com.hanghae.hanghaebnb.users.entity.Users;
import com.hanghae.hanghaebnb.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // TODO: 매개변수 이름 변경하기
        // 사용자 조회
        Users users = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.NOT_FOUND_MATCH_USER_INFO)
                );
        return new UserDetailsImpl(users, users.getEmail(), users.getPassword());
    }
}
