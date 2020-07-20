package com.luke.luke_blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring Boot 启动程序
 *
 * @author zhang
 * @date 2020/07/20
 */
@SpringBootApplication
@EnableSwagger2
public class LukeBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LukeBlogApplication.class, args);
    }

}
