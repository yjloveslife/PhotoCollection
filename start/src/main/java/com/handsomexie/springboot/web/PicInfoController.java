package com.handsomexie.springboot.web;

import com.handsomexie.springboot.service.PicInfoService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
@MapperScan("com.handsomexie.springboot.mapper")
public class PicInfoController {
    @Value("${url}")
    private String url;

    @Autowired
    public PicInfoService picinfoserivce;

}
