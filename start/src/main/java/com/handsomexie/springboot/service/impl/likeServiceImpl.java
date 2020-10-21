package com.handsomexie.springboot.service.impl;

import com.handsomexie.springboot.mapper.likeMapper;
import com.handsomexie.springboot.model.like;
import com.handsomexie.springboot.service.likeService;
import com.sun.prism.shader.AlphaOne_RadialGradient_AlphaTest_Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class likeServiceImpl implements likeService {
    @Autowired
    private likeMapper likemapper;

    @Override
    public ArrayList<String> selectByUsername(String username) {
        return likemapper.selectByUsername(username);
    }

    @Override
    public ArrayList<String> selectByPicname(String picname) {
        return likemapper.selectByPicname(picname);
    }

    @Override
    public int insert(like record) {
        return likemapper.insert(record);
    }

    @Override
    public like selectByPrimaryKey(String upid) {
        return likemapper.selectByPrimaryKey(upid);
    }

    @Override
    public int deleteByPrimaryKey(String upid) {
        return likemapper.deleteByPrimaryKey(upid);
    }

    @Override
    public int deleteByPicname(String picname) {
        ArrayList<String> usersname = likemapper.selectByPicname(picname);
        for (String username : usersname) {
            likemapper.deleteByPrimaryKey(username + picname);
        }
        return 1;
    }
}
