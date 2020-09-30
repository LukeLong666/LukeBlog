package com.luke.luke_blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luke.luke_blog.dao.ArticleMapper;
import com.luke.luke_blog.dao.CommentMapper;
import com.luke.luke_blog.pojo.Article;
import com.luke.luke_blog.pojo.Comment;
import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.ICommentService;
import com.luke.luke_blog.utils.Constants;
import com.luke.luke_blog.utils.TextUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("commentService")
@Transactional
public class CommentServiceImpl extends BaseServiceImpl implements ICommentService {

    @Resource
    private ArticleMapper articleDao;

    @Resource
    private CommentMapper commentDao;

    @Override
    public ResponseResult postComment(Comment comment) {
        //检查用户
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.FAILURE("登录后才能评论");
        }
        //检查内容
        if (TextUtils.isEmpty(comment.getArticleId())) {
            return ResponseResult.FAILURE("文章id不可以为空");
        }
        Article articleFromDb = articleDao.findOneById(comment.getArticleId());
        if (articleFromDb == null) {
            return ResponseResult.FAILURE("评论的文章不存在");
        }
        if (TextUtils.isEmpty(comment.getContent())) {
            return ResponseResult.FAILURE("评论内容不可以为空");
        }
        //补全数据
        comment.setId(idWorker.nextId() + "");
        comment.setState(Constants.Comment.STATE_PUBLISH);
        comment.setUpdateTime(new Date());
        comment.setCreateTime(new Date());
        comment.setUserName(user.getUserName());
        comment.setUserAvatar(user.getAvatar());
        comment.setUserId(user.getId());
        //保存入库
        int result = commentDao.save(comment);
        return result>0?ResponseResult.SUCCESS("评论成功",result):ResponseResult.FAILURE("未知错误,评论失败");
    }

    /**
     * 评论列表
     *
     * @param articleId 文章的id
     * @param page      页面
     * @param size      大
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult listComment(String articleId,int page,int size) {
        //获取用户列表
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        PageHelper.startPage(page, size);
        List<Comment> listArticles = commentDao.findAllByArticleId(articleId);
        PageInfo<Comment> pageInfo = new PageInfo<>(listArticles);
        log.info("PageInfo =====> "+pageInfo.toString());
        return ResponseResult.SUCCESS("查询成功!", pageInfo);
    }

    @Override
    public ResponseResult listComment(int page,int size) {
        //获取用户列表
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        PageHelper.startPage(page, size);
        List<Comment> listArticles = commentDao.findAll();
        PageInfo<Comment> pageInfo = new PageInfo<>(listArticles);
        log.info("PageInfo =====> "+pageInfo.toString());
        return ResponseResult.SUCCESS("查询成功!", pageInfo);
    }

    @Override
    public ResponseResult deleteCommentById(String commentId) {
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN("用户未登录");
        }
        //把评论拿出来
        Comment comment = commentDao.findOneById(commentId);
        if (comment == null) {
            return ResponseResult.FAILURE("评论不存在");
        }
        //当该评论是自己的,或者当前登录的用户是管理员才可以删除
        if (user.getId().equals(comment.getUserId())||Constants.User.ROLES_ADMIN.equals(user.getRoles())) {
            int deleteResult = commentDao.deleteById(comment.getId());
            return deleteResult>0?ResponseResult.SUCCESS("评论删除成功",deleteResult):ResponseResult.FAILURE("未知错误,删除失败");
        }
        return ResponseResult.PERMISSION_DENY("无权访问");
    }

    @Override
    public ResponseResult topComment(String commentId) {
        Comment comment = commentDao.findOneById(commentId);
        if (comment == null) {
            return ResponseResult.FAILURE("该评论不存在");
        }
        String state = comment.getState();
        if (Constants.Comment.STATE_PUBLISH.equals(state)) {
            comment.setState(Constants.Comment.STATE_TOP);
            int result = commentDao.updateStateById(comment);
            return result > 0 ? ResponseResult.SUCCESS("置顶成功") : ResponseResult.FAILURE("置顶失败");
        } else if (Constants.Comment.STATE_TOP.equals(state)) {
            comment.setState(Constants.Comment.STATE_PUBLISH);
            int result = commentDao.updateStateById(comment);
            return result > 0 ? ResponseResult.SUCCESS("取消置顶成功") : ResponseResult.FAILURE("取消置顶失败");
        } else{
            return ResponseResult.FAILURE("未知错误,请重试");
        }
    }
}
