package com.luke.luke_blog.dao;

import com.luke.luke_blog.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

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
}
