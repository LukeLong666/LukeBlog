package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ImageMapper {

    @Insert("insert into tb_images(id,user_id,path,name,content_type,url,state,create_time,update_time) " +
            "values(#{id},#{userId},#{path},#{name},#{contentType},#{url},#{state},#{createTime},#{updateTime})")
    int save(Image image);

    @Select("select * from tb_images where state = '1'")
    List<Image> findAll();

    @Update("update tb_images set state = '0' where id = #{imageId}")
    int deleteById(String imageId);
}
