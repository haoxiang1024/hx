package com.school.services.impl;

import com.alibaba.fastjson.JSON;
import com.school.dao.LostDao;
import com.school.dao.LostFoundDao;
import com.school.entity.Lost;
import com.school.entity.Lostfoundtype;
import com.school.services.interfaces.LostDetail;
import com.school.utils.RedisService;
import com.school.utils.ResponseCode;
import com.school.utils.ServerResponse;
import com.school.utils.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class LostDetailService implements LostDetail {
    String key = "lost";//key值用于redsi存储
    @Resource
    private LostDao lostDao;
    @Resource
    private RedisService redisService;
    @Resource
    private LostFoundDao lostFoundDao;


    @Override
    public ServerResponse getLostDetailList(int lostfoundtypeId) {
        List<Lost> lostDetailList = lostDao.selectByTypeId(lostfoundtypeId);
        if (lostDetailList == null) {
            return ServerResponse.createServerResponseByFail(ResponseCode.DATA_EMPTY.getCode(), ResponseCode.DATA_EMPTY.getMsg());
        }
        //设置用户名
        for (Lost lost : lostDetailList) {
            //获取用户id
            Integer userId = lost.getUserId();
            //根据用户id获取用户名
            String userNameId = lostDao.searchUserNameId(userId);
            //设置用户名
            lost.setNickname(userNameId);

        }

        return ServerResponse.createServerResponseBySuccess(lostDetailList);
    }

    @Override
    public ServerResponse getUserName(int id) {
        String userName = lostDao.searchUserNameId(id);
        if (userName == null) {
            return ServerResponse.createServerResponseByFail(ResponseCode.DATA_EMPTY.getCode(), ResponseCode.DATA_EMPTY.getMsg());
        }
        return ServerResponse.createServerResponseBySuccess(userName);
    }

    @Override
    public ServerResponse addLost(String lostJson) {
        //清空缓存
        redisService.del(key);//以免请求过多造成数据混淆
        //json字符串转java对象
        Lost lost = JSON.parseObject(lostJson, Lost.class);
        //添加信息并存入redis
        if (lostDao.addLost(lost) && redisService.set("lost", lost)) {
            return ServerResponse.createServerResponseBySuccess(ResponseCode.ADD_LOST_SUCCESS.getMsg());
        } else {
            return ServerResponse.createServerResponseByFail(ResponseCode.ADD_LOST_FAIL.getCode(), ResponseCode.ADD_LOST_FAIL.getMsg());
        }
    }

    @Override
    public ServerResponse getLost() {
        //从redis获取数据
        if (redisService.get("lost") != null) {
            Lost lost = (Lost) redisService.get("lost");
            return ServerResponse.createServerResponseBySuccess(lost, ResponseCode.GET_LOST_SUCCESS.getMsg());
        }
        return ServerResponse.createServerResponseByFail(ResponseCode.DATA_EMPTY.getCode(), ResponseCode.DATA_EMPTY.getMsg());

    }

    @Override
    public ServerResponse getAllByIdLostList(int user_id) {
        List<Lost> allByIdLostList = lostDao.getAllByIdLostList(user_id);
        if (allByIdLostList.size() == 0) {
            return ServerResponse.createServerResponseBySuccess("还未发布任何信息");
        } else {
            //设置用户名
            for (Lost lost : allByIdLostList) {
                //获取用户id
                Integer userId = lost.getUserId();
                //根据用户id获取用户名
                String userNameId = lostDao.searchUserNameId(userId);
                //设置用户名
                lost.setNickname(userNameId);
                //设置图片
                String updatePic = Util.updatePic(lost.getImg());
                lost.setImg(updatePic);
            }
        }
        return ServerResponse.createServerResponseBySuccess(allByIdLostList, "获取失物信息列表成功");
    }

    @Override
    public ServerResponse updateState(int id, String state, int user_id) {
        if (lostDao.updateState(id, state)) {
            List<Lost> allByIdLostList = lostDao.getAllByIdLostList(user_id);
            return ServerResponse.createServerResponseBySuccess(allByIdLostList, "状态已更改");
        }
        return ServerResponse.createServerResponseBySuccess("状态更改失败");
    }

    @Override
    public ServerResponse showLostList(int stick) {
        List<Lost> lists = lostDao.showLostList(stick);
        if (lists.size() == 0) {
            return ServerResponse.createServerResponseBySuccess("无置顶信息");
        }
        //设置用户名
        for (Lost lost : lists) {
            //获取用户id
            Integer userId = lost.getUserId();
            //获取分类id
            Integer lostfoundtypeId = lost.getLostfoundtypeId();
            //根据用户id获取用户名
            String userNameId = lostDao.searchUserNameId(userId);
            //设置用户名
            lost.setNickname(userNameId);
            //设置分类
            List<Lostfoundtype> lostfoundtypes = lostFoundDao.GetAll();
            for (Lostfoundtype type : lostfoundtypes) {
                if(Objects.equals(type.getId(), lostfoundtypeId)){
                    lost.setLostfoundtype(type);
                }
            }
            //设置图片
            String img = lost.getImg();
            String updatePic = Util.updatePic(img);
            lost.setImg(updatePic);
        }
        return ServerResponse.createServerResponseBySuccess(lists, "置顶信息");
    }


}
