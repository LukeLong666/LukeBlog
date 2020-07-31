package com.luke.luke_blog.service;

import com.luke.luke_blog.response.ResponseResult;

public interface IWebSizeInfoService {
    /**
     * 得到网络大小标题
     *
     * @return {@link ResponseResult}
     */
    ResponseResult getWebSizeTitle();

    /**
     * 把web大小标题
     *
     * @param title 标题
     * @return {@link ResponseResult}
     */
    ResponseResult putWebSizeTitle(String title);

    /**
     * 得到搜索引擎优化信息
     *
     * @return {@link ResponseResult}
     */
    ResponseResult getSeoInfo();

    /**
     * 把seo信息
     *
     * @param keywords    关键字
     * @param description 描述
     * @return {@link ResponseResult}
     */
    ResponseResult putSeoInfo(String keywords, String description);

    /**
     * 获取web视图数大小
     *
     * @return {@link ResponseResult}
     */
    ResponseResult getWebSizeViewCount();

    /**
     * 更新视图数
     */
    void updateViewCount();
}
