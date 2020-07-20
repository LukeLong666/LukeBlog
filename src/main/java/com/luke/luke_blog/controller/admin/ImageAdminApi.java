package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * 图像管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/image")
public class ImageAdminApi {

    /**
     * 上传图片
     *
     * @return {@link ResponseResult}
     */
    @PostMapping
    public ResponseResult uploadImage() {
        return ResponseResult.success(null);
    }

    /**
     * 删除图片
     *
     * @param imageId 形象标识
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/{imageId}")
    public ResponseResult deleteImage(@PathVariable("imageId") String imageId) {
        return ResponseResult.success(null);
    }

    /**
     * 更新图片
     *
     * @param imageId 形象标识
     * @return {@link ResponseResult}
     */
    @PutMapping("/{imageId}")
    public ResponseResult updateImage(@PathVariable("imageId") String imageId) {
        return ResponseResult.success(null);
    }

    /**
     * 得到图像
     *
     * @param imageId 形象标识
     * @return {@link ResponseResult}
     */
    @GetMapping("/{imageId}")
    public ResponseResult getImage(@PathVariable("imageId") String imageId) {
        return ResponseResult.success(null);
    }

    /**
     * 图片列表
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ResponseResult}
     */
    @GetMapping("/list")
    public ResponseResult listImages(@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseResult.success(null);
    }
}
