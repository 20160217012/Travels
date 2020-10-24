package com.quan.travels.dao;

import com.quan.travels.entity.Place;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlaceDao extends BaseDao<Place,String>{
    //因为是修改当前省份下的景点信息，所以景点业务的接口中应当定义的是基于当前省份去做数据库的增删改查

    List<Place> findByProvinceIdPage(@Param("start") Integer start,@Param("rows") Integer rows, @Param("provinceId") String provinceId);

    Integer findByProvinceIdCounts(String provinceId);
}
