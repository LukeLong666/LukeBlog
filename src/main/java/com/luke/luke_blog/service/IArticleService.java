package com.luke.luke_blog.service;

import com.luke.luke_blog.pojo.Article;
import com.luke.luke_blog.response.ResponseResult;

/**
 * iarticle服务
 *
 * @author zhang
 * @date 2020/07/29
 */
public interface IArticleService {
    /**
     * 发布文章
     *
     * @param article 文章
     * @return {@link ResponseResult}
     */
    ResponseResult postArticle(Article article);

    /**
     * 文章列表
     *
     * @param page       页面
     * @param size       大小
     * @param keyword    关键字
     * @param categoryId 类别id
     * @return {@link ResponseResult}
     */
    ResponseResult listArticle(int page, int size, String keyword, String categoryId,String state);

    /**
     * 得到的文章
     *
     * @param articleId 文章的id
     * @return {@link ResponseResult}
     */
    ResponseResult getArticle(String articleId);

    /**
     * 更新文章
     *
     * @param articleId 文章的id
     * @param article   文章
     * @return {@link ResponseResult}
     */
    ResponseResult updateArticle(String articleId, Article article);

    /**
     * 删除文章
     *
     * @param articleId 文章的id
     * @return {@link ResponseResult}
     */
    ResponseResult deleteArticle(String articleId);

    /**
     * 推荐文章列表
     *
     * @param articleId 文章的id
     * @param size      大小
     * @return {@link ResponseResult}
     */
    ResponseResult listRecommendArticle(String articleId, int size);

    /**
     * 本文由标签列表
     *
     * @param page         页面
     * @param size         大小
     * @param statePublish 国家发布
     * @return {@link ResponseResult}
     */
    ResponseResult listArticleByLabel(int page, int size, String label);

    /**
     * 列表标签
     *
     * @param size 大小
     * @return {@link ResponseResult}
     */
    ResponseResult listLabels(int size);
}
