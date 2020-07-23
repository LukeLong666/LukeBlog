package com.luke.luke_blog.utils;

/**
 * 常量类
 *
 * @author zhang
 * @date 2020/07/21
 */
public interface Constants {

    int DEFAULT_SIZE = 30;

    interface User{
        String KEY_CAPTCHA_CONTENT = "key_captcha_content_";
        String KEY_EMAIL_CODE_CONTENT = "key_email_code_content_";
        String ROLES_ADMIN = "role_admin";
        String ROLES_NORMAL = "role_normal";
        String DEFAULT_AVATAR = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2133261431,2800577620&fm=26&gp=0.jpg";
        String DEFAULT_STATE = "1";
        String KEY_EMAIL_SEND_ADDRESS = "key_email_send_address_";
        String KEY_EMAIL_SEND_IP = "key_email_send_ip_";
    }

    interface Settings{
        String MANAGER_ACCOUNT_INIT_STATE = "manager_account_init_state";
    }
}
