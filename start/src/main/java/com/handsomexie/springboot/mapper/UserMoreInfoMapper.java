package com.handsomexie.springboot.mapper;

import com.handsomexie.springboot.model.UserMoreInfo;

public interface UserMoreInfoMapper {
    int deleteByPrimaryKey(String username);

    int insert(UserMoreInfo record);

    int insertSelective(UserMoreInfo record);

    UserMoreInfo selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(UserMoreInfo record);

    int updateByPrimaryKey(UserMoreInfo record);
}