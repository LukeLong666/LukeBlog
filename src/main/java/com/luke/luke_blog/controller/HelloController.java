package com.luke.luke_blog.controller;

import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
public class HelloController {

    public static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/hello")
    public ResponseResult hello() {
        User user = new User();
        user.setUserName("张鑫龙");
        log.info("haha");
        return ResponseResult.success("哈哈",user);
    }
}
