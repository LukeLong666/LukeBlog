package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.RefreshToken;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 刷新令牌映射器
 *
 * @author zhang
 * @date 2020/07/25
 */
@Mapper
public interface RefreshTokenMapper {

    /**
     * 保存
     *
     * @param refreshToken 刷新令牌
     * @return int
     */
    @Insert("insert into tb_refresh_token(id,refresh_token,user_id,token_key,create_time,update_time) " +
            "values(#{id},#{refreshToken},#{userId},#{tokenKey},#{createTime},#{updateTime})")
    int save(RefreshToken refreshToken);

    /**
     * 找到一个令牌密钥
     *
     * @param token 令牌
     * @return {@link RefreshToken}
     */
    @Select("select * from tb_refresh_token where token_key = #{token}")
    RefreshToken findOneByTokenKey(String token);

    /**
     * 删除通过id
     *
     * @param id id
     * @return int
     */
    @Delete("delete from tb_refresh_token where id = #{id}")
    int deleteById(String id);

    @Delete("delete from tb_refresh_token where user_id = #{userId}")
    int deleteByUserId(String userId);
}
