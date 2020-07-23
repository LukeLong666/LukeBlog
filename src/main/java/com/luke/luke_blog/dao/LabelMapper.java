package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.Label;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签映射器
 *
 * @author zhang
 * @date 2020/07/21
 */
@Mapper
public interface LabelMapper {

    @Insert("insert into " +
            "tb_labels(id,name,count,create_time,update_time)" +
            " values(#{id},#{name},#{count},#{createTime},#{updateTime})")
    int save(Label labels);
}
