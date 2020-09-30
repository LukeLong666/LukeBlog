package com.luke.luke_blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.luke.luke_blog.dao.ArticleMapper;
import com.luke.luke_blog.dao.CommentMapper;
import com.luke.luke_blog.dao.LabelMapper;
import com.luke.luke_blog.pojo.Article;
import com.luke.luke_blog.pojo.Label;
import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IArticleService;
import com.luke.luke_blog.utils.Constants;
import com.luke.luke_blog.utils.RedisUtil;
import com.luke.luke_blog.utils.TextUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("articleService")
@Transactional
public class ArticleServiceImpl extends BaseServiceImpl implements IArticleService {

    @Resource
    private ArticleMapper articleDao;

    @Resource
    private Random random;

    @Resource
    private LabelMapper labelDao;

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
            if (TextUtils.isEmpty(article.getLabels())) {
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
        //打散标签,标签入库
        this.setupLabels(article.getLabels());
        // TODO: 2020/7/29 保存到搜索数据库
        //返回结果
        if (Constants.Article.STATE_DRAFT.equals(state)) {
            return result>0?ResponseResult.SUCCESS("草稿保存成功",article.getId()):ResponseResult.FAILURE("草稿保存失败");
        }
        return result>0?ResponseResult.SUCCESS("发布成功",article.getId()):ResponseResult.FAILURE("发布失败");
    }

    private void setupLabels(String labels) {
        List<String> labelList = new ArrayList<>();
        if(labels.contains("-")){
            labelList.addAll(Arrays.asList(labels.split("-")));
        }else{
            labelList.add(labels);
        }
        //入库统计
        for (String label : labelList) {
            int result = labelDao.updateCountByName(label);
            if (result == 0) {
                Label labelFromDb = new Label();
                labelFromDb.setId(idWorker.nextId() + "");
                labelFromDb.setCount(1);
                labelFromDb.setName(label);
                labelFromDb.setCreateTime(new Date());
                labelFromDb.setUpdateTime(new Date());
                labelDao.save(labelFromDb);
            }
        }
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

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private Gson gson;

    @Override
    public ResponseResult getArticle(String articleId) {
        if (TextUtils.isEmpty(articleId)) {
            return ResponseResult.FAILURE("文章id不可以为空");
        }
        //先从redis里获取
        String articleJson = (String) redisUtil.get(Constants.Article.KEY_ARTICLE_CACHE + articleId);
        if(!TextUtils.isEmpty(articleJson)){
            Article article = gson.fromJson(articleJson, Article.class);
            //增加阅读数量
            redisUtil.incr(Constants.Article.KEY_ARTICLE_VIEW_COUNT + articleId, 1);
            article.setViewCount(Integer.parseInt((String) redisUtil.get(Constants.Article.KEY_ARTICLE_VIEW_COUNT + articleId)));
            return ResponseResult.SUCCESS("查询成功", article);
        }
        Article articleFromDb = articleDao.findOneById(articleId);
        if (articleFromDb == null) {
            return ResponseResult.FAILURE("文章不存在");
        }
        //判断文章状态
        String state = articleFromDb.getState();
        User user = userService.checkUser();
        if (user==null||!Constants.User.ROLES_ADMIN.equals(user.getRoles())) {
            //普通用户
            if (Constants.Article.STATE_PUBLISH.equals(state)||Constants.Article.STATE_TOP.equals(state)) {
                redisUtil.set(Constants.Article.KEY_ARTICLE_CACHE + articleId, gson.toJson(articleFromDb), Constants.TimeValue.MIN * 5);
                String articleViewCount = (String) redisUtil.get(Constants.Article.KEY_ARTICLE_VIEW_COUNT + articleId);
                if (TextUtils.isEmpty(articleViewCount)) {
                    redisUtil.set(Constants.Article.KEY_ARTICLE_VIEW_COUNT + articleId, String.valueOf(articleFromDb.getViewCount()+1));
                }else{
                    articleFromDb.setViewCount(1+Integer.parseInt(articleViewCount));
                    articleDao.updateById(articleFromDb);
                }
                return ResponseResult.SUCCESS("查询成功", articleFromDb);
            }else{
                return ResponseResult.FAILURE("文章不存在");
            }
        }
        redisUtil.set(Constants.Article.KEY_ARTICLE_CACHE + articleId, gson.toJson(articleFromDb), Constants.TimeValue.MIN * 5);
        String articleViewCount = (String) redisUtil.get(Constants.Article.KEY_ARTICLE_VIEW_COUNT + articleId);
        if (TextUtils.isEmpty(articleViewCount)) {
            redisUtil.set(Constants.Article.KEY_ARTICLE_VIEW_COUNT + articleId, String.valueOf(articleFromDb.getViewCount()+1));
        }else{
            articleFromDb.setViewCount(1+Integer.parseInt(articleViewCount));
            articleDao.updateById(articleFromDb);
        }
        //管理员
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
        if (!TextUtils.isEmpty(article.getLabels())) {
            articleFromDb.setLabels(article.getLabels());
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

    @Resource
    private CommentMapper commentDao;

    @Override
    public ResponseResult deleteArticle(String articleId) {
        //先删除评论
        commentDao.deleteByArticleId(articleId);
        int result = articleDao.deleteById(articleId);
        //删除缓存
        redisUtil.del(Constants.Article.KEY_ARTICLE_CACHE + articleId);
        redisUtil.del(Constants.Article.KEY_ARTICLE_VIEW_COUNT + articleId);
        return result>0?ResponseResult.SUCCESS("删除成功",result):ResponseResult.FAILURE("文章不存在");
    }

    @Override
    public ResponseResult listRecommendArticle(String articleId, int size) {
        //查询文章
        String labels = articleDao.findOneWithLabelById(articleId);
        List<String> label = new ArrayList<>();
        if (!labels.contains("-")) {
            label.add(labels);
        }else{
            String[] split = labels.split("-");
            label.addAll(Arrays.asList(split));
        }
        String labelSQL = label.get(random.nextInt(label.size()));
        List<Article> listArticles = articleDao.findAllByLabel(labelSQL,articleId,size);
        return ResponseResult.SUCCESS("查询成功", listArticles);
    }

    @Override
    public ResponseResult listArticleByLabel(int page, int size, String label) {
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        PageHelper.startPage(page, size);
        List<Article> listArticles = articleDao.findAllWithLabel(label);
        PageInfo<Article> pageInfo = new PageInfo<>(listArticles);
        log.info("PageInfo =====> "+pageInfo.toString());
        return ResponseResult.SUCCESS("查询成功!", pageInfo);
    }

    @Override
    public ResponseResult listLabels(int size) {
        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        PageHelper.startPage(1, size);
        List<Label> labelList = labelDao.findAll();
        PageInfo<Label> pageInfo = new PageInfo<>(labelList);
        log.info("PageInfo =====> "+pageInfo.toString());
        return ResponseResult.SUCCESS("查询成功!", pageInfo);

    }

    @Override
    public ResponseResult search(String keyword, int page) {
        //获取用户列表
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        PageHelper.startPage(page, Constants.Page.MIN_SIZE);
        List<Article> listArticles = articleDao.search(keyword);
        PageInfo<Article> pageInfo = new PageInfo<>(listArticles);
        log.info("PageInfo =====> "+pageInfo.toString());
        return ResponseResult.SUCCESS("查询成功!", pageInfo);
    }
}
