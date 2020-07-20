package com.luke.luke_blog.controller;

import com.luke.luke_blog.pojo.User;
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

    @RequestMapping("/hello")
    public User hello() {
        User user = new User();
        user.setName("张鑫龙");
        user.setAge(22);
        return user;
    }
}
