package com.luke.luke_blog.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 饼干跑龙套
 *
 * @author zhang
 * @date 2020/07/24
 */
public class CookieUtils {

    public static final int default_age = 60*60*24*365;

    public static final String domain = "localhost";

    /**
     * 设置Cookie值
     *
     * @param response 响应
     * @param key      关键
     * @param value    价值
     */
    public static void setUpCookie(HttpServletResponse response, String key, String value) {
        setUpCookie(response,key,value,default_age);
    }

    public static void setUpCookie(HttpServletResponse response, String key, String value,int age) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setMaxAge(age);
        response.addCookie(cookie);
    }

    /**
     * 获取Cookie值
     *
     * @param request 请求
     * @param key     关键
     * @return {@link String}
     */
    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 删除Cookie
     *
     * @param request  请求
     * @param response 响应
     * @param key      关键
     */
    public static void deleteCookie(HttpServletRequest request,HttpServletResponse response, String key) {
        setUpCookie(response, key, null, 0);
    }
}
