package com.luke.luke_blog.utils;

/**
 * 常量类
 *
 * @author zhang
 * @date 2020/07/21
 */
public interface Constants {

    String FROM_PC = "p_";
    String FROM_MOBILE = "m_";

    int DEFAULT_SIZE = 30;

    interface Common{
        String ONE = "1";
        String ZERO = "0";
        String STATE_YES = "1";
        String STATE_NO = "0";
    }

    interface Number{
        int TEN = 10;
    }

    interface User {
        String SEND_EMAIL_TYPE_REGISTER = "register";
        String SEND_EMAIL_TYPE_UPDATE = "update";
        String SEND_EMAIL_TYPE_FORGET = "forget";
        int KEY_CAPTCHA_MIN_LENGTH = 13;
        String KEY_CAPTCHA_CONTENT = "key_captcha_content_";
        String KEY_EMAIL_CODE_CONTENT = "key_email_code_content_";
        String ROLES_ADMIN = "role_admin";
        String ROLES_NORMAL = "role_normal";
        String DEFAULT_AVATAR = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2133261431,2800577620&fm=26&gp=0.jpg";
        String DEFAULT_STATE = Common.STATE_YES;
        String KEY_EMAIL_SEND_ADDRESS = "key_email_send_address_";
        String KEY_EMAIL_SEND_IP = "key_email_send_ip_";
        String KEY_TOKEN = "key_token_";
        String COOKIE_TOKEN_KEY = "blog_token";
    }

    interface Settings {
        String MANAGER_ACCOUNT_INIT_STATE = "manager_account_init_state";
        String WEB_SIZE_TITLE =  "web_size_title";
        String WEB_SIZE_DESCRIPTION =  "web_size_description";
        String WEB_SIZE_KEYWORDS =  "web_size_keywords";
        String WEB_SIZE_VIEW_COUNT =  "web_size_view_count";

    }

    interface Page{
        int DEFAULT_PAGE = 1;
        int MIN_SIZE = 5;
    }

    interface ImageType {
        String PREFIX = "image/";
        String TYPE_JPG = "jpg";
        String TYPE_PNG = "png";
        String TYPE_JPEG = "jpeg";
        String TYPE_JPG_WITH_PREFIX = PREFIX + "jpg";
        String TYPE_PNG_WITH_PREFIX = PREFIX + "png";
        String TYPE_JPEG_WITH_PREFIX = PREFIX + "jpeg";
    }

    interface TimeValue {
        long HOUR_2 = 2 * 60 * 60 * 1000;

        int MIN = 60;
        int HOUR = 60 * MIN;
        int DAY = 24 * HOUR;
        int WEEK = 7 * DAY;
        int MONTH = 30 * DAY;
    }

    interface Article{
        Integer TITLE_MAX_LENGTH = 128;
        Integer SUMMARY_MAX_LENGTH = 256;
        String STATE_DELETE = "0";
        String STATE_PUBLISH = "1";
        String STATE_DRAFT = "2";
        String STATE_TOP = "3";
        String KEY_ARTICLE_CACHE = "key_article_cache_";
        String KEY_ARTICLE_VIEW_COUNT = "key_article_view_count_";
    }

    interface Comment{
        String STATE_DELETE = "0";
        String STATE_PUBLISH = "1";
        String STATE_TOP = "3";
        String KEY_COMMENT_FIRST_PAGE_CACHE = "key_comment_first_page_cache_";
    }
}
