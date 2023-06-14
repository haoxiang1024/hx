package com.school;

import com.school.utils.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@SpringBootTest
class SchoolApplicationTests {
    @Autowired
    private ResourceLoader resourceLoader;
    @Value("${file.location}")
    String filelocation;//上传文件路径
    @Autowired
    RedisService redisService;

    @Test
    void contextLoads() {
       redisService.set("kye","123");
        System.err.println(redisService.get("kye"));

    }

}
