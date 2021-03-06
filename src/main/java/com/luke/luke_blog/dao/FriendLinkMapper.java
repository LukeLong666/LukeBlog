package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.FriendLink;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FriendLinkMapper {
    @Insert("insert into " +
            "tb_friends(id,name,logo,url,`order`,create_time,update_time) " +
            "values(#{id},#{name},#{logo},#{url},#{order},#{createTime},#{updateTime})")
    int save(FriendLink friendLink);

    @Select("select * from tb_friends where id = #{friendLinkId} and state='1'")
    FriendLink findOneById(String friendLinkId);

    @Select("select * from tb_friends")
    List<FriendLink> findAll();

    @Select("select * from tb_friends where state='1'")
    List<FriendLink> findAllByState(String state);

    @Delete("delete from tb_friends where id = #{id}")
    int deleteById(String id);

    @Update("update tb_friends set url=#{url},name=#{name},logo=#{logo},`order`=#{order} where id = #{id}")
    int updateById(FriendLink friendLinkFromDb);
}
