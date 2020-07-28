package com.luke.luke_blog.service;

import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * iimage服务
 *
 * @author zhang
 * @date 2020/07/28
 */
public interface IImageService {
    /**
     * 上传图片
     *
     * @param file 文件
     * @return {@link ResponseResult}
     */
    ResponseResult uploadImage(MultipartFile file);

    /**
     * 显示图像
     *
     * @param response 响应
     * @param imageId  形象标识
     * @return {@link ResponseResult}
     */
    void viewImage(HttpServletResponse response, String imageId);

    /**
     * 图片列表
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ResponseResult}
     */
    ResponseResult listImages(int page, int size);

    /**
     * 删除图像通过id
     *
     * @param imageId 形象标识
     * @return {@link ResponseResult}
     */
    ResponseResult deleteImageById(String imageId);
}
