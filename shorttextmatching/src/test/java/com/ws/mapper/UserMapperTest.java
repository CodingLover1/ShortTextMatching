package com.ws.mapper;

import com.ws.bean.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class UserMapperTest {

    @Autowired
    UserMapper userMapper;
    @Test
    public void insertUser() {
        User  user=new User();
        user.setUserName("ws");
        user.setUserPassword("adming");
        user.setUserSex("男");
        user.setUserPhone("15342731624");
        user.setCreateTime(new Date());

        int effectNum=userMapper.insertUser(user);
        assertEquals(1,effectNum);
    }

    @Test
    public void selectUserByUserName() {
    }
}