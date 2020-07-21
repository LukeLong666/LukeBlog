package com.luke.luke_blog.controller.portal;

import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * 文章门户api
 *
 * @author zhang
 * @date 2020/07/21
 */
@RestController
@RequestMapping("/portal/article")
public class ArticlePortalApi {

    /**
     * 文章列表
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ResponseResult}
     */
    @GetMapping("/list/{page}/{size}")
    public ResponseResult listArticle(@PathVariable("page") int page, @PathVariable("size") int size) {
        return ResponseResult.success(null);
    }

    /**
     * 文章通过类别id列表
     *
     * @param categoryId 类别id
     * @param page       页面
     * @param size       大小
     * @return {@link ResponseResult}
     */
    @GetMapping("/list/{categoryId}/{page}/{size}")
    public ResponseResult listArticleByCategoryId(@PathVariable("categoryId") String categoryId,@PathVariable("page") int page, @PathVariable("size") int size) {
        return ResponseResult.success(null);
    }

    /**
     * 获取文章的细节
     *
     * @param categoryId 类别id
     * @return {@link ResponseResult}
     */
    @GetMapping("/{articleId}")
    public ResponseResult getArticleDetail(@PathVariable("categoryId") String categoryId) {
        return ResponseResult.success(null);
    }

    /**
     * 得到推荐的文章
     *
     * @param categoryId 类别id
     * @return {@link ResponseResult}
     */
    @GetMapping("/recommend/{articleId}")
    public ResponseResult getRecommendArticle(@PathVariable("categoryId") String categoryId) {
        return ResponseResult.success(null);
    }
}
