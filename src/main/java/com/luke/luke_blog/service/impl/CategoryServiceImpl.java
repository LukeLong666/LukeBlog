package com.luke.luke_blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luke.luke_blog.dao.CategoryMapper;
import com.luke.luke_blog.pojo.Category;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.ICategoryService;
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

    @Override
    public ResponseResult getCategory(String categoryId) {
        if (TextUtils.isEmpty(categoryId)) {
            log.info(TAG+" getCategory() --> date validation : "+"分类ID为空");
            return ResponseResult.FAILURE("分类ID不能为空");
        }
        Category category = categoryDao.findOneById(categoryId);
        if (category == null) {
            log.info(TAG+" getCategory() --> date from db : "+"分类未找到");
            return ResponseResult.FAILURE("该分类不存在");
        }
        log.info(TAG+" getCategory() --> date from db : "+category.toString());
        return ResponseResult.SUCCESS("获取分类成功",category);
    }

    @Override
    public ResponseResult listCategories(int page, int size) {
        //获取分类列表
        if (page < Constants.Page.DEFAULT_PAGE) {
            log.info(TAG+" listCategories() --> page < Constants.Page.DEFAULT_PAGE : "+true);
            page = Constants.Page.DEFAULT_PAGE;
        }
        if (size < Constants.Page.MIN_SIZE) {
            log.info(TAG+" listCategories() --> size < Constants.Page.MIN_SIZE : "+true);
            size = Constants.Page.MIN_SIZE;
        }
        PageHelper.startPage(page, size);
        List<Category> categoryList = categoryDao.findAll();
        log.info(TAG+" listCategories() --> categoryList : "+categoryList);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        log.info(TAG+" listCategories() --> pageInfo : "+pageInfo);
        log.info(TAG+" listCategories() --> ResponseResult : "+"查询成功");
        return ResponseResult.SUCCESS("获取分类列表成功!", pageInfo);
    }

    @Override
    public ResponseResult updateCategory(String categoryId, Category category) {
        //找出来
        Category categoryFromDb = categoryDao.findOneById(categoryId);
        if (categoryFromDb == null) {
            log.info(TAG+" updateCategory() --> categoryFromDb : "+"分类不存在");
            return ResponseResult.FAILURE("分类不存在");
        }
        //内容改判断
        String name = category.getName();
        if (!TextUtils.isEmpty(name)) {
            log.info(TAG+" updateCategory() --> name : "+name);
            categoryFromDb.setName(name);
        }
        String pinyin = category.getPinyin();
        if (!TextUtils.isEmpty(pinyin)) {
            log.info(TAG+" updateCategory() --> pinyin : "+pinyin);
            categoryFromDb.setPinyin(pinyin);
        }
        String description = category.getDescription();
        if (!TextUtils.isEmpty(description)) {
            log.info(TAG+" updateCategory() --> description : "+description);
            categoryFromDb.setDescription(description);
        }
        categoryFromDb.setOrder(category.getOrder());
        //第三步保存数据
        int result = categoryDao.updateById(categoryFromDb);
        log.info(TAG+" updateCategory() --> result : "+result);
        //返回结果
        return result > 0 ? ResponseResult.SUCCESS("修改成功", result) : ResponseResult.FAILURE("修改失败");
    }

    @Override
    public ResponseResult deleteCategory(String categoryId) {
        int result = categoryDao.deleteById(categoryId);
        log.info(TAG+" deleteCategory() --> result : "+result);
        return result > 0 ? ResponseResult.SUCCESS("删除成功", result) : ResponseResult.FAILURE("删除失败");
    }


}
