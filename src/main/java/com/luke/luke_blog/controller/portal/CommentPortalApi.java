package com.luke.luke_blog.controller.portal;

import com.luke.luke_blog.pojo.Comment;
import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * 评论门户api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/portal/comment")
public class CommentPortalApi {

    /**
     * 文章评论
     *
     * @param comment 评论
     * @return {@link ResponseResult}
     */
    @PostMapping
    public ResponseResult postComment(@RequestBody Comment comment) {
        return ResponseResult.success(null);
    }

    /**
     * 删除评论
     *
     * @param commentId 评论id
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId") String commentId) {
        return ResponseResult.success(null);
    }

    /**
     * 评论列表
     *
     * @param articleId 文章的id
     * @return {@link ResponseResult}
     */
    @GetMapping("/list/{articleId}")
    public ResponseResult listComment(@PathVariable("articleId") String articleId) {
        return ResponseResult.success(null);
    }
}
