package com.luke.luke_blog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author zxl
 * @date 2021/06/09
 */
public class TextUtils {

    public static boolean isEmpty(String text) {
        return text==null||text.length()==0;
    }

    public static boolean isEmailAddress(String str) {
        String regEx = "/^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$/";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isPhoneNumber(String str) {
        String regEx = "/^1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}$/";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isAllNumber(String str) {
        String regEx = "/^[0-9]*$/";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 密码检查
     * 正确格式为：以字母开头，长度在6-18之间，只能包含字符、数字和下划线。
     *
     * @param str 密码
     * @return boolean 是否符合要求
     */
    public static boolean passwordCheck(String str) {
        String regEx = "/^[a-zA-Z]\\w{5,17}$/";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 是否只含大小写字母
     *
     * @param str str
     * @return boolean
     */
    public static boolean isWordLowAndUp(String str) {
        String regEx = "/^[A-Za-z]+$/";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 是否只含小写字母
     *
     * @param str str
     * @return boolean
     */
    public static boolean isWordLow(String str) {
        String regEx = "/^[a-z]+$/";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 是否只含小写字母
     *
     * @param str str
     * @return boolean
     */
    public static boolean isWordUp(String str) {
        String regEx = "/^[A-Z]+$/";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 验证字符串是否只包含汉字
     * 暂时有问题
     * @param str str
     * @return boolean
     */
    @Deprecated
    public static boolean isChinese(String str) {
        String regEx = "/^[\\u4e00-\\u9fa5],{0,}$/";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
