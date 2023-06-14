package com.school.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class Lost implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String img;
    /**
     *
     */
    private Date pub_date;
    /**
     *
     */
    private String content;
    /**
     *
     */
    private String place;
    /**
     *
     */
    private String phone;
    /**
     *
     */
    private String state;
    /**
     *
     */
    private Integer stick;
    /**
     *
     */
    private Integer lostfoundtype_id;
    /**
     *
     */

    private Lostfoundtype lostfoundtype;

    private Integer user_id;
    private String nickname;

    public Lost() {
    }

    public Lost(String title, String img, Date pub_date, String content, String place, String phone, String state, Integer stick, Integer lostfoundtype_id, Integer user_id, String nickname) {
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

    public Lostfoundtype getLostfoundtype() {
        return lostfoundtype;
    }

    public void setLostfoundtype(Lostfoundtype lostfoundtype) {
        this.lostfoundtype = lostfoundtype;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     */
    public String getImg() {
        return img;
    }

    /**
     *
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     *
     */
    public Date getPubDate() {
        return pub_date;
    }

    /**
     *
     */
    public void setPubDate(Date pubDate) {
        this.pub_date = pubDate;
    }

    /**
     *
     */
    public String getContent() {
        return content;
    }

    /**
     *
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     */
    public String getPlace() {
        return place;
    }

    /**
     *
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     *
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     */
    public String getState() {
        return state;
    }

    /**
     *
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     */
    public Integer getStick() {
        return stick;
    }

    /**
     *
     */
    public void setStick(Integer stick) {
        this.stick = stick;
    }

    /**
     *
     */
    public Integer getLostfoundtypeId() {
        return lostfoundtype_id;
    }

    /**
     *
     */
    public void setLostfoundtypeId(Integer lostfoundtype_id) {
        this.lostfoundtype_id = lostfoundtype_id;
    }

    /**
     *
     */
    public Integer getUserId() {
        return user_id;
    }

    /**
     *
     */
    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lost lost = (Lost) o;
        return Objects.equals(id, lost.id) && Objects.equals(title, lost.title) && Objects.equals(img, lost.img) && Objects.equals(pub_date, lost.pub_date) && Objects.equals(content, lost.content) && Objects.equals(place, lost.place) && Objects.equals(phone, lost.phone) && Objects.equals(state, lost.state) && Objects.equals(stick, lost.stick) && Objects.equals(lostfoundtype_id, lost.lostfoundtype_id) && Objects.equals(user_id, lost.user_id) && Objects.equals(nickname, lost.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, img, pub_date, content, place, phone, state, stick, lostfoundtype_id, user_id, nickname);
    }

    @Override
    public String toString() {
        return "Lost{" +
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
                ", lostfoundtype=" + lostfoundtype +
                ", user_id=" + user_id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}