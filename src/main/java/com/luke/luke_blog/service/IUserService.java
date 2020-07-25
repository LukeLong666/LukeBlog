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
    User checkUser(HttpServletRequest request,HttpServletResponse response);
}
