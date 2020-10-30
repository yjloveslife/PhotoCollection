package com.handsomexie.springboot.web;

import com.handsomexie.springboot.model.UserInfo;
import com.handsomexie.springboot.model.UserMoreInfo;
import com.handsomexie.springboot.model.like;
import com.handsomexie.springboot.service.PicInfoService;
import com.handsomexie.springboot.service.UserInfoService;
import com.handsomexie.springboot.service.UserMoreInfoService;
import com.handsomexie.springboot.service.likeService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    //调用服务层
    @Autowired
    public likeService likeService;

    @Autowired
    public UserInfoService userinfoservice;

    @Autowired
    public PicInfoService picinfoserivce;

    @Autowired
    public UserMoreInfoService usermoreinfoservice;

    //    登录界面跳转回主页
    @RequestMapping(value = "/index")
    public String index(String username, String password, Model model, HttpServletRequest request) {
        if (username != null && password != null) {//是否登录
            int result = userinfoservice.selectByPrimaryKey(username, password);
            String message = loginreturn(result);
            if (result != 2) {
                model.addAttribute("message", message);
                return "/login";
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

        model.addAttribute("picname", temp);//所有上传de图片名，用作button id
        request.getSession().setAttribute("user", userInfo);
        if (userInfo != null) {
            ArrayList<String> likelist = likeService.selectByUsername(userInfo.getUsername());
            model.addAttribute("likelist", likelist);//所有喜欢的图
            UserMoreInfo userMoreInfo = usermoreinfoservice.selectByPrimaryKey(userInfo.getUsername());
            model.addAttribute("head", userMoreInfo.getHead());
        }
        return "/index";
    }

    //其他界面跳转回主页
    @RequestMapping("/index2")
    public String index2(Model model, HttpServletRequest request) {
        List<String> temp = picinfoserivce.selectAll();
        model.addAttribute("picname", temp);//图片名称，用作button id
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        if (userInfo != null) {
            String username = userInfo.getUsername();
            ArrayList<String> likelist = likeService.selectByUsername(username);
            model.addAttribute("likelist", likelist);
            UserMoreInfo userMoreInfo = usermoreinfoservice.selectByPrimaryKey(userInfo.getUsername());
            model.addAttribute("head", userMoreInfo.getHead());
        }
        return "/index";
    }

    //收藏功能 ajax用
    @RequestMapping(value = "/like")
    public @ResponseBody
    String like(String url, HttpServletRequest request) {
        System.out.println(url);//picname
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        if (userInfo == null) {
            return "un";
        }
        String username = userInfo.getUsername();
        like record = new like(username + url, username, url, String.valueOf(System.currentTimeMillis()));
        like like1 = likeService.selectByPrimaryKey(username + url);
        if (like1 == null) {
            likeService.insert(record);
        } else {
            likeService.deleteByPrimaryKey(username + url);
        }
        return url;
    }

    //登录功能
    @RequestMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    //注册界面
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/register_fn")
    public String register_fn(String username, String password, String phone, String email, String qq, Model model) {
        int result = userinfoservice.register(username, password, phone);
        if (result == 0) {
            model.addAttribute("message", "已存在该用户");
            return "/register";
        } else {
            UserMoreInfo userMoreInfo = new UserMoreInfo();
            userMoreInfo.setUsername(username);
            userMoreInfo.setEmail(email);
            userMoreInfo.setQq(qq);
            userMoreInfo.setHead("normal_head.png");
            usermoreinfoservice.insert(userMoreInfo);
            return "redirect:/login";
        }
    }


    //退出按钮
    @RequestMapping("/quit")
    public String quit(HttpServletRequest request, Model model) {
        request.getSession().removeAttribute("username");
        List<String> temp = picinfoserivce.selectAll();
        model.addAttribute("picname", temp);//图片名称，用作button id
        return "redirect:/index";
    }

    //    个人信息界面
    @RequestMapping("/mine")
    public String mine(Model model, HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        UserMoreInfo userMoreInfo = usermoreinfoservice.selectByPrimaryKey(userInfo.getUsername());
        model.addAttribute("username", userInfo.getUsername());
        model.addAttribute("head", userMoreInfo.getHead());
        model.addAttribute("email", userMoreInfo.getEmail());
        model.addAttribute("qq", userMoreInfo.getQq());
        ArrayList<String> urls = likeService.selectByUsername(userInfo.getUsername());//收藏的图
        model.addAttribute("picname", urls);
        return "/mine";
    }

    @RequestMapping("/mine2")
    public String mine2(Model model, HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        UserMoreInfo userMoreInfo = usermoreinfoservice.selectByPrimaryKey(userInfo.getUsername());
        model.addAttribute("username", userInfo.getUsername());
        model.addAttribute("head", userMoreInfo.getHead());
        model.addAttribute("email", userMoreInfo.getEmail());
        model.addAttribute("qq", userMoreInfo.getQq());
        ArrayList<String> urls = picinfoserivce.selectUpload(userInfo.getUsername());//上传的图
        model.addAttribute("picname", urls);
        ArrayList<String> likelist = likeService.selectByUsername(userInfo.getUsername());
        model.addAttribute("likelist", likelist);
        return "/mine2";
    }

    //上传的图片 ajax
    @RequestMapping("/mineupload")
    public @ResponseBody
    ArrayList<String> mineupload(String url, HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
//        System.out.println(userInfo);
        ArrayList<String> list_temp = picinfoserivce.selectUpload(userInfo.getUsername());
        return list_temp;
    }

    //头像上传后跳转回个人信息界面  重定向
    @RequestMapping("/headupload")
    public String headupload(@RequestParam(value = "head") MultipartFile head, Model model, HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        UserMoreInfo userMoreInfo = usermoreinfoservice.selectByPrimaryKey(userInfo.getUsername());
        model.addAttribute("username", userInfo.getUsername());
        model.addAttribute("head", userMoreInfo.getHead());
        if (head.getOriginalFilename().equals("")) {
            model.addAttribute("message", "请勿上传空文件");
        } else {
            userMoreInfo.setHead(head.getOriginalFilename());
//            System.out.println(head.getOriginalFilename()+"!");
            usermoreinfoservice.updateByPrimaryKeySelective(userMoreInfo);
            try {
                head.transferTo(new File(urlhead + head.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/mine";
    }

    //个人信息修改界面
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

    //信息修改后返回个人信息界面
    @RequestMapping("/infoedit")
    public String infoedit(String email, String qq, HttpServletRequest request, Model model) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        UserMoreInfo userMoreInfo = new UserMoreInfo();
        userMoreInfo.setEmail(email);
        userMoreInfo.setQq(qq);
        userMoreInfo.setUsername(userInfo.getUsername());
        int result = usermoreinfoservice.updateByPrimaryKeySelective(userMoreInfo);
        model.addAttribute("username", userInfo.getUsername());
        model.addAttribute("head", userMoreInfo.getHead());
        return "redirect:/mine";
    }

    //图片上传界面
    @RequestMapping(value = "/pic")
    public String pic() {
        return "/pic";
    }

    //图片上传功能
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
            return "login1";
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
        return "/pic";
    }

    @RequestMapping("/delete")
    public @ResponseBody
    String delete(String url1) {
        File file = new File(url + url1);
        System.out.println(url + url1);
        file.delete();//删除文件
        picinfoserivce.deleteByPrimaryKey(url1);//删除sql中的文件信息
        likeService.deleteByPicname(url1);
        return url1;
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

    @RequestMapping("/ajax/js")
    public String ajax_js() {
        return "/ajax_js";
    }

    @RequestMapping("/ajax/js/test")
    public @ResponseBody
    String ajax_js_hello(String username) {
        System.out.println(username + " " + "hello");
        return "username";
    }

    @RequestMapping("Responsive")
    public String responseive() {

        return "/Responsive";
    }

    @RequestMapping("/indextest")
    public String indextest(Model model, HttpServletRequest request) {
        List<String> temp = picinfoserivce.selectAll();
        model.addAttribute("picname", temp);//图片名称，用作button id
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        if (userInfo != null) {
            String username = userInfo.getUsername();
            ArrayList<String> likelist = likeService.selectByUsername(username);
            model.addAttribute("likelist", likelist);
            UserMoreInfo userMoreInfo = usermoreinfoservice.selectByPrimaryKey(userInfo.getUsername());
            model.addAttribute("head", userMoreInfo.getHead());
        }
        return "/indextest";
    }

    @RequestMapping("album")
    public String album(){
        return "/album";
    }
}

