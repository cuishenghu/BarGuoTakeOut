package zykj.com.barguotakeout.Utils;

import android.content.Context;

/**
 * Created by ss on 15-4-20.
 */
public class AppHelperImpl  {

    private  String user="";
    private  String pwd="";

    private  String address="";

    private  double latitude=0.0f;
    private  double longitude=0.0f;

    private SharedPreferenceUtils utils;

    public  AppHelperImpl(Context ctx){
        utils = SharedPreferenceUtils.init(ctx);

        if(utils.getUsername()!=null){
            this.user = utils.getUsername();
        }

        if(utils.getPassword() != null){
            this.pwd= utils.getPassword();
        }

    }
    public void setUser(String name) {
        utils.setUsername(name);
        this.user=name;
    }

    public String getUsername() {
        if(user== null){
            user=utils.getUsername();
        }
        return user;
    }

    public String getPassword() {
        if(pwd==null){
            pwd=utils.getPassword();
        }
        return pwd;
    }

    public void setlatitude(double latitude) {
        utils.setLatitude(latitude);
        this.latitude=latitude;
    }

    public double getlatitude() {
        if(latitude == 0){
            latitude = utils.getLatitude();
        }
        return latitude;
    }

    public void setLongitude(double longitude){
        utils.setLongtitude(longitude);
        this.longitude=longitude;
    }

    public double getLongitude(){
        if(longitude==0){
            longitude=utils.getLongtitude();
        }
        return longitude;
    }

    public void setAddress(String address){
        utils.setAddress(address);
        this.address=address;
    }
}
