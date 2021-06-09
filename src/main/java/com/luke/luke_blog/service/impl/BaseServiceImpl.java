package com.luke.luke_blog.service.impl;

import com.luke.luke_blog.service.IUserService;
import com.luke.luke_blog.utils.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
public class BaseServiceImpl {

    public final String TAG = "--> "+this.getClass().getSimpleName()+" ---> ";

    Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    protected IdWorker idWorker;

    @Resource
    protected IUserService userService;
}
