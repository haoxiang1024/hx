package com.school.controller;

import com.school.services.interfaces.UserInterface;
import com.school.utils.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;


@Controller
public class UserController {
    @Resource
    UserInterface user;

    @ResponseBody
    @RequestMapping("/login")
    public ServerResponse login(String phone) {
        return user.loginRegister(phone);
    }
    @ResponseBody
    @RequestMapping("/loginByPwd")
    public ServerResponse loginByPwd(String phone,String pwd){
       return user.loginByPwd(phone,pwd);
    }
    @ResponseBody
    @RequestMapping("/resetPwd")
    public ServerResponse resetPwd(String phone,String newPwd){
        return user.resetPwd(phone, newPwd);
    }
    @ResponseBody
    @RequestMapping("/updatePic")
    public ServerResponse updatePic(MultipartFile upload_file,int id) throws IOException {
        String fileName = upload_file.getOriginalFilename();
        String filePath = System.getProperty("catalina.home") + File.separator + "webapps\\images" + File.separator + fileName;
        File dest = new File(filePath);
        upload_file.transferTo(dest);
        return user.updatePhoto(fileName,id);
    }
    @ResponseBody
    @RequestMapping("/updateAc")
    public ServerResponse updateAc(String nickname,String sex,int id){
        return user.updateAc(nickname, sex, id);
    }
}
