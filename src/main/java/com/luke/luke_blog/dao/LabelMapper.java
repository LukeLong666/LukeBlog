package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.Label;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 标签映射器
 *
 * @author zhang
 * @date 2020/07/21
 */
@Mapper
public interface LabelMapper {

    @Insert("insert into " +
            "tb_labels(id,name,`count`,create_time,update_time)" +
            " values(#{id},#{name},#{count},#{createTime},#{updateTime})")
    int save(Label labels);

    @Select("select id,name,`count` from tb_labels where name = #{name}")
    Label findOneByName(String name);

    @Update("update tb_labels set `count`=`count`+1,update_time=now() where name = #{name}")
    int updateCountByName(String name);

    @Select("select * from tb_labels order by `count` desc")
    List<Label> findAll();
}
