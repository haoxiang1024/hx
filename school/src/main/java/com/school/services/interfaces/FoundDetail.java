package com.school.services.interfaces;

import com.school.utils.ServerResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface FoundDetail {
    //获取物品的详细信息
    ServerResponse getFoundDetailList(int lostfoundtypeId);

    //获取用户名称
    ServerResponse getUserName(int id);

    //添加物品信息(json格式)
    ServerResponse addFound(String foundJson);

    //从redis获取添加物品信息
    ServerResponse getFound();

    //根据用户id获取用户发布的信息
    ServerResponse getAllByIdFoundList(int user_id);

    //修改状态
    ServerResponse updateState(int id,String state,int user_id);

    //显示置顶信息
    ServerResponse showFoundList(@Param("stick")int stick);
}
