package com.luke.luke_blog.service;

import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;

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
}
