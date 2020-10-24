package com.quan.travels.service;

import com.quan.travels.dao.ProvinceDao;
import com.quan.travels.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService{

    @Autowired(required = false)
    private ProvinceDao provinceDao;

    //查询当前页以及每一页显示的个数，参数1：当前页，参数2:每一页显示的记录数
    @Override
    public List<Province> findByPage(Integer page, Integer rows) {
        int start = (page-1)*rows;     //当前页的起始=(页数-1)*条数
        return provinceDao.findByPage(start,rows);
    }

    //查询总条数(因为要根据总条数去获得总页数)
    @Override
    public Integer findTotals() {
        return provinceDao.findTotals();
    }

    //添加省份
    @Override
    public void save(Province province) {
        province.setPlacecounts(0);   //因为是第一次添加省份，所以景点个数为0
        provinceDao.save(province);
    }

    //删除省份
    @Override
    public void delete(String id) {
        provinceDao.delete(id);
    }

    //查询单个数据
    @Override
    public Province findOne(String id) {
        return provinceDao.findOne(id);
    }

    //修改省份信息
    @Override
    public void update(Province province) {
        provinceDao.update(province);
    }





}
