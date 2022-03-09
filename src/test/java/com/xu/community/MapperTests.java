package com.xu.community;


import com.xu.community.dao.DiscussPostMapper;
import com.xu.community.dao.UserMapper;
import com.xu.community.entity.DiscussPost;
import com.xu.community.entity.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void test(){
        User user = userMapper.selectUserById(101);
        System.out.println(user);

        user = userMapper.selectUserByName("liubei");
        System.out.println(user);

    }

    @Test
    public void test2(){
        User user = new User();
        user.setUsername("安旗");
        user.setPassword("xaq9810");
        user.setSalt("abcd");
        user.setEmail("249064@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/105.png");
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());

    }

    @Test
    public void test3(){
        int rows = userMapper.updateHeader(150,"http://www.nowcoder.com/106.png");
        System.out.println(userMapper.selectUserById(150).getHeaderUrl());
        rows = userMapper.updatePassword(150,"xaq8888");
        System.out.println(rows);
    }

    @Test
    public void test4(){
        int i = discussPostMapper.selectDiscussPostsRows(149);
        System.out.println(i);

        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(149, 0, 10);
        for(DiscussPost discussPost:discussPosts){
            System.out.println(discussPost);
        }
    }
}