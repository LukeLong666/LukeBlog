package com.luke.luke_blog.service.impl;

import com.luke.luke_blog.dao.SettingsMapper;
import com.luke.luke_blog.dao.UserMapper;
import com.luke.luke_blog.pojo.Setting;
import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IUserService;
import com.luke.luke_blog.utils.Constants;
import com.luke.luke_blog.utils.IdWorker;
import com.luke.luke_blog.utils.TextUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
        //保存到数据库
        int result = userDao.sava(user);
        //更细已经添加的标记
        //肯定没有
        Setting setting = new Setting();
        setting.setId(String.valueOf(idWorker.nextId()));
        setting.setCreateTime(new Date());
        setting.setUpdateTime(new Date());
        setting.setKey(Constants.Settings.MANAGER_ACCOUNT_INIT_STATE);
        setting.setValue("1");
        settingsDao.sava(setting);
        return ResponseResult.success("添加成功",null);
    }
}
