package com.school.services.interfaces;

import com.school.utils.ServerResponse;
import org.springframework.stereotype.Service;

@Service
public interface SearchNeedInfo {
    //搜索信息
    ServerResponse searchInfo(String value);

}
