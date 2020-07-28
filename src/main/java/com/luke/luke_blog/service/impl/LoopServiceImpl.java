package com.luke.luke_blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luke.luke_blog.dao.LooperMapper;
import com.luke.luke_blog.pojo.Looper;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.ILoopService;
import com.luke.luke_blog.utils.Constants;
import com.luke.luke_blog.utils.IdWorker;
import com.luke.luke_blog.utils.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("loopService")
@Transactional
public class LoopServiceImpl implements ILoopService {

    public static final String TAG="LoopServiceImpl --> ";

    Logger log = LoggerFactory.getLogger(LoopServiceImpl.class);

    @Resource
    private IdWorker idWorker;

    @Resource
    private LooperMapper looperDao;

    @Override
    public ResponseResult addLooper(Looper looper) {
        //检查数据
        if (TextUtils.isEmpty(looper.getTitle())) {
            log.info(TAG+" addLooper() --> data validation : "+"标题为空");
            return ResponseResult.FAILURE("标题不可以为空");
        }
        if (TextUtils.isEmpty(looper.getImageUrl())) {
            log.info(TAG+" addLooper() --> data validation : "+"图片为空");
            return ResponseResult.FAILURE("图片不可以为空");
        }
        if (TextUtils.isEmpty(looper.getTargetUrl())) {
            log.info(TAG+" addLooper() --> data validation : "+"目标路径为空");
            return ResponseResult.FAILURE("目标路径不可以为空");
        }
        //补充数据
        looper.setId(idWorker.nextId()+"");
        looper.setCreateTime(new Date());
        looper.setUpdateTime(new Date());
        log.info(TAG+" addLooper() --> looper : "+looper);
        //保存数据
        int saveResult = looperDao.save(looper);
        log.info(TAG+" addLooper() --> saveResult : "+saveResult);
        //返回结果
        return saveResult>0?ResponseResult.SUCCESS("添加成功!",saveResult):ResponseResult.FAILURE("添加失败");
    }

    @Override
    public ResponseResult getLooper(String looperId) {
        if (TextUtils.isEmpty(looperId)) {
            return ResponseResult.FAILURE("请输入轮播图ID");
        }
        Looper looperFromDb = looperDao.findOneById(looperId);
        if (looperFromDb != null) {
            log.info(TAG+" addLooper() --> looperFromDb : "+looperFromDb);
            return ResponseResult.SUCCESS("查询成功", looperFromDb);
        }
        log.info(TAG+" addLooper() --> looperFromDb : "+null);
        return ResponseResult.FAILURE("轮播图不存在");
    }

    @Override
    public ResponseResult listLoopers(int page, int size) {
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        PageHelper.startPage(page, size);
        List<Looper> looperList = looperDao.findAll();
        PageInfo<Looper> pageInfo = new PageInfo<>(looperList);
        log.info("PageInfo =====> "+pageInfo.toString());
        return ResponseResult.SUCCESS("查询成功!", pageInfo);
    }

    @Override
    public ResponseResult updateLooper(String looperId,Looper looper) {
        //判空
        if (TextUtils.isEmpty(looperId)) {
            return ResponseResult.FAILURE("请输入轮播图ID");
        }
        //查询
        Looper looperFromDb = looperDao.findOneById(looperId);
        if (looperFromDb == null) {
            return ResponseResult.FAILURE("该轮播图不存在");
        }
        //修改数据
        if (!TextUtils.isEmpty(looper.getTitle())) {
            looperFromDb.setTitle(looper.getTitle());
        }
        if (!TextUtils.isEmpty(looper.getTargetUrl())) {
            looperFromDb.setTargetUrl(looper.getTargetUrl());
        }
        if (!TextUtils.isEmpty(looper.getImageUrl())) {
            looperFromDb.setImageUrl(looper.getImageUrl());
        }
        if (!TextUtils.isEmpty(looper.getState())) {
            looperFromDb.setState(looper.getState());
        }
        looperFromDb.setOrder(looper.getOrder());
        looperFromDb.setUpdateTime(new Date());
        //更新
        int result = looperDao.updateById(looper);
        return result>0?ResponseResult.SUCCESS("更新成功!",result):ResponseResult.FAILURE("更新失败");
    }

    @Override
    public ResponseResult deleteLooper(String looperId) {
        //判空
        if (TextUtils.isEmpty(looperId)) {
            return ResponseResult.FAILURE("请输入轮播图ID");
        }
        int result = looperDao.deleteById(looperId);
        return result>0?ResponseResult.SUCCESS("删除成功!",result):ResponseResult.FAILURE("删除失败");
    }
}
