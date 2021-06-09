package com.luke.luke_blog.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TextUtilsTest {

    @Test
    public void isAllNum() {
        String str1 = "135";
        String str2 = "";
        String str3 = "你好";
        String str4 = "带01";
        System.out.println(TextUtils.isAllNumber(str1));
        System.out.println(TextUtils.isAllNumber(str2));
        System.out.println(TextUtils.isAllNumber(str3));
        System.out.println(TextUtils.isAllNumber(str4));
    }

    @Test
    public void isChinese() {
        String str1 = "0135";
        String str2 = "";
        String str3 = "你好";
        String str4 = "带01";
        System.out.println(TextUtils.isChinese(str1));
        System.out.println(TextUtils.isChinese(str2));
        System.out.println(TextUtils.isChinese(str3));
        System.out.println(TextUtils.isChinese(str4));
    }

    @Test
    public void isEmpty() {
        String str1 = "0135";
        String str2 = "";
        String str3 = "你好";
        String str4 = "带01";
        System.out.println(TextUtils.isEmpty(str1));
        System.out.println(TextUtils.isEmpty(str2));
        System.out.println(TextUtils.isEmpty(str3));
        System.out.println(TextUtils.isEmpty(str4));
    }

    @Test
    public void isEmailAddress() {
        String str1 = "0135";
        String str2 = "";
        String str3 = "1251489@qq.com";
        String str4 = "16awo@aw.dq.";
        System.out.println(TextUtils.isEmailAddress(str1));
        System.out.println(TextUtils.isEmailAddress(str2));
        System.out.println(TextUtils.isEmailAddress(str3));
        System.out.println(TextUtils.isEmailAddress(str4));
    }

    @Test
    public void isPhoneNumber() {

    }

    @Test
    public void passwordCheck() {
    }

    @Test
    public void isWordLowAndUp() {
    }

    @Test
    public void isWordLow() {
    }

    @Test
    public void isWordUp() {
    }
}