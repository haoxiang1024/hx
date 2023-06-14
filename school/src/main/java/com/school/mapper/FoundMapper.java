package com.school.mapper;

import com.school.entity.Found;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
@Mapper
public interface FoundMapper {
    List<Found> selectByTypeId(@Param("lostfoundtypeId") int lostfoundtypeId);

    //根据物品中的userid获取用户名称
    String searchUserNameId(@Param("id") int id);

    //添加物品信息
    boolean addFound(@Param("found") Found found);

    //根据用户id获取用户发布的信息
    List<Found>getAllByIdFoundList(@Param("user_id")int user_id);

    //修改状态
    boolean updateState(@Param("id")int id,@Param("state")String state);

    //显示置顶信息
    List<Found>showFoundList(@Param("stick")int stick);
    //添加数据用的
    //所有信息
    List<Found> getAllList();
    //更新信息
    void updateFound(@Param("img")String img,@Param("user_id")int user_id,@Param("id")int id);

//修改电话号码
    void updatephone(@Param("user_id")int user_id,@Param("phone")String phone);
}
