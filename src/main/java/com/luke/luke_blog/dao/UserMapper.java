package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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


    /**
     * 根据用户名查找用户
     *
     * @param userName 用户名
     * @return {@link User}
     */
    @Select("select * from tb_user where user_name=#{userName}")
    User findOneByUserName(String userName);


    /**
     * 根据邮箱地址找用户
     *
     * @param emailAddress 邮箱地址
     * @return {@link User}
     */
    @Select("select * from tb_user where email=#{emailAddress}")
    User findOneByEmail(String emailAddress);

    /**
     * 找到一个通过id
     *
     * @param id id
     * @return {@link User}
     */
    @Select("select * from tb_user where id = #{id}")
    User findOneById(String id);


}
