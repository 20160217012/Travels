package com.quan.travels.service;


import com.quan.travels.entity.Place;

import java.util.List;

public interface PlaceService {

    //查询该省份所有景点信息
    List<Place> findByProvinceIdPage(Integer page,Integer rows,String provinceId);

    //统计该省份景点个数
    Integer findByProvinceIdCounts(String provinceId);

    //添加省份景点信息
    void save(Place place);

    //删除省份景点信息
    void delete(String id);

    //根据id查询该景点信息
    Place findOne(String id);

    //根据当前id修改对应的景点信息
    void update(Place place);
}
