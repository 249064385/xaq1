package com.xu.community.dao;

import com.xu.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    //根据用户id查询其发布的帖子
    List<DiscussPost> selectDiscussPosts(int userId,int offset, int limit);

    //查询总共有多少帖子
    //动态sql，如果只有一个参数并且在if里使用，则必须加别名
    int selectDiscussPostsRows(@Param("userId") int userId);


}
