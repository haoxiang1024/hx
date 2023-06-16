package com.school;

import com.school.entity.User;
import com.school.mapper.UserMapper;
import com.school.services.impl.LostFoundTypeService;
import com.school.utils.RedisService;
import com.school.utils.ServerResponse;
import com.school.utils.Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;

@SpringBootTest
class SchoolApplicationTests {
    @Autowired
    private ResourceLoader resourceLoader;
    @Value("${file.location}")
    String filelocation;//上传文件路径
    @Autowired
    RedisService redisService;
    @Autowired
    LostFoundTypeService lostFoundTypeService;
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        User userInfo = userMapper.userInfo(4);
        if(userInfo.getPassword()==null){
            //新用户的密码重置
            if (userMapper.resetPwd("1", "a1111111")) {
                userInfo = userMapper.userInfo(4);
                //设置头像
                String pic = Util.updatePic(userInfo.getPhoto());
                userInfo.setPhoto(pic);
                //重置成功
                System.err.println("成功");
            }
    }

}}
