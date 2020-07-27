package com.luke.luke_blog.service.impl;

import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.service.IUserService;
import com.luke.luke_blog.utils.Constants;
import com.luke.luke_blog.utils.CookieUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 许可服务
 *
 * @author zhang
 * @date 2020/07/27
 */
@Service("permission")
public class PermissionService {

    @Resource
    private IUserService userService;

    /**
     * 判断是不是管理员
     *
     * @return boolean
     */
    public boolean admin() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        if (tokenKey == null) {
            return false;
        }
        User user = userService.checkUser(request, response);
        if (user == null) {
            return false;
        }
        if (Constants.User.ROLES_ADMIN.equals(user.getRoles())) {
            return true;
        }
        return false;
    }
}
