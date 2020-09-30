package com.handsomexie.springboot.service;

import com.handsomexie.springboot.model.PicInfo;

import java.util.ArrayList;

public interface PicInfoService {
    int insert(String pic_name,String pic_description,String username,long pid);
    ArrayList<String> selectAll();
    PicInfo SelectByPrimaryKey(String picname);
}

