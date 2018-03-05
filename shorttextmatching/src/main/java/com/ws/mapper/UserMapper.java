package com.ws.mapper;

import com.ws.bean.User;

public interface UserMapper {

    int insertUser(User user);

    User selectUserByUserName(User user);

    int updateUser(User user);
}
