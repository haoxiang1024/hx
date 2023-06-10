package com.school.services.interfaces;

import com.school.utils.ServerResponse;
import org.springframework.stereotype.Service;

@Service
public interface LostFoundType {
    //获取所有分类
    ServerResponse getAllType();

    //从redis获取分类
    ServerResponse getAllTypeByR();

    //获取分类id
    ServerResponse getIdByName(String name);


}

