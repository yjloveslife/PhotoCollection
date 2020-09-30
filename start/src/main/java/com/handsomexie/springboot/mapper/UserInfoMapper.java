package com.handsomexie.springboot.mapper;

import com.handsomexie.springboot.model.UserInfo;

public interface UserInfoMapper {
    int deleteByPrimaryKey(String username);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}