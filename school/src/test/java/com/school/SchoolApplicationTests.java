package com.school;

import com.school.mapper.UserMapper;
import com.school.services.impl.LostFoundTypeService;
import com.school.services.impl.UserService;
import com.school.utils.RedisService;
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
    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        System.err.println(userService.resetPwd("18682675515", "p0000000"));


    }
}
