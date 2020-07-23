package com.luke.luke_blog

import com.luke.luke_blog.utils.EmailSender

class TestEmail {
}

fun main() {
    EmailSender.subject("测试邮件发送")
            ?.from("luke博客系统")
            ?.text("这是发送的内容:验证码:123456")
            ?.to("1258828871@qq.com")
            ?.send()
}