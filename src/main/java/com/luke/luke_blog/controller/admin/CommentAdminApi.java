package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * 图像管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/comment")
public class CommentAdminApi {

    @DeleteMapping("/{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId") String commentId) {
        return ResponseResult.SUCCESS(null);
    }


    @GetMapping("/list")
    public ResponseResult listComments(@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseResult.SUCCESS(null);
    }

    @PutMapping("/top/{commentId}")
    public ResponseResult topComment(@PathVariable("commentId") String commentId) {
        return ResponseResult.SUCCESS(null);
    }
}
