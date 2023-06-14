package com.school.mapper;

import com.school.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserMapper {
    /**
     * 判断手机号是否注册
     * 1 手机号未注册，注册一个账户
     * 2 手机号已经注册，返回user id
     */
    Integer findUserByPhone(@Param("phone") String phone);

    //通过手机号注册用户
    void register(@Param("phone") String phone, @Param("user") User user);

    //根据id获取用户信息
    User userInfo(@Param("id") int id);

    //手机号密码登录
    Integer login(@Param("phone") String phone, @Param("password") String pwd);

    //重置密码
    boolean resetPwd(@Param("phone")String phone,@Param("password")String pwd);

    //修改用户头像
    boolean updatePhoto(@Param("photo") String photo,@Param("id")int id);

    //修改用户资料
    boolean updateAc(@Param("nickname")String nickname,@Param("sex")String sex,@Param("id")int id);

//获取所有用户信息
    List<User>getalll();

}
