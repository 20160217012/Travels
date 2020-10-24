package com.quan.travels.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Result {                 //创建这个类用于处理事务状态响应后，业务方法就可以返回处理状态了
    private Boolean state = true;     //事务处理(数据的CRUD)的状态
    private  String msg;       //事务处理的消息
    private String userId;     //存放当前登录的用户的ID
}
