package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.ICommentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 图像管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/comment")
public class CommentAdminApi {

    @Resource
    private ICommentService commentService;

    @PreAuthorize("@permission.admin()")
    @DeleteMapping("/{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId") String commentId) {
        return commentService.deleteCommentById(commentId);
    }


    @PreAuthorize("@permission.admin()")
    @GetMapping("/list/{page}/{size}")
    public ResponseResult listComments(@PathVariable("page") int page, @PathVariable("size") int size) {
        return commentService.listComment(page,size);
    }

    @PreAuthorize("@permission.admin()")
    @PutMapping("/top/{commentId}")
    public ResponseResult topComment(@PathVariable("commentId") String commentId) {
        return commentService.topComment(commentId);
    }

}
