package com.school.mapper;

import com.school.entity.Lost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 李白
 * @description 针对表【lost】的数据库操作Mapper
 * @createDate 2023-05-15 20:15:24
 * @Entity com.school.entity.Lost
 */
@Mapper
public interface LostMapper {

    List<Lost> selectByTypeId(@Param("lostfoundtypeId") int lostfoundtypeId);

    //根据丢失物品中的userid获取用户名称
    String searchUserNameId(@Param("id") int id);

    //添加丢失物品信息
    boolean addLost(@Param("lost") Lost lost);

    //根据用户id获取用户发布的信息
    List<Lost> getAllByIdLostList(@Param("user_id") int user_id);

    //修改状态
    boolean updateState(@Param("id")int id,@Param("state")String state);

    //显示置顶信息
    List<Lost>showLostList(@Param("stick")int stick);




    //添加数据用的
    //更新失物图片
    void updateImg(@Param("phone") String phone, @Param("user_id") int user_id, @Param("id") int id);

    void updateImgs(@Param("img") String img,@Param("id") int id);

    //添加信息
    void addBatch(@Param("user_id") int user_id);

    //所有信息

    List<Lost> getAllList();


}
