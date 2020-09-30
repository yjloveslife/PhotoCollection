package com.handsomexie.springboot.web;

import com.handsomexie.springboot.model.UserInfo;
import com.handsomexie.springboot.model.UserMoreInfo;
import com.handsomexie.springboot.service.PicInfoService;
import com.handsomexie.springboot.service.UserInfoService;
import com.handsomexie.springboot.service.UserMoreInfoService;
import com.sun.xml.internal.ws.policy.sourcemodel.ModelNode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.element.NestingKind;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@MapperScan("com.handsomxie.springboot.mapper")
public class UserInfoController {

    @Value("${url}")
    private String url;
    //保存用
    @Value("${url.head}")
    private String urlhead;

    @Autowired
    public UserInfoService userinfoservice;

    @Autowired
    public PicInfoService picinfoserivce;

    @Autowired
    public UserMoreInfoService usermoreinfoservice;

    @RequestMapping(value = "/index")
    public String index(String username, String password, Model model, HttpServletRequest request) {
        if (username != null && password != null) {//是否登录
            int result = userinfoservice.selectByPrimaryKey(username, password);
            String message = loginreturn(result);
            if (result != 2) {
                model.addAttribute("loginmessage", message);
                return "login";
            }
        }

        /*
        读取的文件夹是所有图片
        File file = new File(url);
        File[] filelist = file.listFiles();
        List<String> urls = new ArrayList<>();
        for (File temp : filelist) {
            urls.add("/pic/" + temp.getName());
        }
        List<String> urls = new ArrayList<>();
        for (String str : temp) {
            urls.add("/pic/" + str);
        }
                model.addAttribute("urls", urls);//图片url，显示用

        */
        //读取数据库中存储的图片
        UserInfo userInfo = userinfoservice.getOneUserInfo(username);
        List<String> temp = picinfoserivce.selectAll();

//        System.out.println(System.currentTimeMillis());//时间
//        model.addAttribute("username", username);
        model.addAttribute("picname", temp);//图片名称，用作button id
        request.getSession().setAttribute("user", userInfo);
//        request.getSession().getAttribute("user")
        return "/index";
    }

    @RequestMapping("/index2")
    public String index2(Model model) {
        List<String> temp = picinfoserivce.selectAll();
        model.addAttribute("picname", temp);//图片名称，用作button id
        return "/index";
    }


    @RequestMapping(value = "/like")
    public @ResponseBody
    String like(String url, String src) {
        System.out.println(url);
        System.out.println(src);
        if (src.equals("/img/like.png"))//!!!!!!!!!!!!!!!!!!!!!!ajax
            return "/img/liked.png";
        else
            return "/img/like.png";
//        return "#"+url;
    }

    @RequestMapping(value = "/login")
    public String login(String username, String password, String phone, String email, String qq, Model model) {
        if (username != null && password != null && phone != null && email != null & qq != null) {
            int result = userinfoservice.register(username, password, phone);
            UserMoreInfo userMoreInfo = new UserMoreInfo();
            userMoreInfo.setUsername(username);
            userMoreInfo.setEmail(email);
            userMoreInfo.setQq(qq);
            userMoreInfo.setHead("normal_head.png");
            int result2 = usermoreinfoservice.insert(userMoreInfo);
            if (result == 0) {
                model.addAttribute("message", registereturn(result));
                return "register";
            }
        }
        return "/login";
    }

    @RequestMapping("/register")
    public String register(String message, Model model) {
        model.addAttribute("message", message);
        return "register";
    }

    @RequestMapping("/quit")
    public String quit(HttpServletRequest request, Model model) {
        request.getSession().removeAttribute("username");
        List<String> temp = picinfoserivce.selectAll();
        model.addAttribute("picname", temp);//图片名称，用作button id
        return "redirect:/index";
    }

    @RequestMapping("/mine")
    public String mine(Model model, HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        UserMoreInfo userMoreInfo = usermoreinfoservice.selectByPrimaryKey(userInfo.getUsername());
        model.addAttribute("username", userInfo.getUsername());
        model.addAttribute("head", userMoreInfo.getHead());
        model.addAttribute("email", userMoreInfo.getEmail());
        model.addAttribute("qq", userMoreInfo.getQq());
        return "/mine";
    }

    @RequestMapping("/headupload")
    public String headupload(@RequestParam(value = "head") MultipartFile head,Model model,HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        UserMoreInfo userMoreInfo = usermoreinfoservice.selectByPrimaryKey(userInfo.getUsername());
        model.addAttribute("username", userInfo.getUsername());
        model.addAttribute("head", userMoreInfo.getHead());

        if (head.getOriginalFilename().equals("")) {
            model.addAttribute("message","请勿上传空文件");
        } else {
            userMoreInfo.setHead(head.getOriginalFilename());
//            System.out.println(head.getOriginalFilename()+"!");
            usermoreinfoservice.updateByPrimaryKeySelective(userMoreInfo);
            try {
                head.transferTo(new File(urlhead+head.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/mine";
    }

    @RequestMapping("/userinfoedit")
    public String userinfoedit(Model model, HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        model.addAttribute("username", userInfo.getUsername());
        //添加用户信息
        UserMoreInfo userMoreInfo = usermoreinfoservice.selectByPrimaryKey(userInfo.getUsername());
        model.addAttribute("email", userMoreInfo.getEmail());
        model.addAttribute("qq", userMoreInfo.getQq());
        return "/userinfoedit";
    }

    @RequestMapping("/infoedit")
    public String infoedit(String email, String qq, HttpServletRequest request,Model model) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        UserMoreInfo userMoreInfo = new UserMoreInfo();
        userMoreInfo.setEmail(email);
        userMoreInfo.setQq(qq);
        userMoreInfo.setUsername(userInfo.getUsername());
        int result = usermoreinfoservice.updateByPrimaryKeySelective(userMoreInfo);
       model.addAttribute("username", userInfo.getUsername());
        model.addAttribute("head", userMoreInfo.getHead());
        return "/mine";
    }

    @RequestMapping(value = "/pic")
    public String pic() {
        return "/pic";
    }

    @RequestMapping(value = "/picupload", method = RequestMethod.POST)//上传图片
    public String picupload(@RequestParam(value = "upload") MultipartFile upload, Model model, HttpServletRequest request) {
        long pid = System.currentTimeMillis();
        if (upload.getOriginalFilename().equals("")) {
            model.addAttribute("message", "上传失败");
            return "/pic";
        }
        int result = 0;

        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("uploadmessage", "未登录，请登录后上传");
            return "/login";
        }
        result = picinfoserivce.insert(upload.getOriginalFilename(), "test", user.getUsername(), pid);
        try {
            if (result == 1) {
                upload.transferTo(new File(url + upload.getOriginalFilename()));
                model.addAttribute("message", "上传成功");
                model.addAttribute("picreturn", "/pic/" + upload.getOriginalFilename());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(result);
        return "/pic";
//        if (result == 1) {
//            return "/index";//success
//        } else {
//            return "/pic";
//        }
    }

    public String loginreturn(int a) {
        if (a == 0) {
            return "无此用户";
        } else if (a == 1) {
            return "用户名或密码错误";
        } else {
            return "登录成功";
        }
    }

    public String registereturn(int a) {
        if (a == 0) {
            return "已存在该用户";
        } else {
            return "注册成功";
        }
    }

    @RequestMapping("/test")
    public @ResponseBody
    String test() {
        return url;
    }
}

