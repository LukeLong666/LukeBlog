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

    @PostMapping
    public ResponseResult postComment(@RequestBody Comment comment) {
        return ResponseResult.success(null);
    }

    @DeleteMapping("/{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId") String commentId) {
        return ResponseResult.success(null);
    }
}
