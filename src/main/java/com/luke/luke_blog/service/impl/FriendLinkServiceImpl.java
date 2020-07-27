package com.luke.luke_blog.service.impl;

import com.luke.luke_blog.dao.FriendLinkMapper;
import com.luke.luke_blog.pojo.FriendLink;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IFriendLinkService;
import com.luke.luke_blog.utils.IdWorker;
import com.luke.luke_blog.utils.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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
}
