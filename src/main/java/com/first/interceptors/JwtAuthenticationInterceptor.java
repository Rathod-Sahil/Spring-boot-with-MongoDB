package com.first.interceptors;

import com.first.exception.UnauthorizedException;
import com.first.exception.TokenExpiredException;
import com.first.service.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Missing authorization header");
        }

        jwt = authHeader.substring(7);

        jwtUtils.extractToken(jwt);

        if(jwtUtils.isTokenExpired()) {
            throw new TokenExpiredException();
        }
        return true;
    }
}