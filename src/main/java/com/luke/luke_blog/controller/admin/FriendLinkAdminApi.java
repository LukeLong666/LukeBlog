package com.luke.luke_blog.controller.admin;

import com.luke.luke_blog.pojo.FriendLink;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IFriendLinkService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 朋友链接管理api
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
@RequestMapping("/admin/friend_link")
public class FriendLinkAdminApi {

    @Resource
    private IFriendLinkService friendLinkService;

    /**
     * 添加朋友联系
     *
     * @param friendLink 朋友联系
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult addFriendLink(@RequestBody FriendLink friendLink) {
        return friendLinkService.addFriendLink(friendLink);
    }

    /**
     * 删除朋友联系
     *
     * @param friendLinkId 朋友链接id
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @DeleteMapping("/{friendLinkId}")
    public ResponseResult deleteFriendLink(@PathVariable("friendLinkId") String friendLinkId) {
        return friendLinkService.deleteById(friendLinkId);
    }

    /**
     * 更新的朋友联系
     *
     * @param friendLinkId 朋友链接id
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("/{friendLinkId}")
    public ResponseResult updateFriendLink(@PathVariable("friendLinkId") String friendLinkId,@RequestBody FriendLink friendLink) {
        return friendLinkService.update(friendLinkId,friendLink);
    }

    /**
     * 得到朋友的链接
     *
     * @param friendLinkId 朋友链接id
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/{friendLinkId}")
    public ResponseResult getFriendLink(@PathVariable("friendLinkId") String friendLinkId) {
        return friendLinkService.getFriendLink(friendLinkId);
    }

    /**
     * 朋友链接列表
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/list/{page}/{size}")
    public ResponseResult listFriendLinks(@PathVariable("page") int page,@PathVariable("size") int size) {
        return friendLinkService.listFriendLinks(page,size);
    }
}
