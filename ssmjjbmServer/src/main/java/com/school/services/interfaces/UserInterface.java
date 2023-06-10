package com.school.services.interfaces;

import com.school.utils.ServerResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserInterface {
    //登录注册
    ServerResponse loginRegister(String phone);
    //密码登录
    ServerResponse loginByPwd(String phone,String pwd);
    //忘记密码
    ServerResponse resetPwd(String phone,String newPwd );
    //修改头像
    ServerResponse updatePhoto(String photo,int id);
    //用户资料修改
    ServerResponse updateAc(String nickname,String sex,int id);
}
