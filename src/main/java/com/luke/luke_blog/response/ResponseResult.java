package com.luke.luke_blog.response;

/**
 * 统一响应结果
 *
 * @author zhang
 * @date 2020/07/20
 */
public class ResponseResult {

    private Boolean success;
    private Integer code;
    private String message;
    private Object data;

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
}
