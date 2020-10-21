package com.handsomexie.springboot.service.impl;

import com.handsomexie.springboot.mapper.PicInfoMapper;
import com.handsomexie.springboot.model.PicInfo;
import com.handsomexie.springboot.service.PicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PicInfoServiceImpl implements PicInfoService {
    @Autowired
    public PicInfoMapper picinfomapper;

    @Override
    public int insert(String pic_name, String pic_description, String username, long pid) {
        PicInfo picInfo = new PicInfo();
        picInfo.setUsername(username);
        picInfo.setPicDescription(pic_description);
        picInfo.setPicName(pic_name);
        picInfo.setPid(pid);
        int result = 0;
        if (SelectByPrimaryKey(pic_name) == null) {
            result = picinfomapper.insert(picInfo);
        }
        return result;
    }

    @Override
    public ArrayList<String> selectAll() {
        ArrayList<String> result = picinfomapper.selectAll();
        return result;
    }

    @Override
    public PicInfo SelectByPrimaryKey(String picname) {
        PicInfo picinfo = picinfomapper.selectByPrimaryKey(picname);
        return picinfo;
    }

    @Override
    public ArrayList<String> selectUpload(String username) {
        return picinfomapper.selectUpload(username);
    }

    @Override
    public int deleteByPrimaryKey(String picname) {
        return picinfomapper.deleteByPrimaryKey(picname);
    }


}
