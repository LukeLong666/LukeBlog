package com.luke.luke_blog.service;

import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 国际单位服务
 *
 * @author zhang
 * @date 2020/07/21
 */
public interface IUserService {



    /**
     * init经理账户
     *
     * @return {@link ResponseResult}
     */
    ResponseResult initManagerAccount(User user, HttpServletRequest request);

    /**
     * 获取验证码
     *
     * @param response   响应
     * @param captchaKey 验证码的关键
     */
    void createCaptcha(HttpServletResponse response, String captchaKey) throws IOException;

    /**
     * 发送电子邮件
     *
     * @param request      请求
     * @param emailAddress 电子邮件地址
     * @param type         类型
     * @return {@link ResponseResult}
     */
    ResponseResult sendEmail(String type,HttpServletRequest request, String emailAddress);

    /**
     * 注册
     *
     * @param user 用户
     * @return {@link ResponseResult}
     */
    ResponseResult register(User user,String emailCode,String captchaCode, String captchaKey,HttpServletRequest request);

    /**
     * 做登录
     *
     * @param captcha    验证码
     * @param captchaKey 验证码的关键
     * @param user       用户
     * @param request    请求
     * @param response   响应
     * @return {@link ResponseResult}
     */
    ResponseResult doLogin(String captcha, String captchaKey, User user, HttpServletRequest request, HttpServletResponse response);

    /**
     * 检查用户登陆状态
     *
     * @return {@link User}
     */
    User checkUser();

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return {@link ResponseResult}
     */
    ResponseResult getUserInfo(String userId);

    /**
     * 检查电子邮件是否重复
     *
     * @param email 电子邮件
     * @return {@link ResponseResult}
     */
    ResponseResult checkEmail(String email);

    /**
     * 检查用户名是否重复
     *
     * @param userName 用户名
     * @return {@link ResponseResult}
     */
    ResponseResult checkUserName(String userName);

    /**
     * 更新用户信息
     *
     * @param userId   用户id
     * @param user     用户
     * @param response 响应
     * @return {@link ResponseResult}
     */
    ResponseResult updateUserInfo(HttpServletRequest request,HttpServletResponse response,String userId, User user);

    /**
     * 删除用户的id
     *
     * @param userId   用户id
     * @param request  请求
     * @param response 响应
     * @return {@link ResponseResult}
     */
    ResponseResult deleteUserById(String userId, HttpServletRequest request, HttpServletResponse response);

    /**
     * 用户列表
     *
     * @param request  请求
     * @param response 响应
     * @param page     页面
     * @param size     大小
     * @return {@link ResponseResult}
     */
    ResponseResult listUsers(HttpServletRequest request, HttpServletResponse response, int page, int size);

    /**
     * 更新密码
     *
     * @param user       用户
     * @param verifyCode 验证代码
     * @return {@link ResponseResult}
     */
    ResponseResult updatePassword(User user, String verifyCode);

    /**
     * 更新电子邮件
     *
     * @param verifyCode 验证代码
     * @param email      电子邮件
     * @return {@link ResponseResult}
     */
    ResponseResult updateEmail(String verifyCode, String email);

    /**
     * 注销
     *
     * @return {@link ResponseResult}
     */
    ResponseResult logout();
}
