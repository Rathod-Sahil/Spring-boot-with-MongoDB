package com.first.configuration;

import com.first.interceptors.JwtAuthenticationInterceptor;
import com.first.service.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final JwtAuthenticationInterceptor jwtAuthenticationInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthenticationInterceptor)
                .excludePathPatterns("/login", "/v3/api-docs/**", "/swagger-ui/**","/student/register");
    }
}
