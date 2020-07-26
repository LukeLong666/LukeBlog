package com.luke.luke_blog.controller.user;

import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户api
 *
 * @author zhang
 * @date 2020/07/21
 */
@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private IUserService userService;

    /**
     * 初始化管理员账号
     *
     * @return {@link ResponseResult}
     */
    @PostMapping("/admin_account")
    public ResponseResult initManagerAccount(@RequestBody User user, HttpServletRequest request) {
        return userService.initManagerAccount(user,request);
    }

    /**
     * 获取图灵验证码
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response,@RequestParam("captcha_key") String captchaKey) throws IOException {
        try{
            userService.createCaptcha(response,captchaKey);
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

    /**
     * 发送验证代码
     *
     * @param emailAddress 电子邮件地址
     * @return {@link ResponseResult}
     */
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(HttpServletRequest request,
                                         @RequestParam("type") String type,
                                         @RequestParam("email") String emailAddress) {
        System.out.println(emailAddress);
        return userService.sendEmail(type,request,emailAddress);
    }

    /**
     * 注册
     *
     * @param user 用户
     * @return {@link ResponseResult}
     */
    @PostMapping
    public ResponseResult register(@RequestBody User user,
                                   @RequestParam("email_code") String emailCode,
                                   @RequestParam("captcha_code") String captchaCode,
                                   @RequestParam("captcha_key") String captchaKey,
                                   HttpServletRequest request) {
        return userService.register(user,emailCode,captchaCode,captchaKey,request);
    }

    /**
     * 登录
     * 需要提交的数据
     * 1. 用户账号-用户名或者邮箱
     * 2. 密码
     * 3. 图灵验证码
     * 4. 图灵验证码key
     *
     * @param captcha    验证码
     * @param user       登录用户
     * @param captchaKey 验证码的关键
     * @return {@link ResponseResult}
     */
    // TODO: 2020/7/23 登录
    @PostMapping("/{captcha}/{captcha_key}")
    public ResponseResult login(@PathVariable("captcha_key") String captchaKey,
                                @PathVariable("captcha") String captcha,
                                @RequestBody User user,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        return userService.doLogin(captcha,captchaKey,user,request,response);
    }

    /**
     * 更新密码
     *
     * @param user   用户
     * @param userId 用户id
     * @return {@link ResponseResult}
     */
    @PutMapping("/password/{userId}")
    public ResponseResult updatePassword(@PathVariable("userId") String userId,@RequestBody User user) {
        return ResponseResult.success(null);
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return {@link ResponseResult}
     */
    @GetMapping("/{userId}")
    public ResponseResult getUserInfo(@PathVariable("userId") String userId) {
        return userService.getUserInfo(userId);
    }

    /**
     * 更新用户信息
     *
     * @param user   用户
     * @param userId 用户id
     * @return {@link ResponseResult}
     */
    @PutMapping("/{userId}")
    public ResponseResult updateUserInfo(@PathVariable("userId") String userId,@RequestBody User user) {
        return ResponseResult.success(null);
    }

    /**
     * 用户列表
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ResponseResult}
     */
    @GetMapping("/list")
    public ResponseResult listUsers(@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseResult.success(null);
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/{userId}")
    public ResponseResult deleteUser(@PathVariable("userId") String userId) {
        return ResponseResult.success(null);
    }
}
