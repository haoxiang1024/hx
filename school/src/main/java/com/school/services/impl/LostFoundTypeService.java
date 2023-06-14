package com.school.services.impl;

import com.school.mapper.LostFoundMapper;
import com.school.services.interfaces.LostFoundType;
import com.school.utils.RedisService;
import com.school.utils.ResponseCode;
import com.school.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LostFoundTypeService implements LostFoundType {
    String key = "typeList";//key值用于redsi存储
    @Autowired
    private LostFoundMapper lostFoundMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public ServerResponse getAllType() {
        //清空缓存
        redisService.del(key);//以免请求过多造成数据混淆
        List<String> lostfoundtypeList = lostFoundMapper.getAllType();
        if (lostfoundtypeList == null) {
            //数据为空
            return ServerResponse.createServerResponseByFail(ResponseCode.DATA_EMPTY.getCode(), ResponseCode.DATA_EMPTY.getMsg());
        } else if (redisService.ListOperation(key, lostfoundtypeList)) {
            //存入redis成功
            return ServerResponse.createServerResponseBySuccess(lostfoundtypeList, ResponseCode.ADD_ALL_TYPE.getMsg());
        } else if (!redisService.ListOperation(key, lostfoundtypeList)) {
            //存入redis失败
            return ServerResponse.createServerResponseBySuccess(ResponseCode.ADD_ALL_TYPE_FAIL.getMsg());
        } else {
            //返回数据
            return ServerResponse.createServerResponseBySuccess(lostfoundtypeList);
        }

    }

    @Override
    public ServerResponse getAllTypeByR() {
        //获取redsi数据成功
        List<String> list = redisService.GetList(key, 0, -1);//获取的list顺序颠倒需要恢复
        Collections.reverse(list);//恢复顺序
        return ServerResponse.createServerResponseBySuccess(list, ResponseCode.GET_ALL_TYPE.getMsg());
    }

    @Override
    public ServerResponse getIdByName(String name) {
        //中英文转换
        String[] en = {"Digital Devices", "Certificates", "Daily Necessities", "Clothing and Apparel", "Other"};
        String[] cn = {"数码设备", "证件", "日用品", "服饰", "其他"};
        Map<String, String> map = new HashMap<>();//建立关系
        int typeIdByName = 0;
        for (int i = 0; i < en.length; i++) {
            map.put(en[i], cn[i]);
        }
        for (String str : cn) {
            if (name.equals(str)) {
                typeIdByName = lostFoundMapper.typeIdByName(name);
            } else {
                String s = map.get(name);
                typeIdByName = lostFoundMapper.typeIdByName(s);
            }
        }
        return ServerResponse.createServerResponseBySuccess(typeIdByName, ResponseCode.GET_DATA_SUCCESS.getMsg());
    }
}
