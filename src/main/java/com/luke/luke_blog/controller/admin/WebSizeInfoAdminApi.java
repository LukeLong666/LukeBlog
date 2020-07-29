package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IWebSizeInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 网页大小信息管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/web_size_info")
public class WebSizeInfoAdminApi {

    @Resource
    IWebSizeInfoService webSizeInfoService;

    /**
     * 得到网络大小标题
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/title")
    public ResponseResult getWebSizeTitle() {
        return webSizeInfoService.getWebSizeTitle();
    }

    /**
     * 更新web大小标题
     *
     * @param title 标题
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("/title")
    public ResponseResult updateWebSizeTitle(@RequestParam("title") String title) {
        return webSizeInfoService.putWebSizeTitle(title);
    }

    /**
     * 得到搜索引擎优化信息
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/seo")
    public ResponseResult getSeoInfo() {
        return webSizeInfoService.getSeoInfo();
    }

    /**
     * 把seo信息
     *
     * @param keywords    关键字
     * @param description 描述
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("/seo")
    public ResponseResult putSeoInfo(@RequestParam("keywords") String keywords, @RequestParam("description") String description) {
        return webSizeInfoService.putSeoInfo(keywords,description);
    }

    @GetMapping("/view_count")
    public ResponseResult getWebSizeViewCount() {
        return webSizeInfoService.getWebSizeViewCount();
    }
}
