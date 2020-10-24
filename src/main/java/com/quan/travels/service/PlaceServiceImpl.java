package com.quan.travels.service;

import com.quan.travels.dao.PlaceDao;
import com.quan.travels.dao.ProvinceDao;
import com.quan.travels.entity.Place;
import com.quan.travels.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlaceServiceImpl implements  PlaceService{

    @Autowired(required = false)
    private PlaceDao placeDao;
    @Autowired
    private ProvinceService provinceService;
    //先根据id查询出所有，并分页显示
    @Override
    public List<Place> findByProvinceIdPage(Integer page, Integer rows, String provinceId) {
        int start = (page-1)*rows;
        return placeDao.findByProvinceIdPage(start,rows,provinceId);
    }

    //统计该省的景点个数
    @Override
    public Integer findByProvinceIdCounts(String provinceId) {
        return placeDao.findByProvinceIdCounts(provinceId);
    }

    //添加景点信息
    @Override
    public void save(Place place) {
        //保存了景点后，该省份的景点要+1
        placeDao.save(place);
        //查询原有的省份景点信息
        Province province =  provinceService.findOne(place.getProvinceid());
        //更新省份信息的景点个数
        province.setPlacecounts(province.getPlacecounts()+1);
        provinceService.update(province);
    }

    //删除省份景点信息
    @Override
    public void delete(String id) {
        //删除景点的时候要注意，不能直接删除，要先保存景点的个数再做删除
        Place place =  placeDao.findOne(id);
        Province province =  provinceService.findOne(place.getProvinceid());     //先获取到当前操作的景点信息属于哪个省份
        province.setPlacecounts(province.getPlacecounts()-1);       //删除，该省份的景点个数减一
        provinceService.update(province);         //再根据省份信息更新该省份的景点个数
        //删除景点信息
        placeDao.delete(id);
    }

    //根据ID查询当前景点信息
    @Override
    public Place findOne(String id) {
        return placeDao.findOne(id);
    }

    //根据Id修改对应的景点信息，注意，包括该景点属于哪个省份，也就是provinceid
    @Override
    public void update(Place place) {
        placeDao.update(place);
    }
}
