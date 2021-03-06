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

    /**
     * 得到类别
     *
     * @param categoryId 类别id
     * @return {@link ResponseResult}
     */
    ResponseResult getCategory(String categoryId);

    /**
     * 类别列表
     *
     * @return {@link ResponseResult}
     */
    ResponseResult listCategories();

    /**
     * 更新类别
     *
     * @param categoryId 类别id
     * @param category   类别
     * @return {@link ResponseResult}
     */
    ResponseResult updateCategory(String categoryId, Category category);

    /**
     * 删除类别
     *
     * @param categoryId 类别id
     * @return {@link ResponseResult}
     */
    ResponseResult deleteCategory(String categoryId);
}
