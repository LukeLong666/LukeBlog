package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户映射器
 *
 * @author zhang
 * @date 2020/07/21
 */
@Mapper
public interface UserMapper {


    /**
     * 萨瓦河
     *
     * @param user 用户
     * @return int
     */
    @Insert("insert into " +
            "tb_user(id,user_name,password,roles,avatar,email,sign,state,reg_ip,login_ip,create_time,update_time) " +
            "values(#{id},#{userName},#{password},#{roles},#{avatar},#{email},#{sign},#{state},#{regIp},#{loginIp}," +
            "#{createTime},#{updateTime})")
    int sava(User user);
}
