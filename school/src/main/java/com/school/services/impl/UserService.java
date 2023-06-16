package com.school.services.impl;

import com.school.mapper.UserMapper;
import com.school.entity.User;
import com.school.services.interfaces.UserInterface;
import com.school.utils.DateUtil;
import com.school.utils.ServerResponse;
import com.school.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService implements UserInterface {
    @Autowired
    UserMapper userMapper;


    @Override
    public ServerResponse loginRegister(String phone) {
        Integer userid = userMapper.findUserByPhone(phone);
        Date time = DateUtil.getTime();//获取时间
        Integer bigDecimal = 1000;
        String nickname = Util.NickNameRandom();//随机获取昵称
        String photo;
        try {
            photo = Util.ImageSearch("头像");//随机获取头像
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User user = new User(nickname, phone, photo, "男", bigDecimal, 100, time);
        if (userid == null) {
            //注册，第一次用户信息为系统默认，后续用户可以自行修改
            //注册成功后返回user对象
            userMapper.register(phone, user);
        } else {
            //已经有账户,返回userInfo
            User userInfo = userMapper.userInfo(userid);
            //设置头像
            String pic = Util.updatePic(userInfo.getPhoto());
            userInfo.setPhoto(pic);
            return ServerResponse.createServerResponseBySuccess(userInfo, "已有账户，立即登录");
        }
        return ServerResponse.createServerResponseBySuccess(user, "注册成功");
    }

    @Override
    public ServerResponse loginByPwd(String phone, String pwd) {
        //判断是否为新账户登录
        //1新账户，则自动注册 2已有账户，立即登录
        Integer userid = userMapper.findUserByPhone(phone);
        Date time = DateUtil.getTime();//获取时间
        Integer bigDecimal = 1000;
        String nickname = Util.NickNameRandom();//随机获取昵称
        String photo;
        try {
            photo = Util.ImageSearch("头像");//随机获取头像
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User user = new User(nickname, phone, photo, "男", bigDecimal, 100, time);
        if (userid == null) {
            //注册，第一次用户信息为系统默认，后续用户可以自行修改
            //注册成功后返回user对象
            user.setPassword(pwd);//设置密码
            userMapper.register(phone, user);
            return ServerResponse.createServerResponseBySuccess(user, "注册成功,立即登录");
        } else {
            //已经有账户,立即登录,返回userInfo
            Integer login = userMapper.login(phone, pwd);
            if (login == null) {
                //登录失败
                return ServerResponse.createServerResponseBySuccess("登录失败,请检查手机号密码是否正确");
            } else {
                User userInfo = userMapper.userInfo(login);
                //设置头像
                String pic = Util.updatePic(userInfo.getPhoto());
                userInfo.setPhoto(pic);
                return ServerResponse.createServerResponseBySuccess(userInfo, "登录成功");
            }

        }
    }

    @Override
    public ServerResponse resetPwd(String phone, String newPwd) {
        //判断新密码与旧密码是否相同如果相同则提示用户，不同则修改密码
        //获取用户信息
        Integer userId = userMapper.findUserByPhone(phone);
        if (userId == null) {
            return ServerResponse.createServerResponseBySuccess("用户不存在");
        }
        User userInfo = userMapper.userInfo(userId);
        if(userInfo.getPassword()==null){
            //新用户的密码重置
            if (userMapper.resetPwd(phone, newPwd)) {
                userInfo = userMapper.userInfo(userId);
                //设置头像
                String pic = Util.updatePic(userInfo.getPhoto());
                userInfo.setPhoto(pic);
                //重置成功
                return ServerResponse.createServerResponseBySuccess(userInfo, "重置成功");
            }
        }else {
            //不是新新用户的密码重置
            if (userInfo.getPassword().equals(newPwd)) {
                return ServerResponse.createServerResponseBySuccess("新密码不能与旧密码相同");
            } else {
                if (userMapper.resetPwd(phone, newPwd)) {
                    userInfo = userMapper.userInfo(userId);
                    //设置头像
                    String pic = Util.updatePic(userInfo.getPhoto());
                    userInfo.setPhoto(pic);
                    //重置成功
                    return ServerResponse.createServerResponseBySuccess(userInfo, "重置成功");
                }
            }
        }


        return ServerResponse.createServerResponseBySuccess("重置失败");
    }

    @Override
    public ServerResponse updatePhoto(String photo, int id) {
        if (userMapper.updatePhoto(photo, id)) {
            User userInfo = userMapper.userInfo(id);//获取用户信息
            //设置头像
            String pic = Util.updatePic(userInfo.getPhoto());
            userInfo.setPhoto(pic);
            return ServerResponse.createServerResponseBySuccess(userInfo, "修改头像成功!");
        }
        return ServerResponse.createServerResponseBySuccess("修改失败!");

    }

    @Override
    public ServerResponse updateAc(String nickname, String sex,int id) {
        if (userMapper.updateAc(nickname,sex,id)) {
            User userInfo = userMapper.userInfo(id);
            //设置头像
            String pic = Util.updatePic(userInfo.getPhoto());
            userInfo.setPhoto(pic);
            return ServerResponse.createServerResponseBySuccess(userInfo,"修改资料成功!");
        }
        return ServerResponse.createServerResponseBySuccess("修改失败!");
    }
}
