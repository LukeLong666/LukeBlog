package com.luke.luke_blog;

import com.luke.luke_blog.utils.IdWorker;
import com.luke.luke_blog.utils.RedisUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Random;

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

    @Bean
    public IdWorker createIdWorker() {
        return new IdWorker(0, 0);
    }

    @Bean
    public BCryptPasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RedisUtil createRedisUtil() {
        return new RedisUtil();
    }

    @Bean
    public Random createRandom() {
        return new Random();
    }

}
