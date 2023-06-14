package com.school.controller;

import com.school.services.interfaces.FoundDetail;
import com.school.services.interfaces.LostDetail;
import com.school.utils.Json;
import com.school.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

@Controller
public class UploadController {

    // 用于处理安卓客户端传过来的文件
    @Autowired
    private LostDetail lostDetail;
    @Autowired
    private FoundDetail foundDetail;
    @Value("${file.location}")
    String filelocation;//上传文件路径
    /**
     * @param upload_file 上传的文件
     * @param lostJson    丢失物品json数据
     * @param foundJson   招领物品json数据
     * @param op          此操作代表是上传丢失物品or招领物品
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/upload")
    public ServerResponse upload(MultipartFile upload_file, String lostJson, String foundJson, String op) throws IOException {
        String filename = upload_file.getOriginalFilename();
        InputStream inputStream = upload_file.getInputStream();
        File file = new File("src/main/resources/static/upload/" + filename);
        OutputStream outputStream = Files.newOutputStream(file.toPath());
        int length;
        byte[] bytes = new byte[1024];
        while ((length = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
        }
        inputStream.close();
        outputStream.close();
        //判断是丢失or招领
        if (op != null && !op.equals("")) {
            if (op.equals("丢失")) {
                //失物
                //添加物品信息,调用json-update设置图片名称
                String newLostJson = Json.updateByKey(lostJson, "img", filename);
                lostDetail.addLost(newLostJson);
            } else {
                //招领
                String newFoundJson = Json.updateByKey(foundJson, "img", filename);
                foundDetail.addFound(newFoundJson);
            }
        } else {
            return ServerResponse.createServerResponseBySuccess("未选择失物or招领");
        }
        return ServerResponse.createServerResponseBySuccess("发布成功!");
    }

}
