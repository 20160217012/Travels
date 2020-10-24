package com.quan.travels.test;

import com.quan.travels.TravelsApplication;
import com.quan.travels.entity.Province;
import com.quan.travels.entity.User;
import com.quan.travels.service.ProvinceService;
import com.quan.travels.service.UserService;
import com.sun.org.apache.xalan.internal.xslt.Process;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.util.List;

@SpringBootTest(classes = TravelsApplication.class)
@RunWith(SpringRunner.class)
public class TestProvinceService {

    @Autowired
    private ProvinceService provinceService;

    @Test
    public void testFindByPage(){        //测试一下分页查询的业务
        List<Province> list =  provinceService.findByPage(1,5);
        list.forEach(pr->{
            System.out.println(pr);
        });
    }
}
