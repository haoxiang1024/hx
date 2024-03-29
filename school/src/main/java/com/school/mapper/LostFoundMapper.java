package com.school.mapper;

import com.school.entity.Lostfoundtype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface LostFoundMapper {
    //获取所有分类
    List<Lostfoundtype> GetAll();

    //获取所有标题名称
    List<String> getAllType();

    //根据标题名称获取类型id
    int typeIdByName(@Param("name") String name);




}
