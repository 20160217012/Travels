package com.quan.travels.test;

import com.quan.travels.TravelsApplication;
import com.quan.travels.entity.User;
import com.quan.travels.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TravelsApplication.class)
@RunWith(SpringRunner.class)
public class TestUserService {

    @Autowired
    private UserService userService;

    @Test
    public void testSave(){        //测试一下注册的业务
        User user = new User();
        user.setUsername("quan");
        user.setPassword("100863");
        user.setEmail("3526381910@qq.com");
        userService.register(user);
    }
}
