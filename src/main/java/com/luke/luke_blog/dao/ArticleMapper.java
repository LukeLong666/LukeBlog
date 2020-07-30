package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.Article;
import com.luke.luke_blog.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into tb_article(id,`title`,user_id,category_id,`content`,`type`,`summary`,`labels`,create_time,update_time,state) " +
            "values(#{id},#{title},#{userId},#{categoryId},#{content},#{type},#{summary},#{label},#{createTime},#{updateTime},#{state})")
    int save(Article article);

    //级联查询,xml编写
    Article findOneById(String id);

    //复杂的sql语句用xml文件编写
    List<Article> findAll(@Param("keyword") String keyword,@Param("categoryId") String categoryId,@Param("state") String state);

    User findUserById(String userId);

    @Update("update tb_article set title = #{title},content=#{content},labels=#{label},category_id=#{categoryId},summary=#{summary} where id = #{id}")
    int updateById(Article articleFromDb);

    @Delete("delete from tb_article where id = #{articleId}")
    int deleteById(String articleId);
}
