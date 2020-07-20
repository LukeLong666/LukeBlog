package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.pojo.Category;
import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * 类别管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryAdminApi {

    /**
     * 添加类别
     *
     * @param category 类别
     * @return {@link ResponseResult}
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category) {
        return ResponseResult.success(null);
    }

    /**
     * 删除类别
     *
     * @param categoryId 类别id
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/{categoryId}")
    public ResponseResult deleteCategory(@PathVariable("categoryId") String categoryId) {
        return ResponseResult.success(null);
    }


    /**
     * 更新类别
     *
     * @param categoryId 类别id
     * @param category   类别
     * @return {@link ResponseResult}
     */
    @PutMapping("/{categoryId}")
    public ResponseResult updateCategory(@PathVariable("categoryId") String categoryId,@RequestBody Category category) {
        return ResponseResult.success(null);
    }

    /**
     * 得到类别
     *
     * @param categoryId 类别id
     * @return {@link ResponseResult}
     */
    @GetMapping("/{categoryId}")
    public ResponseResult getCategory(@PathVariable("categoryId") String categoryId) {
        return ResponseResult.success(null);
    }

    /**
     * 类别列表
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ResponseResult}
     */
    @GetMapping("/list")
    public ResponseResult listCategories(@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseResult.success(null);
    }
}
