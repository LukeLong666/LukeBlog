package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 评论道
 *
 * @author zhang
 * @date 2020/07/24
 */
@Mapper
public interface CommentMapper {

    @Insert("insert into tb_comment(id,parent_content,article_id,content,user_id,user_avatar,user_name,state,create_time,update_time)" +
            " values(#{id},#{parentContent},#{articleId},#{content},#{userId},#{userAvatar},#{userName},#{state},#{createTime},#{updateTime})")
    int save(Comment comment);

    @Select("select * from tb_comment where article_id = #{articleId} order by create_time")
    List<Comment> findAll(String articleId);

    @Select("select * from tb_comment where id = #{commentId}")
    Comment findOneById(String commentId);

    @Delete("delete from tb_comment where id =#{id}")
    int deleteById(String id);
}
