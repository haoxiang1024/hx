package com.school.services.impl;

import com.alibaba.fastjson.JSON;
import com.school.dao.FoundDao;
import com.school.dao.LostFoundDao;
import com.school.entity.Found;
import com.school.entity.Lostfoundtype;
import com.school.services.interfaces.FoundDetail;
import com.school.utils.RedisService;
import com.school.utils.ResponseCode;
import com.school.utils.ServerResponse;
import com.school.utils.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class FoundDatailService implements FoundDetail {
    String key = "found";//key值用于redsi存储
    @Resource
    private FoundDao foundDao;
    @Resource
    private RedisService redisService;
    @Resource
    private LostFoundDao lostFoundDao;

    @Override
    public ServerResponse getFoundDetailList(int lostfoundtypeId) {
        List<Found> foundDetailList = foundDao.selectByTypeId(lostfoundtypeId);
        if (foundDetailList == null) {
            return ServerResponse.createServerResponseByFail(ResponseCode.DATA_EMPTY.getCode(), ResponseCode.DATA_EMPTY.getMsg());
        }
        //设置用户名
        for (Found found : foundDetailList) {
            //获取用户id
            Integer userId = found.getUser_id();
            //根据用户id获取用户名
            String userNameId = foundDao.searchUserNameId(userId);
            //设置用户名
            found.setNickname(userNameId);

        }

        return ServerResponse.createServerResponseBySuccess(foundDetailList);
    }

    @Override
    public ServerResponse getUserName(int id) {
        String userName = foundDao.searchUserNameId(id);
        if (userName == null) {
            return ServerResponse.createServerResponseByFail(ResponseCode.DATA_EMPTY.getCode(), ResponseCode.DATA_EMPTY.getMsg());
        }
        return ServerResponse.createServerResponseBySuccess(userName);
    }

    @Override
    public ServerResponse addFound(String foundJson) {
        //清空缓存
        redisService.del(key);//以免请求过多造成数据混淆
        //json字符串转java对象
        Found found = JSON.parseObject(foundJson, Found.class);
        //添加信息并存入redis
        if (foundDao.addFound(found) && redisService.set("found", found)) {
            return ServerResponse.createServerResponseBySuccess(ResponseCode.ADD_FOUND_SUCCESS.getMsg());
        } else {
            return ServerResponse.createServerResponseByFail(ResponseCode.ADD_FOUND_FAIL.getCode(), ResponseCode.ADD_FOUND_FAIL.getMsg());
        }
    }

    @Override
    public ServerResponse getFound() {
        //从redis获取数据
        if (redisService.get("found") != null) {
            Found found = (Found) redisService.get("found");
            return ServerResponse.createServerResponseBySuccess(found, ResponseCode.GET_FOUND_SUCCESS.getMsg());
        }
        return ServerResponse.createServerResponseByFail(ResponseCode.DATA_EMPTY.getCode(), ResponseCode.DATA_EMPTY.getMsg());
    }

    @Override
    public ServerResponse getAllByIdFoundList(int user_id) {
        List<Found> allByIdFoundList = foundDao.getAllByIdFoundList(user_id);
        if (allByIdFoundList.size() == 0) {
            return ServerResponse.createServerResponseBySuccess("还未发布任何信息");
        } else {
            //设置用户名
            for (Found found : allByIdFoundList) {
                //获取用户id
                Integer userId = found.getUser_id();
                //根据用户id获取用户名
                String userNameId = foundDao.searchUserNameId(userId);
                //设置用户名
                found.setNickname(userNameId);
                //设置图片
                String updatePic = Util.updatePic(found.getImg());
                found.setImg(updatePic);
            }
        }
        return ServerResponse.createServerResponseBySuccess(allByIdFoundList, "获取招领信息列表成功");
    }

    @Override
    public ServerResponse updateState(int id, String state, int user_id) {
        if (foundDao.updateState(id, state)) {
            List<Found> allByIdFoundList = foundDao.getAllByIdFoundList(user_id);
            return ServerResponse.createServerResponseBySuccess(allByIdFoundList, "更改状态成功");

        }
        return ServerResponse.createServerResponseBySuccess("更改状态失败");
    }

    @Override
    public ServerResponse showFoundList(int stick) {
        List<Found> founds = foundDao.showFoundList(stick);
        if (founds.size() == 0) {
            return ServerResponse.createServerResponseBySuccess("无置顶信息");
        }
        //设置用户名
        for (Found found : founds) {
            //获取用户id
            Integer userId = found.getUser_id();
            //获取分类id
            Integer lostfoundtypeId = found.getLostfoundtype_id();
            //根据用户id获取用户名
            String userNameId = foundDao.searchUserNameId(userId);
            //设置用户名
            found.setNickname(userNameId);
            //设置分类
            List<Lostfoundtype> lostfoundtypes = lostFoundDao.GetAll();
            for (Lostfoundtype type : lostfoundtypes) {
                if (Objects.equals(type.getId(), lostfoundtypeId)) {
                    found.setLostfoundtype(type);
                }
            }
            //设置图片
            String img = found.getImg();
            String updatePic = Util.updatePic(img);
            found.setImg(updatePic);
        }
        return ServerResponse.createServerResponseBySuccess(founds, "置顶信息");
    }


}
