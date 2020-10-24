package com.quan.travels.service;

import com.quan.travels.entity.User;

//注意，这里只是定义实现类的接口，在实现类的接口里定义要实现的业务方法，Impl类才是真正的实现类
public interface UserService {

    //注册
    void register(User user);

    //登录
    User login(User user);
}
