package com.luke.luke_blog.controller.portal;

import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 搜索门户api
 *
 * @author zhang
 * @date 2020/07/21
 */
@RestController
@RequestMapping("/portal/search")
public class SearchPortalApi {

    /**
     * 做搜索
     *
     * @param keyword 关键字
     * @param page    页面
     * @return {@link ResponseResult}
     */
    @GetMapping()
    public ResponseResult doSearch(@RequestParam("keyword") String keyword,@RequestParam("page") int page) {
        return ResponseResult.SUCCESS(null);
    }
}
