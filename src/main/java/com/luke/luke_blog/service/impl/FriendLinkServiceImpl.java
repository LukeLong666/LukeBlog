package com.luke.luke_blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luke.luke_blog.dao.FriendLinkMapper;
import com.luke.luke_blog.pojo.FriendLink;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IFriendLinkService;
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
    public ResponseResult listFriendLinks(int page, int size) {
        //获取分类列表
        if (page < Constants.Page.DEFAULT_PAGE) {
            log.info(TAG+" listFriendLinks() --> page < Constants.Page.DEFAULT_PAGE : "+true);
            page = Constants.Page.DEFAULT_PAGE;
        }
        if (size < Constants.Page.MIN_SIZE) {
            log.info(TAG+" listFriendLinks() --> size < Constants.Page.MIN_SIZE : "+true);
            size = Constants.Page.MIN_SIZE;
        }
        PageHelper.startPage(page, size);
        List<FriendLink> friendLinkList = friendLinkDao.findAll();
        log.info(TAG+" listFriendLinks() --> categoryList : "+friendLinkList);
        PageInfo<FriendLink> pageInfo = new PageInfo<>(friendLinkList);
        log.info(TAG+" listFriendLinks() --> pageInfo : "+pageInfo);
        log.info(TAG+" listFriendLinks() --> ResponseResult : "+"查询成功");
        return ResponseResult.SUCCESS("获取友情链接列表成功!", pageInfo);
    }
}
