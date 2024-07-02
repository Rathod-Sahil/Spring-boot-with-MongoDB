package com.first.interceptors;

import com.first.annotations.Access;
import com.first.decorator.RequestSession;
import com.first.enums.Role;
import com.first.exception.TokenExpiredException;
import com.first.exception.UnauthorizedException;
import com.first.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;
    private final RequestSession requestSession;


    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        Access access = ((HandlerMethod) handler).getMethodAnnotation(Access.class);
        assert access != null;
        if(Arrays.toString(access.role()).contains(Role.ANONYMOUS.toString())){
            return true;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Missing authorization header");
        }

        jwt = authHeader.substring(7);

        jwtUtils.extractToken(jwt);

        if (jwtUtils.isTokenExpired()) {
            throw new TokenExpiredException("Token is expired");
        }

        Set<Role> collect = new HashSet<>(requestSession.getJwtUser().getRole());
        if (collect.contains(Role.ANONYMOUS.toString()) || Arrays.stream(access.role()).anyMatch(role -> collect.contains(role.toString()))) {
            return true;
        }

        throw new UnauthorizedException("You don't have rights to access this api");
    }
}