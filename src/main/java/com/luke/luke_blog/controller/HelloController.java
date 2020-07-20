package com.luke.luke_blog.controller;

import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
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
    public ResponseResult hello() {
        User user = new User();
        return null;
    }
}
