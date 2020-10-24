package com.quan.travels.controller;

import com.quan.travels.entity.Province;
import com.quan.travels.entity.Result;
import com.quan.travels.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("province")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    //根据ID查询省份信息
    @GetMapping("findOne")
    public Province findOne(String id){
        System.out.println("打印的id>>>>>"+id);
        return provinceService.findOne(id);
    }

    //修改省份信息
    @PostMapping("update")
    public Result update(@RequestBody Province province){
        Result result = new Result();
        try {
            provinceService.update(province);
            result.setMsg("修改省份信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }

    // 添加省份
    @PostMapping("save")
    public Result save(@RequestBody Province province){
        Result result = new Result();
        try {
            provinceService.save(province);
            result.setMsg("省份添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg("省份添加失败！");
        }
        return result;
    }

    //删除省份
    @GetMapping("delete")
    public Result delete(String id){
        Result result =  new Result();
        try {
            provinceService.delete(id);
            result.setMsg("省份已删除！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg("省份删除失败!");
        }
        return result;
    }
    /**
     * 查询所有的省份信息
     *
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("findByPage")
    private Map<String,Object> findByPage(Integer page,Integer rows){
        page = page==null?1:page;
        rows = rows==null?5:rows;
        HashMap<String,Object> map =  new HashMap<>();
        //1.先计算总页数
        Integer totals = provinceService.findTotals();
        //由总记录数得出总共可以分成多少页
        Integer totalPage = totals%rows==0?totals/rows:totals/rows+1;
        //2.做分页处理
        List<Province> provinces = provinceService.findByPage(page,rows);
        //3.将总记录数和分页参数存入map集合中
        map.put("provinces",provinces);
        map.put("totals",totals);
        map.put("totalPage",totalPage);
        map.put("page",page);
        return map;
    }
}
