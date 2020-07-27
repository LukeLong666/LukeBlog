package com.luke.luke_blog.service;

import com.luke.luke_blog.pojo.Category;
import com.luke.luke_blog.response.ResponseResult;

/**
 * icategory服务
 *
 * @author zhang
 * @date 2020/07/27
 */
public interface ICategoryService {


    /**
     * 添加类别
     *
     * @param category 类别
     * @return {@link ResponseResult}
     */
    ResponseResult addCategory(Category category);
}
