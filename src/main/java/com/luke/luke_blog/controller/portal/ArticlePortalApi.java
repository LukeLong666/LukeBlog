package com.luke.luke_blog.controller.portal;

import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IArticleService;
import com.luke.luke_blog.utils.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 文章门户api
 *
 * @author zhang
 * @date 2020/07/21
 */
@RestController
@RequestMapping("/portal/article")
public class ArticlePortalApi {

    @Resource
    private IArticleService articleService;

    /**
     * 文章列表
     * 已经发布的,置顶的另外一个接口获取
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ResponseResult}
     */
    @GetMapping("/list/{page}/{size}")
    public ResponseResult listArticle(@PathVariable("page") int page, @PathVariable("size") int size) {
        return articleService.listArticle(page, size, null, null, Constants.Article.STATE_PUBLISH);
    }

    @GetMapping("/list/label/{label}/{page}/{size}")
    public ResponseResult listArticleByLabel(@PathVariable("page") int page, @PathVariable("size") int size,@PathVariable("label") String label) {
        return articleService.listArticleByLabel(page,size,label);
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
    public ResponseResult listArticleByCategoryId(@PathVariable("categoryId") String categoryId, @PathVariable("page") int page, @PathVariable("size") int size) {
        return articleService.listArticle(page, size, null, categoryId, Constants.Article.STATE_PUBLISH);
    }

    /**
     * 获取文章的细节
     *
     * @param articleId 文章的id
     * @return {@link ResponseResult}
     */
    @GetMapping("/{articleId}")
    public ResponseResult getArticleDetail(@PathVariable("articleId") String articleId) {
        return articleService.getArticle(articleId);
    }

    /**
     * 得到推荐的文章
     * 通过标签来计算匹配度
     * 从里面随机拿一个出来---每次一都有些许差别
     * 通过标签去查询类似的文章
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/recommend/{articleId}/{size}")
    public ResponseResult getRecommendArticle(@PathVariable("articleId") String articleId, @PathVariable("size") int size) {
        return articleService.listRecommendArticle(articleId, size);
    }

    @GetMapping("/top")
    public ResponseResult getTopArticle() {
        return articleService.listArticle(1, 10, null, null, Constants.Article.STATE_TOP);
    }

    /**
     * 得到标签云
     * 用户点击标签,就会通过标签,获取到相关的文章列表
     * @param size 大小
     * @return {@link ResponseResult}
     */
    @GetMapping("/label/{size}")
    public ResponseResult getLabels(@PathVariable("size") int size) {
        return articleService.listLabels(size);
    }
}
