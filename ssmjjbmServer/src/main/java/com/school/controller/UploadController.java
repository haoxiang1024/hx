package com.school.controller;

import com.school.services.interfaces.FoundDetail;
import com.school.services.interfaces.LostDetail;
import com.school.utils.Json;
import com.school.utils.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {

    // 用于处理安卓客户端传过来的文件
    @Resource
    private LostDetail lostDetail;
    @Resource
    private FoundDetail foundDetail;

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
        String fileName = upload_file.getOriginalFilename();
        String filePath = System.getProperty("catalina.home") + File.separator + "webapps\\images" + File.separator + fileName;
//        String filePath = session.getServletContext().getRealPath("/");
//        //对文件路径进行修改
//        String delString = "target\\ssmjjbm\\";
//        String endPath = filePath.replace(delString, "") + "/src/main/webapp/images/" + fileName;
        File dest = new File(filePath);
        upload_file.transferTo(dest);
        //判断是丢失or招领
        if (op != null && !op.equals("")) {
            if (op.equals("丢失")) {
                //失物
                //添加物品信息,调用json-update设置图片名称
                String newLostJson = Json.updateByKey(lostJson, "img", fileName);
                lostDetail.addLost(newLostJson);
            } else {
                //招领
                String newFoundJson = Json.updateByKey(foundJson, "img", fileName);
                foundDetail.addFound(newFoundJson);
            }
        } else {
            return ServerResponse.createServerResponseBySuccess("未选择失物or招领");
        }
        return ServerResponse.createServerResponseBySuccess("发布成功!");
    }

}
