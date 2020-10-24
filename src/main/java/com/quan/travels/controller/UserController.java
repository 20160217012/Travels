package com.quan.travels.controller;

import com.quan.travels.entity.Result;
import com.quan.travels.entity.User;
import com.quan.travels.service.UserService;
import com.quan.travels.utils.CreateImageCode;
import com.quan.travels.utils.ValidateImageCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController          //加上@Restcontroller后所有方法的返回值都会转换成json格式,而不需要再使用@ResponseBody进行转换
@RequestMapping("user")
@CrossOrigin           //因为是前后端分离的架构，后面肯定涉及到跨域的问题，所以这里先给一个允许跨域
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    /*生成验证码方法*/
    @GetMapping("getImage")
    public Map<String,String> getImage(HttpServletRequest request) throws Exception{    //这里是通过流的方式响应验证码
        Map<String,String> mapresult = new HashMap<>();
        CreateImageCode createImageCode = new CreateImageCode();
        //1.获取验证码
        String securityCode = createImageCode.getCode();
        //2.验证码存入sesssion
        String key = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());    //(1)这里直接使用session来存储每次前端拿到的的验证码是不行的，因为根本就拿不到，所以改用sessionContext
        request.getServletContext().setAttribute(key,securityCode);     //(2)使用sessionContext的话，这里就需要一个时间戳的key(其实只要每次值都不一样一个key就行，这也是前后端项目中关于验证码的在传值方面的做法)
        //3.生成图片
        BufferedImage image = createImageCode.getBuffImg();
        //4.响应浏览器
        //进行base64编码(在这里给验证码进行了base64编码，那么在前台页面做一个解码展示就可以了，这是异步请求的方式)
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image,"png",bos);
        String string = Base64Utils.encodeToString(bos.toByteArray());    //Spring框架提供的将base64码转换成文件流的一个工具类
        mapresult.put("key",key);
        mapresult.put("image",string);
        return mapresult;
    }

    /*用户注册方法*/
    @PostMapping("register")
    public Result register(String code, String key, @RequestBody User user, HttpServletRequest request){      //加@RequestBody是为了接收json格式的数据,因为最终String类型的数据是要转成JSON格式的

        Result result = new Result();

        log.info("接收的验证码：  "+code);
        log.info("接收的验证码的key：  "+key);
        log.info("接收到的user对象数据：  "+user);
        //校对验证码
        String keycode = (String) request.getServletContext().getAttribute(key);
        log.info("收到的验证码"+keycode);
        try {
            if(code.equalsIgnoreCase(keycode)){    //如果验证码校验通过，就将用户数据写入数据库
                //新用户数据写入数据库(在这一步之前最好是去写一个测试类，测试一下注册业务，也就是是否可以存入数据，特别注意配置文件中的扫描路径)
                userService.register(user);
                result.setMsg("注册成功");
            }else {                                    //否则的话就返回提示错误
                throw new RuntimeException("验证码错误");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(e.getMessage()).setState(false);
        }
        return result;
    }

    /*用户登录方法*/
    @RequestMapping("login")
    public  Result login(@RequestBody User user,HttpServletRequest request){
        Result result = new Result();
        log.info("接收到的表单数据：  "+user);
        try {
            User userDB = userService.login(user);
            //登录成功，就要保存当前登录的用户   applicationContex Redis userid userDB
            request.getServletContext().setAttribute(userDB.getId(),userDB);
            result.setMsg("登陆成功--").setUserId(userDB.getId());      //登录成功的同时要返回当前登录的用户的ID
        } catch (Exception e) {
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }
}
