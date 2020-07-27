package com.luke.luke_blog.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 错误代码配置
 *
 * @author zhang
 * @date 2020/07/27
 */
@Configuration
public class ErrorCodeConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN,"/403"));
        registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/404"));
        registry.addErrorPages(new ErrorPage(HttpStatus.GATEWAY_TIMEOUT,"/504"));
        registry.addErrorPages(new ErrorPage(HttpStatus.HTTP_VERSION_NOT_SUPPORTED,"/505"));


    }
}
