package com.school.entity;

public class FormDataDTO {
    //发送邮件表单传输实体类
    private String name;//自己的邮箱
    private String password;//邮箱授权码
    private String email;//目标邮箱
    private String subject;//主题
    private String message;//消息

    public FormDataDTO() {
    }

    public FormDataDTO(String name, String password, String email, String subject, String message) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

