package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.pojo.Looper;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.ILoopService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 电影管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/loop")
public class LooperAdminApi {

    @Resource
    private ILoopService loopService;

    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult addLooper(@RequestBody Looper looper) {
        return loopService.addLooper(looper);
    }

    @PreAuthorize("@permission.admin()")
    @DeleteMapping("/{looperId}")
    public ResponseResult deleteLooper(@PathVariable("looperId") String looperId) {
        return loopService.deleteLooper(looperId);
    }

    @PreAuthorize("@permission.admin()")
    @PutMapping("/{looperId}")
    public ResponseResult updateLooper(@PathVariable("looperId") String looperId,@RequestBody Looper looper) {
        return loopService.updateLooper(looperId,looper);
    }

    @PreAuthorize("@permission.admin()")
    @GetMapping("/{looperId}")
    public ResponseResult getLooper(@PathVariable("looperId") String looperId) {
        return loopService.getLooper(looperId);
    }

    @PreAuthorize("@permission.admin()")
    @GetMapping("/list/{page}/{size}")
    public ResponseResult listLoopers(@PathVariable("page") int page,@PathVariable("size") int size) {
        return loopService.listLoopers(page,size);
    }
}
