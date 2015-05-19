package zykj.com.barguotakeout.model;

import java.io.Serializable;

/**
 * Created by ss on 15-5-5.
 */
public class User implements Serializable{
    private String userid;
    private String username;
    private String nickname;
    private String avatar;
    private String phonenum;
    private String email;
    private String birthday;
    private String gender;
    private String baguobi;

    public String getBaguobi() {
        return baguobi;
    }

    public void setBaguobi(String baguobi) {
        this.baguobi = baguobi;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
