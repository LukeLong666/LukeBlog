package com.luke.luke_blog.service.impl;

import com.luke.luke_blog.dao.SettingsMapper;
import com.luke.luke_blog.dao.UserMapper;
import com.luke.luke_blog.pojo.Setting;
import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IUserService;
import com.luke.luke_blog.utils.*;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * 用户服务impl
 *
 * @author zhang
 * @date 2020/07/21
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {

    @Resource
    private IdWorker idWorker;

    @Resource
    private UserMapper userDao;

    @Resource
    private SettingsMapper settingsDao;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    Random random;

    @Resource
    private TaskService taskService;


    /**
     * init经理账户
     *
     * @param user    用户
     * @param request 请求
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult initManagerAccount(User user, HttpServletRequest request) {
        //检查是否有初始化
        Setting managerAccountState = settingsDao.findOneByKey(Constants.Settings.MANAGER_ACCOUNT_INIT_STATE);
        if (managerAccountState!=null) {
            return ResponseResult.failure("已经初始化过了");
        }
        //检查数据
        if (TextUtils.isEmpty(user.getUserName())) {
            return ResponseResult.failure("用户名不能为空");
        }
        if (TextUtils.isEmpty(user.getPassword())) {
            return ResponseResult.failure("密码不能为空");
        }
        if (TextUtils.isEmpty(user.getEmail())) {
            return ResponseResult.failure("邮箱不能为空");
        }
        //补充数据
        user.setId(String.valueOf(idWorker.nextId()));
        user.setRoles(Constants.User.ROLES_ADMIN);
        user.setAvatar(Constants.User.DEFAULT_AVATAR);
        user.setState(Constants.User.DEFAULT_STATE);
        String localAddr = request.getLocalAddr();
        String remoteAddr = request.getRemoteAddr();
        user.setLoginIp(remoteAddr);
        user.setRegIp(remoteAddr);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        //对密码进行加密
        //原密码
        String originPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(originPassword);
        user.setPassword(encodedPassword);
        //保存到数据库
        int result1 = userDao.sava(user);
        if (result1==0) {
            return ResponseResult.failure("添加失败!");
        }
        //更细已经添加的标记
        //肯定没有
        Setting setting = new Setting();
        setting.setId(String.valueOf(idWorker.nextId()));
        setting.setCreateTime(new Date());
        setting.setUpdateTime(new Date());
        setting.setKey(Constants.Settings.MANAGER_ACCOUNT_INIT_STATE);
        setting.setValue("1");
        int result2 = settingsDao.sava(setting);
        if (result2==0) {
            return ResponseResult.failure("添加失败!");
        }
        return ResponseResult.success("添加成功",null);
    }

    /**
     * 创建验证码
     *
     * @param response   响应
     * @param captchaKey 验证码的关键
     * @throws IOException ioexception
     */
    @Override
    public void createCaptcha(HttpServletResponse response, String captchaKey) throws IOException {
        if (TextUtils.isEmpty(captchaKey)||captchaKey.length()<13) {
            return;
        }
        long key = 0L;
        try {
            key = Long.parseLong(captchaKey);
            //处理
        } catch (Exception e) {
            return;
        }
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(200, 60, 5);
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));
        // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        String content = specCaptcha.text().toLowerCase();

        //存入redis
        //十分钟有效
        redisUtil.set(Constants.User.KEY_CAPTCHA_CONTENT + key, content, 60 * 10);
        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }

    /**
     * 发送电子邮件验证码
     *
     * @param request      请求
     * @param emailAddress 电子邮件地址
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult sendEmail(HttpServletRequest request, String emailAddress){
        //防止暴力发送,同一个邮箱,间隔不得低于30s,同一个ip,最多只能10次
        String remoteAddr = request.getRemoteAddr();
        if (remoteAddr != null) {
            remoteAddr = remoteAddr.replaceAll(":", "_");
        }
        System.out.println(remoteAddr);
        //频率判断
        Integer ipSendTime = (Integer)redisUtil.get(Constants.User.KEY_EMAIL_SEND_IP + remoteAddr);
        if ( ipSendTime!= null&&ipSendTime>10) {
            return ResponseResult.failure("您发送验证码也太频繁了");
        }
        Object addressSendTime = redisUtil.get(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress);
        if (addressSendTime != null) {
            return ResponseResult.failure("您发送验证码也太频繁了");
        }

        //检查邮箱地址是否正确
        boolean isEmailFormatOk = TextUtils.isEmailAddress(emailAddress);
        if (!isEmailFormatOk) {
            return ResponseResult.failure("邮箱地址格式不正确");
        }
        int code = random.nextInt(999999);
        if (code<100000) {
            code+=100000;
        }
        //发送验证码,验证码范围6位数100000-999999
        try {
            System.out.println(code);
            taskService.sendEmailVerifyCode(String.valueOf(code), emailAddress);
        } catch (Exception e) {
            return ResponseResult.failure("验证码发送失败,请稍后重试");
        }

        //记录发送记录和code
        if (ipSendTime == null) {
            ipSendTime=0;
        }
        ipSendTime++;
        redisUtil.set(Constants.User.KEY_EMAIL_SEND_IP + remoteAddr, ipSendTime,60*60);
        redisUtil.set(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress, "true", 30);
        //保存code
        redisUtil.set(Constants.User.KEY_EMAIL_CODE_CONTENT+emailAddress, String.valueOf(code), 60 * 10);
        return ResponseResult.success("发送成功",null);
    }
}
