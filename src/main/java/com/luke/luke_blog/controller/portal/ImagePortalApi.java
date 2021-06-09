package com.luke.luke_blog.controller.portal;

import com.luke.luke_blog.service.IImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 形象门户api
 *
 * @author zxl
 * @date 2021/06/09
 */
@RestController
@RequestMapping("/portal/image")
public class ImagePortalApi {

    @Resource
    private IImageService imageService;

    /**
     * 得到图像
     *
     * @param response 响应
     * @param imageId  形象标识
     */
    @GetMapping("/{imageId}")
    public void getImage(HttpServletResponse response, @PathVariable("imageId") String imageId) {
        imageService.viewImage(response,imageId);
    }
}
