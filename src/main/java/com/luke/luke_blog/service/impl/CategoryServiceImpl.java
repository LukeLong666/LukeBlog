package com.luke.luke_blog.service.impl;

import com.luke.luke_blog.dao.CategoryMapper;
import com.luke.luke_blog.pojo.Category;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.ICategoryService;
import com.luke.luke_blog.utils.IdWorker;
import com.luke.luke_blog.utils.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements ICategoryService {

    public static final String TAG="CategoryServiceImpl --> ";

    Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Resource
    private CategoryMapper categoryDao;

    @Resource
    private IdWorker idWorker;

    @Override
    public ResponseResult addCategory(Category category) {
        //检查数据
        if (TextUtils.isEmpty(category.getName())) {
            log.info(TAG+" addCategory() --> date validation : "+"分类名称为空");
            return ResponseResult.FAILURE("分类名称不可以为空");
        }
        if (TextUtils.isEmpty(category.getPinyin())) {
            log.info(TAG+" addCategory() --> date validation : "+"分类拼音为空");
            return ResponseResult.FAILURE("分类拼音不可以为空");
        }
        if (TextUtils.isEmpty(category.getDescription())) {
            log.info(TAG+" addCategory() --> date validation : "+"分类描述为空");
            return ResponseResult.FAILURE("分类描述不可以为空");
        }
        //补全数据
        category.setId(idWorker.nextId() + "");
        category.setStatus("1");
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        log.info(TAG+" addCategory() --> date detail --> category : "+category.toString());
        int result = categoryDao.save(category);
        log.info(TAG+" addCategory() --> save result : "+result);
        return result>0?ResponseResult.SUCCESS("添加分类成功!",result):ResponseResult.FAILURE("添加分类失败");
    }
}
