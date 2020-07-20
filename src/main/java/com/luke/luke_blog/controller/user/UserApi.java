package com.luke.luke_blog.controller.user;

import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserApi {

    /**
     * 初始化管理员账号
     *
     * @return {@link ResponseResult}
     */
    @PostMapping("/admin_account")
    public ResponseResult initManagerAccount(@RequestBody User user) {
        System.out.println(user);
        return ResponseResult.success(null);
    }

    /**
     * 注册
     *
     * @param user 用户
     * @return {@link ResponseResult}
     */
    @PostMapping("/")
    public ResponseResult register(@RequestBody User user) {

        return ResponseResult.success(null);
    }

    /**
     * 登录
     *
     * @param captcha 验证码
     * @param user    登录用户
     * @return {@link ResponseResult}
     */
    @PostMapping("/{captcha}")
    public ResponseResult login(@PathVariable("captcha") String captcha,@RequestBody User user) {

        return ResponseResult.success(null);
    }

    /**
     * 获取图灵验证码
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/captcha")
    public ResponseResult getCaptcha() {
        return ResponseResult.success(null);
    }

    /**
     * 发送验证代码
     *
     * @param emailAddress 电子邮件地址
     * @return {@link ResponseResult}
     */
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(@RequestParam("email") String emailAddress) {
        return ResponseResult.success(emailAddress);
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
        return ResponseResult.success(null);
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
