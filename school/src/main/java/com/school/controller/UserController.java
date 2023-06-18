package com.school.controller;

import com.school.services.interfaces.UserInterface;
import com.school.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;


@Controller
public class UserController {
    @Autowired
    UserInterface userInterface;
    @Value("${file.location}")
    String filelocation;//上传文件路径
    @Autowired
    private ResourceLoader resourceLoader;
    @ResponseBody
    @RequestMapping("/login")
    public ServerResponse login(String phone) {
        return userInterface.loginRegister(phone);
    }
    @ResponseBody
    @RequestMapping("/loginByPwd")
    public ServerResponse loginByPwd(String phone,String pwd){
       return userInterface.loginByPwd(phone,pwd);
    }
    @ResponseBody
    @RequestMapping("/resetPwd")
    public ServerResponse resetPwd(String phone,String newPwd){
        return userInterface.resetPwd(phone, newPwd);
    }
    @ResponseBody
    @RequestMapping("/updatePic")
    public ServerResponse updatePic(MultipartFile upload_file,int id) throws IOException {
        String filename = upload_file.getOriginalFilename();
        InputStream inputStream = upload_file.getInputStream();
        File file = new File("/usr/project/upload/" + filename);
        OutputStream outputStream = Files.newOutputStream(file.toPath());
        int length;
        byte[] bytes = new byte[1024];
        while ((length = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
        }
        inputStream.close();
        outputStream.close();
        return userInterface.updatePhoto(filename,id);
    }
    @ResponseBody
    @RequestMapping("/updateAc")
    public ServerResponse updateAc(String nickname,String sex,int id){
        return userInterface.updateAc(nickname, sex, id);
    }
}
