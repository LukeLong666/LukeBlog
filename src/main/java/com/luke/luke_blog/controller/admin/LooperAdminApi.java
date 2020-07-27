package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.pojo.Looper;
import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * 电影管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/loop")
public class LooperAdminApi {

    @PostMapping
    public ResponseResult addLooper(@RequestBody Looper looper) {
        return ResponseResult.SUCCESS(null);
    }

    @DeleteMapping("/{looperId}")
    public ResponseResult deleteLooper(@PathVariable("looperId") String looperId) {
        return ResponseResult.SUCCESS(null);
    }

    @PutMapping("/{looperId}")
    public ResponseResult updateLooper(@PathVariable("looperId") String looperId) {
        return ResponseResult.SUCCESS(null);
    }

    @GetMapping("/{looperId}")
    public ResponseResult getLooper(@PathVariable("looperId") String looperId) {
        return ResponseResult.SUCCESS(null);
    }

    @GetMapping("/list")
    public ResponseResult listLoopers(@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseResult.SUCCESS(null);
    }
}
