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
     * 注册
     *
     * @param user 用户
     * @return {@link ResponseResult}
     */
    // TODO: 2020/7/23 注册
    @PostMapping
    public ResponseResult register(@RequestBody User user) {
        //检查当前用户名是否已经注册
        //检查邮箱格式是否正确
        //检查邮箱是否已经注册
        //检查邮箱验证码是否正确
        //检查图灵验证码是否正确
        //密码加密
        //补全数据
        //保存到数据库
        //返回结果
        return ResponseResult.success(null);
    }

    /**
     * 登录
     *
     * @param captcha 验证码
     * @param user    登录用户
     * @return {@link ResponseResult}
     */
    // TODO: 2020/7/23 登录
    @PostMapping("/{captcha}")
    public ResponseResult login(@PathVariable("captcha") String captcha,@RequestBody User user) {
        return ResponseResult.success(null);
    }


    /**
     * 发送验证代码
     *
     * @param emailAddress 电子邮件地址
     * @return {@link ResponseResult}
     */
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(HttpServletRequest request,@RequestParam("email") String emailAddress) {
        System.out.println(emailAddress);
        return userService.sendEmail(request,emailAddress);
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
