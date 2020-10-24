package com.quan.travels.dao;

import com.quan.travels.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    //根据用户名查询用户
    User findByUsername(String username);
    //写入用户数据
    void save(User user);

}
