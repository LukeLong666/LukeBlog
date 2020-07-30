package com.luke.luke_blog.controller.portal;

import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.ICategoryService;
import com.luke.luke_blog.service.IFriendLinkService;
import com.luke.luke_blog.service.ILoopService;
import com.luke.luke_blog.service.IWebSizeInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * web大小信息api
 *
 * @author zhang
 * @date 2020/07/21
 */
@RestController
@RequestMapping("/portal/web_size_info")
public class WebSizeInfoApi {

    @Resource
    private ICategoryService categoryService;

    @Resource
    private IFriendLinkService friendLinkService;

    @Resource
    private ILoopService loopService;

    @Resource
    private IWebSizeInfoService webSizeInfoService;

    /**
     * 得到categores
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/categories")
    public ResponseResult getCategories() {
        return categoryService.listCategories();
    }

    /**
     * 获得冠军
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/title")
    public ResponseResult getTitle() {
        return webSizeInfoService.getWebSizeTitle();
    }

    /**
     * 得到视图数
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/view_count")
    public ResponseResult getViewCount() {
        return webSizeInfoService.getWebSizeViewCount();
    }

    /**
     * 让搜索引擎优化
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/seo")
    public ResponseResult getSeo() {
        return webSizeInfoService.getSeoInfo();
    }

    /**
     * 得到循环
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/loop")
    public ResponseResult getLoops() {
        return loopService.listLoopers();
    }

    /**
     * 得到链接
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/friend_link")
    public ResponseResult getLinks() {
        return friendLinkService.listFriendLinks();
    }
}
