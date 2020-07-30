package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.pojo.Article;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IArticleService;
import com.luke.luke_blog.utils.Constants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 文章管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/article")
public class ArticleAdminApi {

    @Resource
    private IArticleService articleService;

    @PostMapping
    public ResponseResult postArticle(@RequestBody Article article) {
        return articleService.postArticle(article);
    }

    @PreAuthorize("@permission.admin()")
    @DeleteMapping("/{articleId}")
    public ResponseResult deleteArticle(@PathVariable("articleId") String articleId) {
        return articleService.deleteArticle(articleId);
    }

    @PutMapping("/{articleId}")
    public ResponseResult updateArticle(@PathVariable("articleId") String articleId,@RequestBody Article article) {
        return articleService.updateArticle(articleId, article);
    }

    @GetMapping("/{articleId}")
    public ResponseResult getArticle(@PathVariable("articleId") String articleId) {
        return articleService.getArticle(articleId);
    }

    @GetMapping("/list/{page}/{size}")
    public ResponseResult listArticles(@PathVariable("page") int page,
                                       @PathVariable("size") int size,
                                       @RequestParam(value = "keyword",required = false) String keyword,
                                       @RequestParam(value = "categoryId",required = false) String categoryId,
                                       @RequestParam(value = "state",required = false) String state) {
        return articleService.listArticle(page,size,keyword,categoryId,state);
    }

    @PreAuthorize("@permission.admin()")
    @PutMapping("/state/{articleId}/{state}")
    public ResponseResult updateArticleState(@PathVariable("state") String state, @PathVariable("articleId") String articleId) {
        Article article = new Article();
        article.setId(articleId);
        article.setState(state);
        return articleService.updateArticle(articleId, article);
    }

    @PreAuthorize("@permission.admin()")
    @PutMapping("/top/{articleId}")
    public ResponseResult topArticle(@PathVariable("articleId") String articleId) {
        Article article = new Article();
        article.setId(articleId);
        article.setState(Constants.Article.STATE_TOP);
        return articleService.updateArticle(articleId, article);
    }
}
