package com.luke.luke_blog.service.impl;

import com.luke.luke_blog.dao.SettingsMapper;
import com.luke.luke_blog.pojo.Setting;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IWebSizeInfoService;
import com.luke.luke_blog.utils.Constants;
import com.luke.luke_blog.utils.IdWorker;
import com.luke.luke_blog.utils.RedisUtil;
import com.luke.luke_blog.utils.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络信息服务impl大小
 *
 * @author zhang
 * @date 2020/07/29
 */
@Service("webSizeInfoService")
@Transactional
public class WebSizeInfoServiceImpl implements IWebSizeInfoService {

    public static final String TAG="WebSizeInfoServiceImpl --> ";

    Logger log = LoggerFactory.getLogger(WebSizeInfoServiceImpl.class);

    @Resource
    private IdWorker idWorker;

    @Resource
    private SettingsMapper settingsDao;

    @Override
    public ResponseResult getWebSizeTitle() {
        Setting setting = settingsDao.findOneByKey(Constants.Settings.WEB_SIZE_TITLE);
        log.info(TAG+" getWebSizeTitle() --> "+" setting : "+setting);
        return ResponseResult.SUCCESS("获取网站标题成功",setting);
    }

    @Override
    public ResponseResult putWebSizeTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return ResponseResult.FAILURE("标题不可以为空");
        }
        Setting setting = settingsDao.findOneByKey(Constants.Settings.WEB_SIZE_TITLE);
        int result=-1;
        if (setting == null) {
            log.info(TAG+" putWebSizeTitle() --> "+" setting == null ");
            setting = new Setting();
            setting.setId(idWorker.nextId()+"");
            setting.setCreateTime(new Date());
            setting.setUpdateTime(new Date());
            setting.setKey(Constants.Settings.WEB_SIZE_TITLE);
            setting.setValue(title);
            result = settingsDao.save(setting);
        }else{
            log.info(TAG+" putWebSizeTitle() --> "+" setting != null ");
            setting.setValue(title);
            setting.setUpdateTime(new Date());
            result = settingsDao.updateById(setting);
        }
        log.info(TAG+" putWebSizeTitle() --> "+" setting : "+setting);
        log.info(TAG+" putWebSizeTitle() --> "+" result : "+result);
        return result>0?ResponseResult.SUCCESS("更新标题成功!",result):ResponseResult.FAILURE("失败");
    }

    @Override
    public ResponseResult getSeoInfo() {
        Setting description = settingsDao.findOneByKey(Constants.Settings.WEB_SIZE_DESCRIPTION);
        log.info(TAG+" getSeoInfo() --> "+" description : "+description);
        Setting keywords = settingsDao.findOneByKey(Constants.Settings.WEB_SIZE_KEYWORDS);
        log.info(TAG+" getSeoInfo() --> "+" keywords : "+keywords);
        Map<String, String> result = new HashMap<>();
        result.put(description.getKey(), description.getValue());
        result.put(keywords.getKey(), keywords.getValue());
        log.info(TAG+" getSeoInfo() --> "+" result : "+result);
        return ResponseResult.SUCCESS("获取SEO信息成功", result);
    }

    @Override
    public ResponseResult putSeoInfo(String keywords, String description) {
        if (TextUtils.isEmpty(keywords)) {
            return ResponseResult.FAILURE("关键字不可以为空");
        }
        if (TextUtils.isEmpty(description)) {
            return ResponseResult.FAILURE("描述不可以为空");
        }
        Setting descriptionFromDb = settingsDao.findOneByKey(Constants.Settings.WEB_SIZE_DESCRIPTION);
        int result1=-1,result2=-1;
        if (descriptionFromDb == null) {
            descriptionFromDb = new Setting();
            descriptionFromDb.setId(idWorker.nextId()+"");
            descriptionFromDb.setCreateTime(new Date());
            descriptionFromDb.setUpdateTime(new Date());
            descriptionFromDb.setKey(Constants.Settings.WEB_SIZE_DESCRIPTION);
            descriptionFromDb.setValue(description);
            result1 = settingsDao.save(descriptionFromDb);
        }else{
            descriptionFromDb.setValue(description);
            descriptionFromDb.setUpdateTime(new Date());
            result1 = settingsDao.updateById(descriptionFromDb);
        }
        log.info(TAG+" putSeoInfo() --> "+" descriptionFromDb : "+descriptionFromDb);
        Setting keywordsFromDb = settingsDao.findOneByKey(Constants.Settings.WEB_SIZE_KEYWORDS);
        if (keywordsFromDb == null) {
            keywordsFromDb = new Setting();
            keywordsFromDb.setId(idWorker.nextId()+"");
            keywordsFromDb.setCreateTime(new Date());
            keywordsFromDb.setUpdateTime(new Date());
            keywordsFromDb.setKey(Constants.Settings.WEB_SIZE_KEYWORDS);
            keywordsFromDb.setValue(keywords);
            result2 = settingsDao.save(keywordsFromDb);
        }else{
            keywordsFromDb.setValue(keywords);
            keywordsFromDb.setUpdateTime(new Date());
            result2 = settingsDao.updateById(keywordsFromDb);
        }
        log.info(TAG+" putSeoInfo() --> "+" keywordsFromDb : "+keywordsFromDb);
        log.info(TAG+" putSeoInfo() --> "+" result1+result2 : "+result1+result2);
        return ResponseResult.SUCCESS("更新SEO信息成功",result1+result2);
    }

    /**
     * 全网站访问量
     * 只统计文章的浏览量
     *
     *
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getWebSizeViewCount() {
        //先从redis拿出来
        String viewCountStr = (String) redisUtil.get(Constants.Settings.WEB_SIZE_VIEW_COUNT);
        Setting viewCount = settingsDao.findOneByKey(Constants.Settings.WEB_SIZE_VIEW_COUNT);
        if(viewCount==null){
            viewCount = new Setting();
            viewCount.setId(idWorker.nextId() + "");
            viewCount.setKey(Constants.Settings.WEB_SIZE_VIEW_COUNT);
            viewCount.setCreateTime(new Date());
            viewCount.setUpdateTime(new Date());
            viewCount.setValue("1");
            settingsDao.save(viewCount);
        }
        if (TextUtils.isEmpty(viewCountStr)) {
            viewCountStr = viewCount.getValue();
            redisUtil.set(Constants.Settings.WEB_SIZE_VIEW_COUNT, viewCountStr);
        }else{
            //把redis更新到数据酷
            viewCount.setValue(viewCountStr);
            settingsDao.updateById(viewCount);
        }
        Map<String, Integer> result = new HashMap<>();
        result.put(viewCount.getKey(), Integer.valueOf(viewCount.getValue()));
        return ResponseResult.SUCCESS("获取访问量成功",result);
    }

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void updateViewCount() {
        //redis
        Object viewCount = redisUtil.get(Constants.Settings.WEB_SIZE_VIEW_COUNT);
        if (viewCount == null) {
            Setting viewCountFromDb = settingsDao.findOneByKey(Constants.Settings.WEB_SIZE_VIEW_COUNT);
            if (viewCountFromDb == null) {
                viewCountFromDb = new Setting();
                viewCountFromDb.setId(idWorker.nextId() + "");
                viewCountFromDb.setKey(Constants.Settings.WEB_SIZE_VIEW_COUNT);
                viewCountFromDb.setCreateTime(new Date());
                viewCountFromDb.setUpdateTime(new Date());
                viewCountFromDb.setValue("1");
                settingsDao.save(viewCountFromDb);
            }
            String viewCountValue = viewCountFromDb.getValue();
            redisUtil.set(Constants.Settings.WEB_SIZE_VIEW_COUNT, viewCountValue);
        }
        redisUtil.incr(Constants.Settings.WEB_SIZE_VIEW_COUNT, 1);
    }
}
