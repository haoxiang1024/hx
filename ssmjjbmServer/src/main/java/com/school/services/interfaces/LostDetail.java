package com.school.services.interfaces;

import com.school.entity.Lost;
import com.school.utils.ServerResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LostDetail {
    //获取丢失物品的详细信息
    ServerResponse getLostDetailList(int lostfoundtypeId);

    //获取用户名称
    ServerResponse getUserName(int id);

    //添加丢失物品信息(json格式)
    ServerResponse addLost(String lostJson);

    //从redis获取添加丢失物品信息
    ServerResponse getLost();

    //根据用户id获取用户发布的信息
    ServerResponse getAllByIdLostList(int user_id);

    //修改状态
    ServerResponse updateState(int id,String state,int user_id);

    //显示置顶信息
    ServerResponse showLostList(@Param("stick")int stick);
}
