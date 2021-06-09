package com.luke.luke_blog.service.impl;

import com.luke.luke_blog.utils.EmailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class TaskService {

    @Async
    public void sendEmailVerifyCode(String code, String emailAddress) throws Exception {
        EmailSender.sendRegisterVerifyCode(code, emailAddress);
    }
}
