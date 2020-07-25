package com.luke.luke_blog.controller.user;

import com.luke.luke_blog.dao.CommentMapper;
import com.luke.luke_blog.dao.LabelMapper;
import com.luke.luke_blog.pojo.Comment;
import com.luke.luke_blog.pojo.Label;
import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IUserService;
import com.luke.luke_blog.utils.Constants;
import com.luke.luke_blog.utils.CookieUtils;
import com.luke.luke_blog.utils.IdWorker;
import com.luke.luke_blog.utils.RedisUtil;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Date;

/**
 * 测试控制器
 *
 * @author zhang
 * @date 2020/07/20
 */
@RestController
public class HelloController {

    @Resource
    IdWorker idWorker;

    @Resource
    LabelMapper labelDao;

    @Resource
    CommentMapper commentDao;

    public static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/hello")
    public ResponseResult hello() {
        User user = new User();
        user.setUserName("张鑫龙");
        String string = (String) redisUtil.get(Constants.User.KEY_CAPTCHA_CONTENT + "123456");
        log.info("haha");
        return ResponseResult.success("哈哈", string);
    }

    @PostMapping("/label")
    public ResponseResult addLabel(@RequestBody Label label) {
        label.setId(String.valueOf(idWorker.nextId()));
        label.setCreateTime(new Date());
        label.setUpdateTime(new Date());
        int result = labelDao.save(label);
        return result != 0 ? ResponseResult.success("添加成功", null) : ResponseResult.success("添加失败", null);
    }

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));
        // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

        String content = specCaptcha.text().toLowerCase();

        // 验证码存入session
        //request.getSession().setAttribute("captcha", specCaptcha.text().toLowerCase());
        //存入redis
        //十分钟有效
        redisUtil.set(Constants.User.KEY_CAPTCHA_CONTENT + "123456", content, 60 * 10);

        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }

    @Resource
    private IUserService userService;

    @PostMapping("/comment")
    public ResponseResult testComment(@RequestBody Comment comment,HttpServletRequest request,HttpServletResponse response) {
        String content = comment.getContent();
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        log.info("comment tokenKey == > "+tokenKey);
        if (tokenKey == null) {
            return ResponseResult.failure("账号未登录");
        }
        User user = userService.checkUser(request, response);
        // TODO: 2020/7/25
        if (user == null) {
            return ResponseResult.failure("账号未登录");
        }
        comment.setUserId(user.getId());
        comment.setUserAvatar(user.getAvatar());
        comment.setUserName(user.getUserName());
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        comment.setState("1");
        comment.setId(String.valueOf(idWorker.nextId()));
        commentDao.save(comment);
        return ResponseResult.success("评论成功", null);
    }
}
