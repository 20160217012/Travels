package com.quan.travels.service;

import com.quan.travels.dao.UserDao;
import com.quan.travels.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired(required = false)      //如果加@Autowired后出现对象无法装配的问题就设置 required 为 false，也可以将@Autowired换成@Resource
    private UserDao userDao;

    //注册的业务处理
    @Override
    public void register(User user) {
        //先判断库中是否存在相同的用户名
        if(userDao.findByUsername(user.getUsername()) == null){     //如果数据库中该用户名不存在则写入
            userDao.save(user);
        }else{
            throw new  RuntimeException("用户名已存在！");
        }
    }

    //登录的业务处理
    @Override
    public User login(User user) {
        //数据库匹配表单的用户密码
        User userDB = userDao.findByUsername(user.getUsername());
        if(userDB!=null){               //如果用户名查得到
            if(userDB.getPassword().equals(user.getPassword())){     //匹配密码
                return userDB;
            }
            throw new RuntimeException("密码输入错误--");
        }else{
            throw new RuntimeException("用户名不存在！");
        }
    }
}
