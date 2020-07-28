package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.Looper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LooperMapper {


    @Insert("insert into tb_looper(id,title,`order`,target_url,image_url,create_time,update_time) " +
            "values(#{id},#{title},#{order},#{targetUrl},#{imageUrl},#{createTime},#{updateTime})")
    int save(Looper looper);

    @Select("select * from tb_looper where state = '1'")
    List<Looper> findAll();

    @Select("select * from tb_looper where state = '1' and id=#{looperId}")
    Looper findOneById(String looperId);

    @Update("update tb_looper set title=#{title},`order`=#{order},target_url=#{targetUrl},image_url=#{imageUrl},update_time = #{updateTime}" +
            " where id = #{id}")
    int updateById(Looper looper);

    @Delete("delete from tb_looper where id = #{looperId}")
    int deleteById(String looperId);
}
