package com.school.entity;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @TableName found
 */
public class Found implements Serializable {

    /**
     *
     */
    @NotNull(message = "[]不能为空")
    @ApiModelProperty("")
    private Integer id;
    /**
     *
     */
    @Size(max = 50, message = "编码长度不能超过50")
    @ApiModelProperty("")
    @Length(max = 50, message = "编码长度不能超过50")
    private String title;
    /**
     *
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @ApiModelProperty("")
    @Length(max = 100, message = "编码长度不能超过100")
    private String img;
    /**
     *
     */
    @ApiModelProperty("")
    private Date pub_date;
    /**
     *
     */
    @Size(max = 15000, message = "编码长度不能超过15000")
    @ApiModelProperty("")
    @Length(max = 15000, message = "编码长度不能超过15,000")
    private String content;
    /**
     *
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @ApiModelProperty("")
    @Length(max = 100, message = "编码长度不能超过100")
    private String place;
    /**
     *
     */
    @ApiModelProperty("")
    private String phone;
    /**
     *
     */
    @ApiModelProperty("")
    private String state;
    /**
     *
     */
    @ApiModelProperty("")
    private Integer stick;
    /**
     *
     */
    @ApiModelProperty("")
    private Integer lostfoundtype_id;
    /**
     *
     */
    @ApiModelProperty("")
    private Integer user_id;
    private String nickname;
    private Lostfoundtype lostfoundtype;

    public Lostfoundtype getLostfoundtype() {
        return lostfoundtype;
    }

    public void setLostfoundtype(Lostfoundtype lostfoundtype) {
        this.lostfoundtype = lostfoundtype;
    }

    public Found() {
    }

    public Found(String title, String img, Date pub_date, String content, String place, String phone, String state, Integer stick, Integer lostfoundtype_id, Integer user_id, String nickname) {
        this.title = title;
        this.img = img;
        this.pub_date = pub_date;
        this.content = content;
        this.place = place;
        this.phone = phone;
        this.state = state;
        this.stick = stick;
        this.lostfoundtype_id = lostfoundtype_id;
        this.user_id = user_id;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getPub_date() {
        return pub_date;
    }

    public void setPub_date(Date pub_date) {
        this.pub_date = pub_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getStick() {
        return stick;
    }

    public void setStick(Integer stick) {
        this.stick = stick;
    }

    public Integer getLostfoundtype_id() {
        return lostfoundtype_id;
    }

    public void setLostfoundtype_id(Integer lostfoundtype_id) {
        this.lostfoundtype_id = lostfoundtype_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Found found = (Found) o;
        return Objects.equals(id, found.id) && Objects.equals(title, found.title) && Objects.equals(img, found.img) && Objects.equals(pub_date, found.pub_date) && Objects.equals(content, found.content) && Objects.equals(place, found.place) && Objects.equals(phone, found.phone) && Objects.equals(state, found.state) && Objects.equals(stick, found.stick) && Objects.equals(lostfoundtype_id, found.lostfoundtype_id) && Objects.equals(user_id, found.user_id) && Objects.equals(nickname, found.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, img, pub_date, content, place, phone, state, stick, lostfoundtype_id, user_id, nickname);
    }

    @Override
    public String toString() {
        return "Found{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", pub_date=" + pub_date +
                ", content='" + content + '\'' +
                ", place='" + place + '\'' +
                ", phone='" + phone + '\'' +
                ", state='" + state + '\'' +
                ", stick=" + stick +
                ", lostfoundtype_id=" + lostfoundtype_id +
                ", user_id=" + user_id +
                ", nickname='" + nickname + '\'' +
                ", lostfoundtype=" + lostfoundtype +
                '}';
    }
}
