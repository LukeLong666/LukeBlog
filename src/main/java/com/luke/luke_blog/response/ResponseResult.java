package com.luke.luke_blog.response;

/**
 * 统一响应结果
 *
 * @author zhang
 * @date 2020/07/20
 */
public class ResponseResult {

    private Boolean success;

    //操作结果码
    //错误 0
    //成功 20000
    //登陆成功 20001
    //注册成功 20002
    //权限不足 30000
    //未登录 10000
    private Integer code;
    private String message;
    private Object data;

    public ResponseResult(Boolean success, Integer code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResponseResult SUCCESS(Integer code, String message, Object data) {
        return new ResponseResult(true,code,message,data);
    }

    public static ResponseResult SUCCESS(String message, Object data) {
        return new ResponseResult(true,20000,message,data);
    }

    public static ResponseResult SUCCESS(Object data) {
        return new ResponseResult(true,20000,"No message",data);
    }

    public static ResponseResult FAILURE(Integer code, String message, Object data) {
        return new ResponseResult(false,code,message,data);
    }

    public static ResponseResult FAILURE(String message, Object data) {
        return new ResponseResult(false,0,message,data);
    }

    public static ResponseResult FAILURE(String message) {
        return new ResponseResult(false,0,message,null);
    }

    public static ResponseResult ACCOUNT_NOT_LOGIN() {
        return new ResponseResult(false,10000,null,null);
    }

    public static ResponseResult ACCOUNT_NOT_LOGIN(String message) {
        return new ResponseResult(false,10000,message,null);
    }

    public static ResponseResult PERMISSION_DENY(){
        return new ResponseResult(false,30000,null,null);
    }

    public static ResponseResult PERMISSION_DENY(String message){
        return new ResponseResult(false,30000,message,null);
    }
}
