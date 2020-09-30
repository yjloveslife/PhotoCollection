package com.handsomexie.springboot.mapper;

import com.handsomexie.springboot.model.PicInfo;

import java.util.ArrayList;

public interface PicInfoMapper {
    int deleteByPrimaryKey(String picName);

    int insert(PicInfo record);

    int insertSelective(PicInfo record);

    PicInfo selectByPrimaryKey(String picName);

    int updateByPrimaryKeySelective(PicInfo record);

    int updateByPrimaryKey(PicInfo record);

    ArrayList<String> selectAll();
}