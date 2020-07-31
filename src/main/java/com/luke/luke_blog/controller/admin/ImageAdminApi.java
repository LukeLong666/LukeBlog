package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IImageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 图像管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/image")
public class ImageAdminApi {

    @Resource
    private IImageService imageService;

    /**
     * 上传图片
     *
     * @param file 文件
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult uploadImage(@RequestParam("file")MultipartFile file) {
        return imageService.uploadImage(file);
    }

    /**
     * 删除图片
     *
     * @param imageId 形象标识
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @DeleteMapping("/{imageId}")
    public ResponseResult deleteImage(@PathVariable("imageId") String imageId) {
        return imageService.deleteImageById(imageId);
    }

    /**
     * 图片列表
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/list/{page}/{size}")
    public ResponseResult listImages(@PathVariable("page") int page,@PathVariable("size") int size) {
        return imageService.listImages(page,size);
    }
}
