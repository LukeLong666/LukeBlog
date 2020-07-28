package com.luke.luke_blog.service;

import com.luke.luke_blog.pojo.FriendLink;
import com.luke.luke_blog.response.ResponseResult;

/**
 * ifriend链接服务
 *
 * @author zhang
 * @date 2020/07/27
 */
public interface IFriendLinkService {
    /**
     * 添加朋友联系
     *
     * @param friendLink 朋友联系
     * @return {@link ResponseResult}
     */
    ResponseResult addFriendLink(FriendLink friendLink);

    /**
     * 得到朋友的链接
     *
     * @param friendLinkId 朋友链接id
     * @return {@link ResponseResult}
     */
    ResponseResult getFriendLink(String friendLinkId);

    /**
     * 朋友链接列表
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ResponseResult}
     */
    ResponseResult listFriendLinks(int page, int size);

    /**
     * 删除通过id
     *
     * @param friendLinkId 朋友链接id
     * @return {@link ResponseResult}
     */
    ResponseResult deleteById(String friendLinkId);

    /**
     * 更新
     *
     * @param friendLinkId 朋友链接id
     * @param friendLink   朋友联系
     * @return {@link ResponseResult}
     */
    ResponseResult update(String friendLinkId, FriendLink friendLink);
}
