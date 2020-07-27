package com.luke.luke_blog.controller.user;

import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IUserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return ResponseResult.SUCCESS(null);
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
     * 允许用户修改的内容
     * 1.头像 ()
     * 2.用户名 (唯一的)
     * 3.密码 (单独修改) {@link UserApi#updatePassword(String, User)}
     * 4.签名
     * 5.email (唯一的,单独修改)
     * @see
     * @param user   用户
     * @param userId 用户id
     * @return {@link ResponseResult}
     */
    @PutMapping("/{userId}")
    public ResponseResult updateUserInfo(HttpServletRequest request,HttpServletResponse response,
                                         @PathVariable("userId") String userId,@RequestBody User user) {
        return userService.updateUserInfo(request,response,userId,user);
    }

    /**
     * 用户列表
     * 需要管理员权限
     * @param page 页面
     * @param size 大小
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/list")
    public ResponseResult listUsers(HttpServletRequest request,HttpServletResponse response,
                                    @RequestParam("page") int page,@RequestParam("size") int size) {
        return userService.listUsers(request,response,page,size);
    }

    /**
     * 删除用户
     * 需要管理员权限
     * @param userId 用户id
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @DeleteMapping("/{userId}")
    public ResponseResult deleteUser(@PathVariable("userId") String userId,
                                     HttpServletRequest request,HttpServletResponse response) {
        //判断当前的操作用户
        // TODO: 2020/7/26 通过注解的方式控制权限
        return userService.deleteUserById(userId,request,response);
    }


    /**
     * 检查电子邮件是否重复
     *
     * @param email 电子邮件
     * @return {@link ResponseResult}
     */
    @ApiResponses({
            @ApiResponse(code = 20000,message = "表示当前邮箱已经注册"),
            @ApiResponse(code = 0,message = "表示当前邮箱没有注册")
    })
    @GetMapping("/email")
    public ResponseResult checkEmail(@RequestParam("email") String email) {
        return userService.checkEmail(email);
    }

    /**
     * 检查用户名是否重复
     *
     * @param userName 用户名
     * @return {@link ResponseResult}
     */
    @ApiResponses({
            @ApiResponse(code = 20000,message = "表示当前用户名已经注册"),
            @ApiResponse(code = 0,message = "表示当前用户名没有注册")
    })
    @GetMapping("/user_name")
    public ResponseResult checkUserName(@RequestParam("user_name") String userName) {
        return userService.checkUserName(userName);
    }
}
