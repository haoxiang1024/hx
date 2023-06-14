package com.school.services.impl;

import com.school.mapper.LostFoundMapper;
import com.school.mapper.SearchMapper;
import com.school.mapper.UserMapper;
import com.school.entity.Lostfoundtype;
import com.school.entity.SearchInfo;
import com.school.services.interfaces.SearchNeedInfo;
import com.school.utils.ServerResponse;
import com.school.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SearchInfoService implements SearchNeedInfo {
    @Autowired
    private SearchMapper searchMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LostFoundMapper lostFoundMapper;

    @Override
    public ServerResponse searchInfo(String value) {
        List<SearchInfo> searchInfos = searchMapper.searchInfoByValue(value);
        if (searchInfos.size() == 0) {
            return ServerResponse.createServerResponseBySuccess("没有找到相关信息");
        } else {
            //设置用户名
            for (SearchInfo searchInfo : searchInfos) {
                //获取用户id
                Integer userId = searchInfo.getUser_id();
                //根据用户id获取用户名
                String userNameId = userMapper.userInfo(userId).getNickname();
                //设置用户名
                searchInfo.setNickname(userNameId);
                //设置图片
                String updatePic = Util.updatePic(searchInfo.getImg());
                searchInfo.setImg(updatePic);
                //设置分类
                List<Lostfoundtype> lostfoundtypes = lostFoundMapper.GetAll();
                for (Lostfoundtype type : lostfoundtypes
                ) {
                    if (Objects.equals(type.getId(), searchInfo.getLostfoundtype_id())) {
                        searchInfo.setLostfoundtype(type);
                    }

                }

            }
        }
        return ServerResponse.createServerResponseBySuccess(searchInfos, "查找成功");
    }
}
