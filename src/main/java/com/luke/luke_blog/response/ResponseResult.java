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

    public static ResponseResult success(Integer code,String message,Object data) {
        return new ResponseResult(true,code,message,data);
    }

    public static ResponseResult success(String message,Object data) {
        return new ResponseResult(true,20000,message,data);
    }

    public static ResponseResult success(Object data) {
        return new ResponseResult(true,20000,"No message",data);
    }

    public static ResponseResult failure(Integer code,String message,Object data) {
        return new ResponseResult(false,code,message,data);
    }

    public static ResponseResult failure(String message,Object data) {
        return new ResponseResult(false,0,message,data);
    }

    public static ResponseResult failure(String message) {
        return new ResponseResult(false,0,message,null);
    }
}
