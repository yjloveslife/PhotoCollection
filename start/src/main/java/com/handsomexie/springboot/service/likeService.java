package com.handsomexie.springboot.service;

import com.handsomexie.springboot.model.like;
import java.util.ArrayList;

public interface likeService {
    ArrayList<String> selectByUsername(String username);

    ArrayList<String> selectByPicname(String picname);

    int insert(like record);

    like selectByPrimaryKey(String upid);

    int deleteByPrimaryKey(String upid);

    int deleteByPicname(String picname);
}
