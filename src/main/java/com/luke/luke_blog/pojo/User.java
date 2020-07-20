package com.luke.luke_blog.pojo;

import java.io.Serializable;

/**
 * 用户
 *
 * @author zhang
 * @date 2020/07/20
 */
public class User implements Serializable {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
