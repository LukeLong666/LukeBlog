package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.pojo.Category;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.ICategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 类别管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryAdminApi {

    @Resource
    private ICategoryService categoryService;

    /**
     * 添加类别
     * 需要管理员权限
     * @param category 类别
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    /**
     * 删除类别
     *
     * @param categoryId 类别id
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/{categoryId}")
    public ResponseResult deleteCategory(@PathVariable("categoryId") String categoryId) {
        return ResponseResult.SUCCESS(null);
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
        return ResponseResult.SUCCESS(null);
    }

    /**
     * 得到类别
     *
     * @param categoryId 类别id
     * @return {@link ResponseResult}
     */
    @GetMapping("/{categoryId}")
    public ResponseResult getCategory(@PathVariable("categoryId") String categoryId) {
        return ResponseResult.SUCCESS(null);
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
        return ResponseResult.SUCCESS(null);
    }
}
