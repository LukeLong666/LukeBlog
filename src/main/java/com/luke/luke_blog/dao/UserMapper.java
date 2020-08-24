package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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
    int save(User user);


    /**
     * 根据用户名查找用户
     *
     * @param userName 用户名
     * @return {@link User}
     */
    @Select("select * from tb_user where user_name=#{userName} and id!=#{id}")
    User findOneByUserNameAndId(String userName,String id);

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


    /**
     * 更新通过id
     *
     * @param userAccount 用户帐户
     * @return int
     */
    @Update("update tb_user set user_name = #{userName},avatar = #{avatar},sign=#{sign},update_time=#{updateTime},login_ip=#{loginIp} where id = #{id}")
    int updateById(User userAccount);


    @Update("update tb_user set state='0' where id = #{userId}")
    int deleteUserByState(String userId);

    @Update("update tb_user set state='1' where id = #{userId}")
    int UndeleteUserByState(String userId);

    /**
     * 带有模糊查询,移至xml
     * @param userName 用户名
     * @param email    电子邮件
     * @return {@link List<User>}
     */
    List<User> findAll(String userName,String email);


    @Update("update tb_user set password = #{encode} where email = #{email}")
    int updatePasswordByEmail(String encode, String email);


    @Update("update tb_user set email = #{email} where id = #{id}")
    int updateEmailById(String email, String id);
}
