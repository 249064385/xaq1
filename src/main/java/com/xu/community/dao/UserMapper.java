package com.xu.community.dao;

import com.xu.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper {
    //根据ID查询用户
    User selectUserById(@Param("id") int id);

    //根据名字查询用户
    User selectUserByName(@Param("username") String username);

    //根据邮箱查询用户
    User selectUserByEmail(@Param("email") String email);

    //插入用户
    int insertUser(User user);

    //更新状态
    int updateStatus(@Param("id") int id,@Param("status") int status);


    int updateHeader(@Param("id") int id,@Param("headerUrl") String headerUrl);

    int updatePassword(@Param("id") int id,@Param("password") String password);

}
