package com.platform.ppdbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                //.allowedOrigins("*")
                .allowedOrigins("*")
                .allowedMethods("*") // 기타 설정
                .allowedHeaders("*");
                //.allowCredentials(true);

    }
}
