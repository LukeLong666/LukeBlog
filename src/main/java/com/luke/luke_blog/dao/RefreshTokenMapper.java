package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.RefreshToken;
import org.apache.ibatis.annotations.*;

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
    @Insert("insert into tb_refresh_token(id,refresh_token,user_id,mobile_token_key,token_key,create_time,update_time) " +
            "values(#{id},#{refreshToken},#{userId},#{mobileTokenKey},#{tokenKey},#{createTime},#{updateTime})")
    int save(RefreshToken refreshToken);

    /**
     * 找到一个令牌密钥
     *
     * @param token 令牌
     * @return {@link RefreshToken}
     */
    @Select("select * from tb_refresh_token where token_key = #{token}")
    RefreshToken findOneByTokenKey(String token);

    @Select("select * from tb_refresh_token where mobile_token_key = #{token}")
    RefreshToken findOneByMobileTokenKey(String token);

    @Select("select * from tb_refresh_token where user_id = #{id}")
    RefreshToken findOneByUserId(String id);

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

    @Delete("delete from tb_refresh_token where token_key = #{tokenKey}")
    int deleteByTokenKey(String tokenKey);

    @Update("update tb_refresh_token set mobile_token_key = '' where mobile_token_key = #{tokenKey}")
    int deleteMobileTokenKey(String tokenKey);

    @Update("update tb_refresh_token set token_key = '' where token_key = #{tokenKey}")
    int deletePcTokenKey(String tokenKey);

    @Update("update tb_refresh_token set refresh_token=#{refreshToken},mobile_token_key=#{mobileTokenKey},token_key=#{tokenKey},update_time=#{updateTime} where id =#{id}")
    int updateById(RefreshToken refreshToken);
}
