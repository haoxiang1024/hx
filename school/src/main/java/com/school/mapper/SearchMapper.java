package com.school.mapper;

import com.school.entity.SearchInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface SearchMapper {

    List<SearchInfo> searchInfoByValue(@Param("value")String value);


}
