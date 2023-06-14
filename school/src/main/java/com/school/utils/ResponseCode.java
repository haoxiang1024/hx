package com.school.utils;

/*
用于放状态码跟状态信息
 */
public enum ResponseCode {

    //状态信息放在;前面
    DATA_EMPTY(1, "数据为空"),
    PHONE_NOT_EMPTY(2, "电话不能为空"),
    ADD_LOST_SUCCESS(3, "添加丢失物品信息成功"),
    ADD_LOST_FAIL(4, "添加丢失物品信息失败"),
    GET_LOST_SUCCESS(5, "获取丢失物品信息成功"),
    ADD_ALL_TYPE(6, "存入redis丢失物品分类成功"),
    ADD_ALL_TYPE_FAIL(7, "存入redis丢失物品分类失败"),
    GET_ALL_TYPE(8, "获取redis丢失物品分类成功"),
    GET_DATA_SUCCESS(9, "获取数据成功"),
    GET_DATA_FAIL(10, "获取数据失败"),
    ADD_FOUND_SUCCESS(11, "添加招领物品信息成功"),
    ADD_FOUND_FAIL(12, "添加招领物品信息失败"),
    GET_FOUND_SUCCESS(13, "获取招领物品信息成功"),
    GET_FOUND_FAIL(14, "获取招领物品信息失败");


    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}

