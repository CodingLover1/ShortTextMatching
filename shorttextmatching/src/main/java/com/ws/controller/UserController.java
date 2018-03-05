package com.ws.controller;

import com.alibaba.fastjson.JSON;
import com.ws.bean.User;
import com.ws.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping(value="userlogin",method= RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public  String userLogin(HttpSession httpSession, User user){
        System.out.println(JSON.toJSONString(user));
        System.out.println(user.getUserName());
       User user1=userMapper.selectUserByUserName(user);
       if(user1!=null){
           System.out.println(JSON.toJSONString(user1));
           httpSession.setAttribute("user",user1);
           httpSession.setAttribute("loginflag",true);
           return JSON.toJSONString(user1);
       }
       else{
           return "";
       }
    }

    @RequestMapping(value="showuser")
    public String shouUser(){
        return  "ShowUser";
    }

    @RequestMapping("updateuser")
    @ResponseBody
    public String updateUser(User user,HttpSession httpSession){
        System.out.println("userId:"+user.getUserId());
        System.out.println(JSON.toJSONString(user)+"yes");
        int effectNum=userMapper.updateUser(user);
        System.out.println(effectNum+ "yes");
        if(effectNum==1||effectNum==0){
            return "{\"msg\":\"success\"}";
        }else{
            return "{\"msg\":\"fai\"}";
        }
    }


}
