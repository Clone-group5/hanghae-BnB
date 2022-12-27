package com.hanghae.hanghaebnb.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.hanghaebnb.common.dto.ResponseDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request);
        String uri = request.getRequestURI();
        String method = request.getMethod();

        if (token != null) {
            if (!jwtUtil.validateToken(token)) {
                jwtExceptionHandler(response, "로그인 상태를 확인해주세요.", HttpStatus.UNAUTHORIZED.value());
                return;
            }
            Claims claims = jwtUtil.getUserInfoFromToken(token);
            setAuthentication(claims.getSubject());
        }

        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String email) { // TODO. email 을 받아옴.
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
        // 과정을 거치고 다음 필터로 이동했을 때 이 요청은 인증이 되었다고 Security 에서 판단하고 Controller 로 요청이 넘어감.
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new ResponseDto<>(statusCode, msg, null));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
