package com.handsomexie.springboot.service;

import com.handsomexie.springboot.model.UserInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.catalina.User;

public interface UserInfoService {
    int selectByPrimaryKey(String username, String password);

    int register(String username, String password, String phone);

    UserInfo getOneUserInfo(String username);
}
