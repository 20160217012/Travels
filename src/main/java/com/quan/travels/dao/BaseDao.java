package com.quan.travels.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDao<T,K>{     //传入两个泛型，第一个表示当前操作的类型，第二个代表操作的组件的类型
    void save(T t);
    void update(T t);        //修改信息
    void delete(K k);
    List<T> findAll();        //查询所有
    T findOne(K k);           //查询单条数据
    List<T> findByPage(@Param("start") Integer start, @Param("rows") Integer rows);    //分页查询
    Integer findTotals();    //省份
}
