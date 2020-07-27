package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.Category;
import com.luke.luke_blog.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 类别映射器
 *
 * @author zhang
 * @date 2020/07/27
 */
@Mapper
public interface CategoryMapper {


    @Insert("insert into " +
            "tb_categories(id,name,pinyin,description,`order`,status,create_time,update_time) " +
            "values(#{id},#{name},#{pinyin},#{description},#{order},#{status},#{createTime},#{updateTime})")
    int save(Category category);

    @Select("select * from tb_categories where id = #{categoryId}")
    Category findOneById(String categoryId);

    @Select("select * from tb_categories order by create_time DESC")
    List<User> findAll();
}
