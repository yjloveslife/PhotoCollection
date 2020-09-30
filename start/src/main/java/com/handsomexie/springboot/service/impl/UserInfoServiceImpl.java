package com.handsomexie.springboot.service.impl;

import com.handsomexie.springboot.mapper.UserInfoMapper;
import com.handsomexie.springboot.model.UserInfo;
import com.handsomexie.springboot.service.UserInfoService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    public UserInfoMapper userinfomapper;

    @Override
    public int selectByPrimaryKey(String username, String password) {//login
        UserInfo userInfo = userinfomapper.selectByPrimaryKey(username);
        if (userInfo == null) {
            return 0;//null
        } else if (userInfo.getPassword() .equals(password) ) {
            return 2;//success
        }
        return 1;//failure
    }

    @Override
    public int register(String username, String password, String phone) {
        UserInfo userInfo = userinfomapper.selectByPrimaryKey(username);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            userInfo.setPhone(phone);
            userinfomapper.insert(userInfo);
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public UserInfo getOneUserInfo(String username) {
        return userinfomapper.selectByPrimaryKey(username);
    }
}
