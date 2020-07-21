package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * 网页大小信息管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/web_size_info")
public class WebSizeInfoAdminApi {

    /**
     * 得到网络大小标题
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/title")
    public ResponseResult getWebSizeTitle() {
        return ResponseResult.success(null);
    }

    /**
     * 更新web大小标题
     *
     * @param title 标题
     * @return {@link ResponseResult}
     */
    @PutMapping("/title")
    public ResponseResult updateWebSizeTitle(@RequestParam("title") String title) {
        return ResponseResult.success(null);
    }

    /**
     * 得到搜索引擎优化信息
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/seo")
    public ResponseResult getSeoInfo() {
        return ResponseResult.success(null);
    }

    @PutMapping("/seo")
    public ResponseResult putSeoInfo(@RequestParam("keywords") String keywords, @RequestParam("description") String description) {
        return ResponseResult.success(null);
    }

    @GetMapping("/view_count")
    public ResponseResult getWebSizeViewCount() {
        return ResponseResult.success(null);
    }
}
