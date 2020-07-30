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
    @PreAuthorize("@permission.admin()")
    public ResponseResult deleteCategory(@PathVariable("categoryId") String categoryId) {
        return categoryService.deleteCategory(categoryId);
    }


    /**
     * 更新类别
     *
     * @param categoryId 类别id
     * @param category   类别
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("/{categoryId}")
    public ResponseResult updateCategory(@PathVariable("categoryId") String categoryId,@RequestBody Category category) {
        return categoryService.updateCategory(categoryId,category);
    }

    /**
     * 获取单个类别
     * 需要管理员权限
     * @param categoryId 类别id
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/{categoryId}")
    public ResponseResult getCategory(@PathVariable("categoryId") String categoryId) {
        return categoryService.getCategory(categoryId);
    }

    /**
     * 类别列表
     *
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/list")
    public ResponseResult listCategories() {
        return categoryService.listCategories();
    }
}
