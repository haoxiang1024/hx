package com.school.dao;

import com.school.entity.SearchInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchDao {

    List<SearchInfo> searchInfoByValue(@Param("value")String value);


}
