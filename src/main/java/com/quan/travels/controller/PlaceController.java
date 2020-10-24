package com.quan.travels.controller;

import com.quan.travels.entity.Place;
import com.quan.travels.entity.Result;
import com.quan.travels.service.PlaceService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Value("${upload.dir}")     //这个注解的作用就是将图片上传的路径注入
    private String realPath;

    //根据Id修改对应的景点信息
    @PostMapping("update")
    public Result update(MultipartFile pic,Place place) throws IOException {
        System.out.println("访问到");
        Result result = new Result();
        try {
            //对接收的文件做Base64编码
            String picpath = Base64Utils.encodeToString(pic.getBytes());
            place.setPicpath(picpath);
            //处理文件上传
            String extension =  FilenameUtils.getExtension(pic.getOriginalFilename());    //先拿到上传的图片的名字
//            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+extension;
//            pic.transferTo(new File(realPath,newFileName));
            //保存修改操作
            placeService.update(place);
            result.setMsg("景点信息修改成功！");
        } catch (IOException e) {
            e.printStackTrace();
            result.setState(false).setMsg("景点信息修改失败！");
        } catch (IllegalStateException e) {
            result.setState(false).setMsg("景点信息修改失败！");
            e.printStackTrace();
        }
        return result;
    }

    //根据ID查询景点信息
    @GetMapping("findOne")
    public Place findOne(String id){
        return placeService.findOne(id);
    }

    //删除景点信息
    @GetMapping("delete")
    public Result delete(String id){
        Result result = new Result();
        try {
            placeService.delete(id);
            result.setMsg("景点信息删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg("景点信息删除失败!");
        }
        return result;
    }

    //添加景点信息
    @PostMapping("save")
    public Result save(MultipartFile pic, Place place) throws IOException {
        Result result = new Result();
        System.out.println("图片路径>>>>>>>>"+pic.getOriginalFilename());
        System.out.println("place对象>>>>>>"+place);
        System.out.println("打印pic>>>>>"+pic);
        try {
            //文件上传的处理
            String extension =  FilenameUtils.getExtension(pic.getOriginalFilename());    //先拿到上传的图片的名字
//            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+extension;
            //Base64编码处理
            place.setPicpath(Base64Utils.encodeToString(pic.getBytes()));    //由于是前后端分离的模式，所以图片的路径要做一个Base64的编码处理
            //文件上传
            File file = new File(realPath);
//            pic.transferTo(new File(file,newFileName));
            //保存place对象
            placeService.save(place);
            result.setMsg("添加省份景点信息成功！");
        } catch (IOException e) {
            e.printStackTrace();
            result.setState(false).setMsg("添加省份景点信息失败！");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            result.setState(false).setMsg("添加省份景点信息失败！");
        }
        return result;
    }

    //根据省份id查询景点
    @GetMapping("findAllPlace")
    public Map<String,Object> findAllPlace(Integer page,Integer rows, String provinceId){
        System.out.println("访问到");
        HashMap<String,Object> result = new HashMap<>();
        page = page==null?1:page;
        rows = rows==null?3:rows;
        //这里查到的是当前的省份的景点的集合
        List<Place> places =  placeService.findByProvinceIdPage(page,rows,provinceId);
        //对查到的景点信息进行分页处理
        Integer counts =  placeService.findByProvinceIdCounts(provinceId);
        //计算总页数
        Integer totalPage = counts%rows==0?counts/rows:counts/rows+1;
        result.put("places",places);
        result.put("page",page);          //页码
        result.put("counts",counts);       //总条数
        result.put("totalPage",totalPage);     //总页数
        return result;
    }
}
