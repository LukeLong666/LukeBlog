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

    public static boolean isEmailAddress(String emailAddress) {
        String regEx = "/^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$/";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(emailAddress);
        return m.matches();
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        String regEx = "/^1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}$/";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public static boolean isAllNumber(String num) {
        String regEx = "/^[0-9]*$/";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(num);
        return m.matches();
    }
}
