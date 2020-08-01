package com.luke.luke_blog.controller.portal;

import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.ICategoryService;
import com.luke.luke_blog.service.IFriendLinkService;
import com.luke.luke_blog.service.ILoopService;
import com.luke.luke_blog.service.IWebSizeInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
public class WebSizeInfoPortalApi {

    @Resource
    private ICategoryService categoryService;

    @Resource
    private IFriendLinkService friendLinkService;

    @Resource
    private ILoopService loopService;

    @Resource
    private IWebSizeInfoService webSizeInfoService;



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

    /**
     * 访问量
     * 每个页面点击一次,访问量就加一
     * 根据ip进行过滤
     *
     * 不会每次都更新到mysql,只有获取访问量的时候,才更新mysql数据库
     *
     *
     */
    @PutMapping("/viewCount")
    public void updateViewCount() {
        webSizeInfoService.updateViewCount();
    }
}
