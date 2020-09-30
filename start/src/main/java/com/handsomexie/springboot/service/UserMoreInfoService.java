package com.handsomexie.springboot.service;

import com.handsomexie.springboot.model.UserMoreInfo;
import org.springframework.stereotype.Service;

public interface UserMoreInfoService {

    int insert(UserMoreInfo userMoreInfo);

    int updateByPrimaryKeySelective(UserMoreInfo userMoreInfo);

    UserMoreInfo selectByPrimaryKey(String name);

}
