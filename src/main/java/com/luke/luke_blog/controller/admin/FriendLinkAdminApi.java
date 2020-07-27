package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.pojo.FriendLink;
import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * 朋友链接管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/friend_link")
public class FriendLinkAdminApi {

    /**
     * 添加朋友联系
     *
     * @param friendLink 朋友联系
     * @return {@link ResponseResult}
     */
    @PostMapping
    public ResponseResult addFriendLink(@RequestBody FriendLink friendLink) {
        return ResponseResult.SUCCESS(null);
    }

    /**
     * 删除朋友联系
     *
     * @param friendLinkId 朋友链接id
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/{friendLinkId}")
    public ResponseResult deleteFriendLink(@PathVariable("friendLinkId") String friendLinkId) {
        return ResponseResult.SUCCESS(null);
    }

    /**
     * 更新的朋友联系
     *
     * @param friendLinkId 朋友链接id
     * @return {@link ResponseResult}
     */
    @PutMapping("/{friendLinkId}")
    public ResponseResult updateFriendLink(@PathVariable("friendLinkId") String friendLinkId) {
        return ResponseResult.SUCCESS(null);
    }

    /**
     * 得到朋友的链接
     *
     * @param friendLinkId 朋友链接id
     * @return {@link ResponseResult}
     */
    @GetMapping("/{friendLinkId}")
    public ResponseResult getFriendLink(@PathVariable("friendLinkId") String friendLinkId) {
        return ResponseResult.SUCCESS(null);
    }

    /**
     * 朋友链接列表
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ResponseResult}
     */
    @GetMapping("/list")
    public ResponseResult listFriendLinks(@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseResult.SUCCESS(null);
    }
}
