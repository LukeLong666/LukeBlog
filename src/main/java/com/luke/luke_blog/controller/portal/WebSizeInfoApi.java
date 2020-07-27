package com.luke.luke_blog.controller.portal;

import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * web大小信息api
 *
 * @author zhang
 * @date 2020/07/21
 */
@RestController
@RequestMapping("/portal/web_size_info")
public class WebSizeInfoApi {

    /**
     * 得到categores
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/categories")
    public ResponseResult getCategores() {
        return ResponseResult.SUCCESS(null);
    }

    /**
     * 获得冠军
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/title")
    public ResponseResult getTitle() {
        return ResponseResult.SUCCESS(null);
    }

    /**
     * 得到视图数
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/view_count")
    public ResponseResult getViewCount() {
        return ResponseResult.SUCCESS(null);
    }

    /**
     * 让搜索引擎优化
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/seo")
    public ResponseResult getSeo() {
        return ResponseResult.SUCCESS(null);
    }

    /**
     * 得到循环
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/loop")
    public ResponseResult getLoops() {
        return ResponseResult.SUCCESS(null);
    }

    /**
     * 得到链接
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/friend_link")
    public ResponseResult getLinks() {
        return ResponseResult.SUCCESS(null);
    }
}
