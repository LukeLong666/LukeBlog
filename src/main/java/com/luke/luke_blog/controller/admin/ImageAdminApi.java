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

    @PostMapping
    public ResponseResult uploadImage() {
        return ResponseResult.success(null);
    }

    @DeleteMapping("/{imageId}")
    public ResponseResult deleteImage(@PathVariable("imageId") String imageId) {
        return ResponseResult.success(null);
    }

    @PutMapping("/{imageId}")
    public ResponseResult updateImage(@PathVariable("imageId") String imageId) {
        return ResponseResult.success(null);
    }

    @GetMapping("/{imageId}")
    public ResponseResult getImage(@PathVariable("imageId") String imageId) {
        return ResponseResult.success(null);
    }

    @GetMapping("/list")
    public ResponseResult listImages(@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseResult.success(null);
    }
}
