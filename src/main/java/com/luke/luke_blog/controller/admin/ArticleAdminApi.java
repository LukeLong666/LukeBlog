package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.pojo.Article;
import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * 文章管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/article")
public class ArticleAdminApi {

    @PostMapping
    public ResponseResult postArticle(@RequestBody Article article) {
        return ResponseResult.SUCCESS(null);
    }

    @DeleteMapping("/{articleId}")
    public ResponseResult deleteArticle(@PathVariable("articleId") String articleId) {
        return ResponseResult.SUCCESS(null);
    }

    @PutMapping("/{articleId}")
    public ResponseResult updateArticle(@PathVariable("articleId") String articleId) {
        return ResponseResult.SUCCESS(null);
    }

    @GetMapping("/{articleId}")
    public ResponseResult getArticle(@PathVariable("articleId") String articleId) {
        return ResponseResult.SUCCESS(null);
    }

    @GetMapping("/list")
    public ResponseResult listArticles(@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseResult.SUCCESS(null);
    }

    @PutMapping("/state/{articleId}/{state}")
    public ResponseResult updateArticleState(@PathVariable("state") String state,@PathVariable("articleId") String articleId) {
        return ResponseResult.SUCCESS(null);
    }

    @PutMapping("/top/{articleId}")
    public ResponseResult updateArticleState(@PathVariable("articleId") String articleId) {
        return ResponseResult.SUCCESS(null);
    }
}
