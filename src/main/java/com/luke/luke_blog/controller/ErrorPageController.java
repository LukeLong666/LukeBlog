package com.luke.luke_blog.controller;

import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 错误页面控制器
 *
 * @author zhang
 * @date 2020/07/27
 */
@RestController
public class ErrorPageController {

    @RequestMapping("/403")
    public ResponseResult page403() {
        return ResponseResult.FAILURE(40003, "权限不足", null);
    }

    @RequestMapping("/404")
    public ResponseResult page404() {
        return ResponseResult.FAILURE(40004, "页面丢失", null);
    }

    @RequestMapping("/500")
    public ResponseResult page500() {
        return ResponseResult.FAILURE(50000, "系统繁忙,请稍后重试", null);
    }

    @RequestMapping("/504")
    public ResponseResult page504() {
        return ResponseResult.FAILURE(50004, "系统繁忙,请稍后重试", null);
    }

    @RequestMapping("/505")
    public ResponseResult page505() {
        return ResponseResult.FAILURE(50005, "请求错误,请检查参数", null);
    }
}
