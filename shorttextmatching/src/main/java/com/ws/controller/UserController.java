package com.ws.controller;

import com.alibaba.fastjson.JSON;
import com.ws.bean.User;
import com.ws.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

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

    @RequestMapping(value="updateuser",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String updateUser(User user,HttpSession httpSession){
        System.out.println("要更新的信息："+JSON.toJSONString(user));
        int effectNum=userMapper.updateUser(user);
        Message message= new Message();
        if(effectNum==1||effectNum==0){
          message.setMsg("success");
        }else{
            message.setMsg("fail");
        }
        user=userMapper.selectUserByUserName(user);
        httpSession.setAttribute("user",user);
        System.out.println(JSON.toJSONString(message));
        return JSON.toJSONString(message);
    }

    @RequestMapping(value="adduser",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String addUser(User user,HttpSession httpSession){
        System.out.println("要添加的用户信息为:"+JSON.toJSONString(user));
        user.setCreateTime(new Date());
        int effectNum=userMapper.insertUser(user);
        Message message= new Message();
        if(effectNum==1||effectNum==0){
            System.out.println("新添加的用户ID:"+user.getUserId());
            httpSession.setAttribute("user",user);
            httpSession.setAttribute("loginflag",true);
            message.setMsg("success");
        }else{
            message.setMsg("fail");
        }
        return JSON.toJSONString(message);
    }

    @RequestMapping(value="userlogout",method =RequestMethod.GET)
    public String userLogout(HttpSession httpSession){
        httpSession.setAttribute("user",null);
        httpSession.setAttribute("loginflag",null);
        return "redirect:/";
    }

    @RequestMapping(value="userregist",method = RequestMethod.GET)
    public String userRegist(){
        return "regist";
    }

    class Message{
        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
