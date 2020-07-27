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
}
