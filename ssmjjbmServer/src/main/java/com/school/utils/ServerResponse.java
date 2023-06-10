package com.school.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * 封装前端返回的统一实体类
 *
 * @param <T>
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)//只包含非空字段
public class ServerResponse<T> {
    private int status; //状态 0；接口调用成功,1调用失败
    private T data; //泛型T，当status=0，将返回的数据封装到data中
    private String msg; //提示信息

    private ServerResponse() {
    }

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static ServerResponse createServerResponseBySuccess() {
        return new ServerResponse(0);
    }

    /**
     * <T>后跟方法名表示泛型方法
     */
    public static <T> ServerResponse createServerResponseBySuccess(T data) {
        return new ServerResponse(0, data);
    }

    public static <T> ServerResponse createServerResponseBySuccess(String msg) {
        return new ServerResponse(0, msg);
    }

    public static <T> ServerResponse createServerResponseBySuccess(T data, String msg) {
        return new ServerResponse(0, data, msg);
    }

    public static ServerResponse createServerResponseByFail(int status) {
        return new ServerResponse(status);
    }

    public static ServerResponse createServerResponseByFail(int status, String msg) {
        return new ServerResponse(status, null, msg);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == 0;
    }

}
