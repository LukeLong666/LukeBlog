package com.luke.luke_blog.service;

import com.luke.luke_blog.pojo.Looper;
import com.luke.luke_blog.response.ResponseResult;

public interface ILoopService {
    /**
     * 添加电影
     *
     * @param looper 电影
     * @return {@link ResponseResult}
     */
    ResponseResult addLooper(Looper looper);

    /**
     * 得到电影
     *
     * @param looperId 尺蠖id
     * @return {@link ResponseResult}
     */
    ResponseResult getLooper(String looperId);

    /**
     * 电影列表
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ResponseResult}
     */
    ResponseResult listLoopers(int page, int size);

    /**
     * 更新电影
     *
     * @param looperId 尺蠖id
     * @return {@link ResponseResult}
     */
    ResponseResult updateLooper(String looperId,Looper looper);

    /**
     * 删除电影
     *
     * @param looperId 尺蠖id
     * @return {@link ResponseResult}
     */
    ResponseResult deleteLooper(String looperId);
}
