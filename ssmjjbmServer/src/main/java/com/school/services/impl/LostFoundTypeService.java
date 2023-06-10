package com.school.services.impl;

import com.school.dao.LostFoundDao;
import com.school.services.interfaces.LostFoundType;
import com.school.utils.RedisService;
import com.school.utils.ResponseCode;
import com.school.utils.ServerResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class LostFoundTypeService implements LostFoundType {
    String key = "typeList";//key值用于redsi存储
    @Resource
    private LostFoundDao lostFoundDao;
    @Resource
    private RedisService redisService;

    @Override
    public ServerResponse getAllType() {
        //清空缓存
        redisService.del(key);//以免请求过多造成数据混淆
        List<String> lostfoundtypeList = lostFoundDao.getAllType();
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
        int typeIdByName = lostFoundDao.typeIdByName(name);
        return ServerResponse.createServerResponseBySuccess(typeIdByName, ResponseCode.GET_DATA_SUCCESS.getMsg());
    }
}
