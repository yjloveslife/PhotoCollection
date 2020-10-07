package com.handsomexie.springboot.mapper;

import com.handsomexie.springboot.model.like;

import java.util.ArrayList;

public interface likeMapper {
    int deleteByPrimaryKey(String upid);

    int insert(like record);

    int insertSelective(like record);

    like selectByPrimaryKey(String upid);

    ArrayList<String> selectByUsername(String username);

    int updateByPrimaryKeySelective(like record);

    int updateByPrimaryKey(like record);
}