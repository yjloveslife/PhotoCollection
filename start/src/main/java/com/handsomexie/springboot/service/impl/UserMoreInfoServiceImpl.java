package com.handsomexie.springboot.service.impl;

import com.handsomexie.springboot.mapper.UserMoreInfoMapper;
import com.handsomexie.springboot.model.UserMoreInfo;
import com.handsomexie.springboot.service.UserMoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMoreInfoServiceImpl implements UserMoreInfoService {
    @Autowired
    private UserMoreInfoMapper userMoreInfoMapper;
    @Override
    public int insert(UserMoreInfo userMoreInfo) {
        return userMoreInfoMapper.insert(userMoreInfo);
    }

    @Override
    public int updateByPrimaryKeySelective(UserMoreInfo userMoreInfo) {
        return userMoreInfoMapper.updateByPrimaryKeySelective(userMoreInfo);
    }

    @Override
    public UserMoreInfo selectByPrimaryKey(String name) {
        return userMoreInfoMapper.selectByPrimaryKey(name);
    }
}
