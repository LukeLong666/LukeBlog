package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.Setting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 设置映射器
 *
 * @author zhang
 * @date 2020/07/21
 */
@Mapper
public interface SettingsMapper {

    /**
     * 发现的一个关键
     *
     * @param key 关键
     * @return {@link Setting}
     */
    @Select("select * from tb_settings where `key` = #{key}")
    Setting findOneByKey(String key);


    /**
     * 萨瓦河
     *
     * @param setting 设置
     * @return int
     */
    @Insert("insert into " +
            "tb_settings(id,`key`,`value`,create_time,update_time) " +
            "values(#{id},#{key},#{value},#{createTime},#{updateTime})")
    int save(Setting setting);

    @Update("update tb_settings set `value` = #{value},update_time = #{updateTime} where id = #{id}")
    int updateById(Setting setting);
}
