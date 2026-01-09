package com.cafe.coffeejava.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final String uploadPath;

    public WebMvcConfiguration(@Value("${file.directory}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ✅ 업로드 이미지용만 직접 처리
        registry.addResourceHandler("/pic/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // RestController 모든 URL에 /api prefix
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
    }
}