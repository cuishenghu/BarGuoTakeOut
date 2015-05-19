package zykj.com.barguotakeout;

import android.content.Context;
import android.text.TextUtils;

import zykj.com.barguotakeout.Utils.SharedPreferenceUtils;
import zykj.com.barguotakeout.model.User;

/**
 * Created by ss on 15-4-20.
 */
public class AppModel {

    private String username;
    private String pwd;
    private String userid;

    private String address;
    private String baguobi;

    private double latitude=0.0f;
    private double longitude=0.0f;
    private static SharedPreferenceUtils utils;

    private String citycode;

    private AppModel(){};


    public static AppModel init(Context context){
        AppModel model =new AppModel();
        utils = SharedPreferenceUtils.init(context);

        if(utils.getUsername()!=null){
            model.username = utils.getUsername();
        }

        if(utils.getPassword() != null){
            model.pwd= utils.getPassword();
        }

        if(!TextUtils.isEmpty(utils.getAddress())){
            model.address = utils.getAddress();
        }
        if(utils.getLatitude() !=0){
            model.latitude= utils.getLatitude();
        }

        if(utils.getLongtitude() !=0){
            model.longitude= utils.getLongtitude();
        }

        if(!TextUtils.isEmpty(utils.getUserid())){
            model.userid = utils.getUserid();
        }

        if(!TextUtils.isEmpty(utils.getBaguobi())){
            model.baguobi = utils.getBaguobi();
        }

        return model;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        utils.setUsername(username);
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        utils.setPwd(pwd);
    }

    public String getUserid() { return userid; }

    public void setUserid(String userid){
        this.userid = userid;
        utils.setUserid(userid);
    }

    public String getBaguobi() { return baguobi; }

    public void setBaguobi(String baguobi){
        this.baguobi = baguobi;
        utils.setBaguobi(baguobi);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public static SharedPreferenceUtils getUtils() {
        return utils;
    }

    public static void setUtils(SharedPreferenceUtils utils) {
        AppModel.utils = utils;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }
}
