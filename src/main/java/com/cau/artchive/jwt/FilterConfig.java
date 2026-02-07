package com.cau.artchive.jwt;


import com.cau.artchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilter() {
        FilterRegistrationBean<JwtAuthFilter> bean =
                new FilterRegistrationBean<>();

        bean.setFilter(new JwtAuthFilter(jwtProvider, userRepository));
        bean.addUrlPatterns("/api/*"); // API만 적용
        bean.setOrder(1);

        return bean;
    }
}

