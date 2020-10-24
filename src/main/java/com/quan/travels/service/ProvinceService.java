package com.quan.travels.service;

import com.quan.travels.entity.Province;

import java.util.List;

public interface ProvinceService {

    //查询当前页以及每一页显示的个数，参数1：当前页，参数2:每一页显示的记录数
    List<Province> findByPage(Integer page,Integer rows);

    //查询总条数(因为要根据总条数去获得总页数)
    Integer findTotals();

    //添加省份信息
    void save(Province province);

    //删除省份信息
    void delete(String id);

    //查询单个省份信息
    Province findOne(String id);

    //修改当前省份信息
    void update(Province province);
}
