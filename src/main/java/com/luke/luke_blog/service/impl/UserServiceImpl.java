package com.luke.luke_blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.luke.luke_blog.dao.RefreshTokenMapper;
import com.luke.luke_blog.dao.SettingsMapper;
import com.luke.luke_blog.dao.UserMapper;
import com.luke.luke_blog.pojo.RefreshToken;
import com.luke.luke_blog.pojo.Setting;
import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IUserService;
import com.luke.luke_blog.utils.*;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private IdWorker idWorker;

    @Resource
    private UserMapper userDao;

    @Resource
    private SettingsMapper settingsDao;

    @Resource
    private RefreshTokenMapper refreshTokenDao;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    Random random;

    @Resource
    private Gson gson;

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
        if (managerAccountState != null) {
            return ResponseResult.FAILURE("已经初始化过了");
        }

        //检查数据
        if (TextUtils.isEmpty(user.getUserName())) {
            return ResponseResult.FAILURE("用户名不能为空");
        }
        if (TextUtils.isEmpty(user.getPassword())) {
            return ResponseResult.FAILURE("密码不能为空");
        }
        if (TextUtils.isEmpty(user.getEmail())) {
            return ResponseResult.FAILURE("邮箱不能为空");
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
        int result1 = userDao.save(user);
        if (result1 == 0) {
            return ResponseResult.FAILURE("添加失败!");
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
        if (result2 == 0) {
            return ResponseResult.FAILURE("添加失败!");
        }
        return ResponseResult.SUCCESS("添加成功", null);
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
        if (TextUtils.isEmpty(captchaKey) || captchaKey.length() < 13) {
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
     * type:(register,forget,update)
     *
     * @param request      请求
     * @param emailAddress 电子邮件地址
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult sendEmail(String type, HttpServletRequest request, String emailAddress) {
        if (emailAddress == null) {
            return ResponseResult.FAILURE("邮箱地址不可以为空");
        }
        User userByEmail = userDao.findOneByEmail(emailAddress);
        //根据功能类型查询邮箱是否存在
        if ("register".equals(type) || "update".equals(type)) {
            if (userByEmail != null) {
                return ResponseResult.FAILURE("改邮箱已被注册");
            }
        } else if ("forget".equals(type)) {
            if (userByEmail == null) {
                return ResponseResult.FAILURE("改邮箱并未注册");
            }
        }
        //防止暴力发送,同一个邮箱,间隔不得低于30s,同一个ip,最多只能10次
        String remoteAddr = request.getRemoteAddr();
        if (remoteAddr != null) {
            remoteAddr = remoteAddr.replaceAll(":", "_");
        }
        log.info("remoteAddress--->:" + remoteAddr);
        //频率判断
        Integer ipSendTime = (Integer) redisUtil.get(Constants.User.KEY_EMAIL_SEND_IP + remoteAddr);
        if (ipSendTime != null && ipSendTime > 10) {
            return ResponseResult.FAILURE("您发送验证码也太频繁了");
        }
        Object addressSendTime = redisUtil.get(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress);
        if (addressSendTime != null) {
            return ResponseResult.FAILURE("您发送验证码也太频繁了");
        }

        //检查邮箱地址是否正确
        boolean isEmailFormatOk = TextUtils.isEmailAddress(emailAddress);
        if (!isEmailFormatOk) {
            return ResponseResult.FAILURE("邮箱地址格式不正确");
        }
        int code = random.nextInt(999999);
        if (code < 100000) {
            code += 100000;
        }
        //发送验证码,验证码范围6位数100000-999999
        try {
            System.out.println(code);
            taskService.sendEmailVerifyCode(String.valueOf(code), emailAddress);
        } catch (Exception e) {
            return ResponseResult.FAILURE("验证码发送失败,请稍后重试");
        }

        //记录发送记录和code
        if (ipSendTime == null) {
            ipSendTime = 0;
        }
        ipSendTime++;
        redisUtil.set(Constants.User.KEY_EMAIL_SEND_IP + remoteAddr, ipSendTime, 60 * 60);
        redisUtil.set(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress, "true", 30);
        //保存code
        redisUtil.set(Constants.User.KEY_EMAIL_CODE_CONTENT + emailAddress, String.valueOf(code), 60 * 10);
        return ResponseResult.SUCCESS("发送成功", null);
    }

    /**
     * 注册
     *
     * @param user 用户
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult register(User user, String emailCode, String captchaCode, String captchaKey, HttpServletRequest request) {
        String userName = user.getUserName();
        if (TextUtils.isEmpty(userName)) {
            return ResponseResult.FAILURE("用户名不可以为空!");
        }
        //检查当前用户名是否已经注册
        User userFromDbByUserName = userDao.findOneByUserName(userName);
        if (userFromDbByUserName != null) {
            return ResponseResult.FAILURE("该用户名已注册!");
        }
        //检查邮箱格式是否正确
        String email = user.getEmail();
        if (TextUtils.isEmpty(email)) {
            return ResponseResult.FAILURE("邮箱不可以为空!");
        }
        if (!TextUtils.isEmailAddress(email)) {
            return ResponseResult.FAILURE("邮箱地址格式不正确!");
        }
        //检查邮箱是否已经注册
        User userFromDbByEmail = userDao.findOneByEmail(email);
        if (userFromDbByEmail != null) {
            return ResponseResult.FAILURE("该邮箱已被注册!");
        }
        //检查邮箱验证码是否正确
        String emailVerifyCode = (String) redisUtil.get(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        if (TextUtils.isEmpty(emailVerifyCode)) {
            return ResponseResult.FAILURE("邮箱验证码无效!");
        }
        if (TextUtils.isEmpty(emailCode)) {
            return ResponseResult.FAILURE("请输入邮箱验证码!");
        }
        if (!emailVerifyCode.equals(emailCode)) {
            return ResponseResult.FAILURE("邮箱验证码错误!");
        } else {
            //清楚redis数据
            redisUtil.del(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        }
        //检查图灵验证码是否正确
        Long key = Long.parseLong(captchaKey);
        String captchaVerifyCode = (String) redisUtil.get(Constants.User.KEY_CAPTCHA_CONTENT + key);
        if (TextUtils.isEmpty(captchaVerifyCode)) {
            return ResponseResult.FAILURE("人类验证码无效!");
        }
        if (!captchaVerifyCode.equals(captchaCode)) {
            return ResponseResult.FAILURE("图灵验证码错误!");
        } else {
            //清楚redis数据
            redisUtil.del(Constants.User.KEY_CAPTCHA_CONTENT + key);
        }
        //密码加密
        String password = user.getPassword();
        if (TextUtils.isEmpty(password)) {
            return ResponseResult.FAILURE("密码不能为空!");
        }
        user.setPassword(passwordEncoder.encode(password));
        //补全数据
        String remoteAddr = request.getRemoteAddr();
        String userId = String.valueOf(idWorker.nextId());
        user.setId(userId);
        user.setState("1");
        user.setRegIp(remoteAddr);
        user.setLoginIp(remoteAddr);
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        user.setAvatar(Constants.User.DEFAULT_AVATAR);
        user.setRoles(Constants.User.ROLES_NORMAL);
        //保存到数据库
        userDao.save(user);
        //返回结果
        return ResponseResult.SUCCESS(20002, "注册成功", null);
    }

    /**
     * 登录
     *
     * @param captcha    验证码
     * @param captchaKey 验证码的关键
     * @param user       用户
     * @param request    请求
     * @param response   响应
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult doLogin(String captcha, String captchaKey, User user, HttpServletRequest request, HttpServletResponse response) {
        //验证码
        String captchaFromRedis = (String) redisUtil.get(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        log.info("Redis captcha ===> " + captchaFromRedis);
        if (!captcha.equals(captchaFromRedis)) {
            log.info("人类验证码失败");
            return ResponseResult.FAILURE("人类验证码失败");
        }
        String account = user.getUserName();
        if (TextUtils.isEmpty(account)) {
            return ResponseResult.FAILURE("请输入用户账号");
        }
        String password = user.getPassword();
        if (TextUtils.isEmpty(password)) {
            return ResponseResult.FAILURE("请输入用户密码");
        }
        //用户是否存在
        User userFromDB = userDao.findOneByUserName(account);
        if (userFromDB == null) {
            userFromDB = userDao.findOneByEmail(account);
        }
        if (userFromDB == null) {
            return ResponseResult.FAILURE("用户名或密码错误");
        }
        //匹配密码
        boolean matches = passwordEncoder.matches(password, userFromDB.getPassword());
        log.info("password matches result ===> " + matches);
        if (!matches) {
            return ResponseResult.FAILURE("用户名或密码错误");
        }
        //密码正确
        //判断用户状态
        if (!"1".equals(userFromDB.getState())) {
            return ResponseResult.FAILURE("该账号已被封禁或删除");
        }
        createToken(response, userFromDB);
        //清楚redis人类验证码
        redisUtil.del(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        log.info("人类验证码已从redis内清除");
        return ResponseResult.SUCCESS(20001, "登陆成功", null);
    }

    /**
     * 创建令牌
     *
     * @param response   响应
     * @param userFromDB 用户数据库
     * @return {@link String}
     */
    private String createToken(HttpServletResponse response, User userFromDB) {
        refreshTokenDao.deleteByUserId(userFromDB.getId());
        log.info("createToken()");
        //生成token
        Map<String, Object> claims = ClaimsUtils.user2Claims(userFromDB);
        //默认有效两个小时
        String token = JwtUtil.createToken(claims);
        log.info("token ==>>"+ token);
        //返回token的md5值,token会保存在redis里
        //前端访问携带MD5key,从redis中获取即可
        String tokenKey = DigestUtils.md5DigestAsHex(token.getBytes());
        log.info("tokenKey ==>>"+ tokenKey);
        //保存token到redis,有效期为2个小时,key是tokenKey
        redisUtil.set(Constants.User.KEY_TOKEN + tokenKey, token, Constants.TimeValue.HOUR * 2);
        //把tokenKey写道cookies里
        CookieUtils.setUpCookie(response, Constants.User.COOKIE_TOKEN_KEY, tokenKey);
        //这个要动态获取,可以从request获取
        //生成refreshToken(一个月)
        String refreshTokenValue = JwtUtil.createRefreshToken(userFromDB.getId(), Constants.TimeValue.MONTH);
        //保存到数据库里面
        //refreshToken tokenKey 用户ID 创建时间 更新时间
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(idWorker.nextId() + "");
        refreshToken.setRefreshToken(refreshTokenValue);
        refreshToken.setUserId(userFromDB.getId());
        refreshToken.setTokenKey(tokenKey);
        refreshToken.setCreateTime(new Date());
        refreshToken.setUpdateTime(new Date());
        //保存
        refreshTokenDao.save(refreshToken);
        log.info("refresh token 已保存在数据库");
        return tokenKey;
    }

    /**
     * 检查用户是否有登录
     * 如果登录,返回用户信息
     *
     * @param request  请求
     * @param response 响应
     * @return {@link User}
     */
    @Override
    public User checkUser(HttpServletRequest request, HttpServletResponse response) {
        log.info("checkUser()");
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        log.info("tokenKey ===>> "+tokenKey);
        User user = parseByTokenKey(tokenKey);
        if (user == null) {
            log.info("user == null");
            //根据refresh token判断
            //解析过期
            //去数据库查询refresh token
            RefreshToken refreshToken = refreshTokenDao.findOneByTokenKey(tokenKey);
            //如果不存在,就是没登陆
            if (refreshToken == null) {
                log.info("refreshToken == null");
                return null;
            }
            //如果存在,就解析refresh token
            try {
                log.info("存在,就解析refresh token");
                Claims claims = JwtUtil.parseJWT(refreshToken.getRefreshToken());
                //如果有效,创建新的token和新的refresh token
                String userId = refreshToken.getUserId();
                User userFromDb = userDao.findOneById(userId);
                //删除refreshToken记录
                refreshTokenDao.deleteById(refreshToken.getId());
                String newTokenKey = createToken(response, userFromDb);
                return parseByTokenKey(newTokenKey);
            } catch (Exception e1) {
                //如果refresh token 过期了,就当前访问没有登录
                log.error(e1.toString());
                return null;
            }
        }
        return user;
    }

    /**
     * 解析由令牌密钥
     *
     * @param tokenKey 令牌的关键
     * @return {@link User}
     */
    private User parseByTokenKey(String tokenKey) {
        log.info("parseByTokenKey()  tokenKey == >" + tokenKey);
        String token = (String) redisUtil.get(Constants.User.KEY_TOKEN+tokenKey);
        if (token != null) {
            log.info("token != null");
            try {
                Claims claims = JwtUtil.parseJWT(token);
                User user = ClaimsUtils.claims2User(claims);
                return user;
            } catch (Exception e) {
                log.error(e.toString());
                return null;
            }

        }
        log.info("token == null");
        return null;
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getUserInfo(String userId) {
        //从数据库获取
        User user = userDao.findOneById(userId);
        //如果不存在
        if (user == null) {
            return ResponseResult.FAILURE("用户不存在");
        }
        //如果存在,复制对象,清空不必要信息
        String userJson = gson.toJson(user);
        User newUser = gson.fromJson(userJson, User.class);
        newUser.setPassword("");
        newUser.setEmail("");
        newUser.setRegIp("");
        newUser.setLoginIp("");
        return ResponseResult.SUCCESS("获取成功",newUser);
    }


    /**
     * 检查电子邮件是否重复
     *
     * @param email 电子邮件
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult checkEmail(String email) {
        User oneByEmail = userDao.findOneByEmail(email);
        if (oneByEmail != null) {
            return ResponseResult.SUCCESS("当前邮箱已经注册",null);
        }else{
            return ResponseResult.FAILURE("当前邮箱没有注册");
        }
    }

    /**
     * 检查用户名是否重复
     *
     * @param userName 用户名
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult checkUserName(String userName) {
        User oneByUserName = userDao.findOneByUserName(userName);
        if (oneByUserName != null) {
            return ResponseResult.SUCCESS("当前用户名已经注册",null);
        }else{
            return ResponseResult.FAILURE("当前用户名没有注册");
        }
    }

    /**
     * 更新用户信息
     *
     * @param request  请求
     * @param response 响应
     * @param userId   用户id
     * @param user     用户
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult updateUserInfo(HttpServletRequest request, HttpServletResponse response, String userId, User user) {
        //检查是否已经登陆
        User userFromKey = checkUser(request, response);
        if (userFromKey == null) {
            log.info("账号未登录");
            return ResponseResult.ACCOUNT_NOT_LOGIN("账号未登录");
        }
        User userFromDb = userDao.findOneById(userFromKey.getId());
        log.info("checkUser user : "+userFromDb.toString());
        //判断用户的id是否一致,一致才可以修改
        if (!userFromDb.getId().equals(userId)) {
            log.info("无权修改");
            return ResponseResult.PERMISSION_DENY("无权修改");
        }
        //可以进行修改
        //头像,签名,用户名
        if (!TextUtils.isEmpty(user.getUserName())) {
            User oneByUserName = userDao.findOneByUserName(user.getUserName());
            if (oneByUserName != null) {
                return ResponseResult.FAILURE("该用户名已注册");
            }
            userFromDb.setUserName(user.getUserName());
        }
        if (!TextUtils.isEmpty(user.getAvatar())) {
            userFromDb.setAvatar(user.getAvatar());
        }
        userFromDb.setSign(user.getSign());
        userFromDb.setUpdateTime(new Date());
        log.info("userAccount.id = "+userFromDb.getId());
        int result = userDao.updateById(userFromDb);
        log.info("userDao.save : "+result);
        //删除redis里的token,下一次请求,就会根据refreshToken重新创建一个
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        redisUtil.del(Constants.User.KEY_TOKEN+tokenKey);
        log.info("删除旧的redis token缓存");
        return ResponseResult.SUCCESS("用户信息更新成功");
    }

    /**
     * 删除用户的id
     * 逻辑删除
     * 需要管理员权限
     * @param userId   用户id
     * @param request  请求
     * @param response 响应
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult deleteUserById(String userId, HttpServletRequest request, HttpServletResponse response) {
        //检验当前操作的用户是谁
        User currentUser = checkUser(request, response);
        if (currentUser == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN("用户未登录");
        }
        if (!Constants.User.ROLES_ADMIN.equals(currentUser.getRoles())) {
            return ResponseResult.PERMISSION_DENY("无权操作");
        }
        //可以操作
        int result = userDao.deleteUserByState(userId);
        if (result>0) {
            return ResponseResult.SUCCESS("删除成功", result);
        }
        return ResponseResult.FAILURE("用户不存在");
    }

    @Override
    public ResponseResult listUsers(HttpServletRequest request, HttpServletResponse response, int page, int size) {
        //检验当前操作的用户是谁
        User currentUser = checkUser(request, response);
        if (currentUser == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN("用户未登录");
        }
        if (!Constants.User.ROLES_ADMIN.equals(currentUser.getRoles())) {
            return ResponseResult.PERMISSION_DENY("无权操作");
        }
        //可以获取用户列表
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        PageHelper.startPage(page, size);
        List<User> listUsers = userDao.findAll();
        PageInfo<User> pageInfo = new PageInfo<>(listUsers);
        log.info("PageInfo =====> "+pageInfo.toString());
        return ResponseResult.SUCCESS("查询成功!", pageInfo);
    }
}
