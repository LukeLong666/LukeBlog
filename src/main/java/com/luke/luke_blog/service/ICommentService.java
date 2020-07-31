package com.luke.luke_blog.service;

import com.luke.luke_blog.pojo.Comment;
import com.luke.luke_blog.response.ResponseResult;

public interface ICommentService {
    /**
     * 文章评论
     *
     * @param comment 评论
     * @return {@link ResponseResult}
     */
    ResponseResult postComment(Comment comment);

    /**
     * 评论列表
     *
     * @param articleId 文章的id
     * @return {@link ResponseResult}
     */
    ResponseResult listComment(String articleId,int page,int size);

    /**
     * 通过id删除评论
     *
     * @param commentId 评论id
     * @return {@link ResponseResult}
     */
    ResponseResult deleteCommentById(String commentId);
}
