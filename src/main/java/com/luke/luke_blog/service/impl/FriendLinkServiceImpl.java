package com.luke.luke_blog.service.impl;

import com.luke.luke_blog.dao.FriendLinkMapper;
import com.luke.luke_blog.pojo.FriendLink;
import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IFriendLinkService;
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

@Service("friendLinkService")
@Transactional
public class FriendLinkServiceImpl implements IFriendLinkService {

    public static final String TAG="FriendLinkServiceImpl --> ";

    Logger log = LoggerFactory.getLogger(FriendLinkServiceImpl.class);

    @Resource
    private IdWorker idWorker;

    @Resource
    private FriendLinkMapper friendLinkDao;

    @Resource
    private IUserService userService;

    @Override
    public ResponseResult addFriendLink(FriendLink friendLink) {
        //检查数据
        if (TextUtils.isEmpty(friendLink.getUrl())) {
            log.info(TAG+" addFriendLink() --> date validation : "+"链接为空");
            return ResponseResult.FAILURE("链接不可以为空");
        }
        if (TextUtils.isEmpty(friendLink.getLogo())) {
            log.info(TAG+" addFriendLink() --> date validation : "+"LOGO为空");
            return ResponseResult.FAILURE("LOGO不可以为空");
        }
        if (TextUtils.isEmpty(friendLink.getName())) {
            log.info(TAG+" addFriendLink() --> date validation : "+"名称为空");
            return ResponseResult.FAILURE("名称不可以为空");
        }
        //补全数据
        friendLink.setCreateTime(new Date());
        friendLink.setUpdateTime(new Date());
        friendLink.setId(idWorker.nextId()+"");
        log.info(TAG + " addFriendLink() --> date detail --> friendLink : " + friendLink.toString());
        //保存数据
        int result = friendLinkDao.save(friendLink);
        log.info(TAG+" addFriendLink() --> save result : "+result);
        //返回结果
        return result>0?ResponseResult.SUCCESS("添加友情链接成功!",result):ResponseResult.FAILURE("添加友情链接失败");
    }

    @Override
    public ResponseResult getFriendLink(String friendLinkId) {
        if (TextUtils.isEmpty(friendLinkId)) {
            log.info(TAG+" getFriendLink() --> date validation : "+"友情链接ID为空");
            return ResponseResult.FAILURE("友情链接ID不能为空");
        }
        FriendLink friendLink = friendLinkDao.findOneById(friendLinkId);
        if (friendLink == null) {
            log.info(TAG+" getFriendLink() --> date from db : "+"友情链接未找到");
            return ResponseResult.FAILURE("该友情链接不存在");
        }
        log.info(TAG+" getFriendLink() --> date from db : "+friendLink.toString());
        return ResponseResult.SUCCESS("获取友情链接成功",friendLink);
    }

    @Override
    public ResponseResult listFriendLinks() {
        //获取分类列表
        User user = userService.checkUser();
        List<FriendLink> friendLinkList = null;
        if (user == null||!Constants.User.ROLES_ADMIN.equals(user.getRoles())) {
            //普通或未等陆用户
            friendLinkList = friendLinkDao.findAllByState("1");
        }else {
            //管理员
            friendLinkList = friendLinkDao.findAll();
        }
        log.info(TAG+" listFriendLinks() --> categoryList : "+friendLinkList);
        log.info(TAG+" listFriendLinks() --> ResponseResult : "+"查询成功");
        return ResponseResult.SUCCESS("获取友情链接列表成功!", friendLinkList);
    }

    @Override
    public ResponseResult deleteById(String friendLinkId) {
        int result = friendLinkDao.deleteById(friendLinkId);
        log.info(TAG+" deleteById() --> delete result : "+result);
        return result>0?ResponseResult.SUCCESS("删除友情链接成功",result):ResponseResult.FAILURE("删除失败");
    }

    @Override
    public ResponseResult update(String friendLinkId, FriendLink friendLink) {
        //找出来
        FriendLink friendLinkFromDb = friendLinkDao.findOneById(friendLinkId);
        if (friendLinkFromDb == null) {
            log.info(TAG+" update() --> friendLinkFromDb : "+"友情链接不存在");
            return ResponseResult.FAILURE("友情链接不存在");
        }
        //内容改判断
        String logo = friendLink.getLogo();
        if (!TextUtils.isEmpty(logo)) {
            log.info(TAG+" update() --> logo : "+logo);
            friendLinkFromDb.setLogo(logo);
        }
        String name = friendLink.getName();
        if (!TextUtils.isEmpty(name)) {
            log.info(TAG+" update() --> name : "+name);
            friendLinkFromDb.setName(name);
        }
        String url = friendLink.getUrl();
        if (!TextUtils.isEmpty(url)) {
            log.info(TAG+" update() --> url : "+url);
            friendLinkFromDb.setUrl(url);
        }
        friendLinkFromDb.setOrder(friendLink.getOrder());
        //第三步保存数据
        int result = friendLinkDao.updateById(friendLinkFromDb);
        log.info(TAG+" update() --> result : "+result);
        //返回结果
        return result > 0 ? ResponseResult.SUCCESS("修改成功", result) : ResponseResult.FAILURE("修改失败");
    }
}
