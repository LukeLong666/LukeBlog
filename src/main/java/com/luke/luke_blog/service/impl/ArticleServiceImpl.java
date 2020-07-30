package com.luke.luke_blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luke.luke_blog.dao.ArticleMapper;
import com.luke.luke_blog.pojo.Article;
import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IArticleService;
import com.luke.luke_blog.service.IUserService;
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

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements IArticleService {

    public static final String TAG = "ArticleServiceImpl ---> ";

    Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Resource
    private IUserService userService;

    @Resource
    private IdWorker idWorker;

    @Resource
    private ArticleMapper articleDao;


    /**
     * 发布文章
     * 定时发布(待做)
     * 文章审核(待做)
     *
     * 保存成草稿
     * 1.用户手动提交
     * 2.代码自动提交
     *
     * 防止重复提交
     *
     *
     * @param article 文章
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult postArticle(Article article) {
        //检查用户
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.FAILURE("账号未登录");
        }
        //检查数据(标题,分类ID,内容,类型,摘要,标签)
        //标题
        if (TextUtils.isEmpty(article.getTitle())) {
            return ResponseResult.FAILURE("标题不可以为空");
        }
        if (article.getTitle().length()> Constants.Article.TITLE_MAX_LENGTH) {
            return ResponseResult.FAILURE("标题过长");
        }
        //类型
        if (TextUtils.isEmpty(article.getType())) {
            return ResponseResult.FAILURE("类型不可以为空");
        }
        if(!"0".equals(article.getType()) && !"1".equals(article.getType())){
            return ResponseResult.FAILURE("文章类型非法");
        }

        //状态有两种 草稿,发布
        String state = article.getState();
        if (!Constants.Article.STATE_PUBLISH.equals(state) && !Constants.Article.STATE_DRAFT.equals(state)) {
            //不支持该操作
            return ResponseResult.FAILURE("不支持该操作");
        }


        //以下检查是发布的检查
        if (Constants.Article.STATE_PUBLISH.equals(state)) {
            //分类
            if (TextUtils.isEmpty(article.getCategoryId())) {
                return ResponseResult.FAILURE("分类不可以为空");
            }
            //内容
            if (TextUtils.isEmpty(article.getContent())) {
                return ResponseResult.FAILURE("内容不可以为空");
            }
            //摘要
            if (TextUtils.isEmpty(article.getSummary())) {
                return ResponseResult.FAILURE("摘要不可以为空");
            }
            if(article.getSummary().length()>Constants.Article.SUMMARY_MAX_LENGTH){
                return ResponseResult.FAILURE("摘要过长");
            }
            //标签
            if (TextUtils.isEmpty(article.getLabel())) {
                return ResponseResult.FAILURE("标签不可以为空");
            }
        }

        String articleId = article.getId();
        if (TextUtils.isEmpty(articleId)) {
            //新的内容
            //补充数据
            article.setCreateTime(new Date());
            article.setId(idWorker.nextId() + "");

        }else{
            //更新内容,对状态进行处理,如果已经发布,则不能改为草稿
            Article articleFromDb = articleDao.findOneById(article.getId());
            if (Constants.Article.STATE_PUBLISH.equals(articleFromDb.getState())&&Constants.Article.STATE_DRAFT.equals(state)) {
                //已经发布了,只能更新,不能保存成草稿
                return ResponseResult.FAILURE("该文章已发布或者已经为草稿,不能保存成草稿");
            }
        }
        article.setUpdateTime(new Date());
        article.setUserId(user.getId());
        log.info(TAG+" postArticle() ---> article: "+article);
        //保存数据库
        int result = articleDao.save(article);
        log.info(TAG+" postArticle() ---> result: "+result);
        // TODO: 2020/7/29 保存到搜索数据库
        //返回结果
        if (Constants.Article.STATE_DRAFT.equals(state)) {
            return result>0?ResponseResult.SUCCESS("草稿保存成功",article.getId()):ResponseResult.FAILURE("草稿保存失败");
        }
        return result>0?ResponseResult.SUCCESS("发布成功",article.getId()):ResponseResult.FAILURE("发布失败");
    }

    /**
     * 文章列表
     *
     * @param page       页面
     * @param size       大小
     * @param keyword    标题关键字
     * @param categoryId 分类ID
     * @param state      状态:已删除,草稿,已发布,置顶
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult listArticle(int page, int size, String keyword, String categoryId,String state) {
        //获取用户列表
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        PageHelper.startPage(page, size);
        List<Article> listArticles = articleDao.findAll(keyword,categoryId,state);
        PageInfo<Article> pageInfo = new PageInfo<>(listArticles);
        log.info("PageInfo =====> "+pageInfo.toString());
        return ResponseResult.SUCCESS("查询成功!", pageInfo);
    }

    @Override
    public ResponseResult getArticle(String articleId) {
        if (TextUtils.isEmpty(articleId)) {
            return ResponseResult.FAILURE("文章id不可以为空");
        }
        Article articleFromDb = articleDao.findOneById(articleId);
        if (articleFromDb == null) {
            return ResponseResult.FAILURE("文章不存在");
        }
        //判断文章状态
        String state = articleFromDb.getState();
        if (Constants.Article.STATE_PUBLISH.equals(state)||Constants.Article.STATE_TOP.equals(state)) {
            return ResponseResult.SUCCESS("查询成功", articleFromDb);
        }
        User user = userService.checkUser();
        if (!Constants.User.ROLES_ADMIN.equals(user.getRoles())) {
            return ResponseResult.PERMISSION_DENY("无权访问");
        }
        return ResponseResult.SUCCESS("查询成功", articleFromDb);
    }

    /**
     * 更新文章
     * 只支持修改 标题,内容,标签,分类,摘要
     * @param articleId 文章的id
     * @param article   文章
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult updateArticle(String articleId, Article article) {
        Article articleFromDb = articleDao.findOneById(articleId);
        if (articleFromDb == null) {
            return ResponseResult.FAILURE("文章不存在");
        }
        if (!TextUtils.isEmpty(article.getState())) {
            articleFromDb.setState(article.getState());
        }
        if (!TextUtils.isEmpty(article.getTitle())) {
            articleFromDb.setTitle(article.getTitle());
        }
        if (!TextUtils.isEmpty(article.getContent())) {
            articleFromDb.setContent(article.getContent());
        }
        if (!TextUtils.isEmpty(article.getLabel())) {
            articleFromDb.setLabel(article.getLabel());
        }
        if (!TextUtils.isEmpty(article.getCategoryId())) {
            articleFromDb.setCategoryId(article.getCategoryId());
        }
        if (!TextUtils.isEmpty(article.getSummary())) {
            articleFromDb.setSummary(article.getSummary());
        }
        articleFromDb.setUpdateTime(new Date());
        //修改
        int result = articleDao.updateById(articleFromDb);
        return result > 0 ? ResponseResult.SUCCESS("修改成功", result) : ResponseResult.FAILURE("修改失败");
    }

    @Override
    public ResponseResult deleteArticle(String articleId) {
        int result = articleDao.deleteById(articleId);
        return result>0?ResponseResult.SUCCESS("删除成功",result):ResponseResult.FAILURE("文章不存在");
    }
}
