package zykj.com.barguotakeout.Utils;

/**
 * Created by ss on 15-4-14.
 */
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {

    private static SharedPreferenceUtils mUtil;
    private static final String PREFERENCE_NAME="_ZYKJSS";
    private static SharedPreferences mSharedPreference;
    private SharedPreferences.Editor mEditor;

    private static final String USERID="userid";
    private static final String USERNAME="username";
    private static final String PASSWORD="password";
    private static final String ISFIRUSE="isfirst";

    private static final String LONGTITUDE="longtitude";//经度
    private static final String LATITUDE="latitude";//维度

    private static final String ADDRESS="address";

    private SharedPreferenceUtils(Context context){
        mSharedPreference=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor=mSharedPreference.edit();
    }

    /**
     *
     * @param context
     */
    @SuppressWarnings("JavaDoc")
    public static synchronized SharedPreferenceUtils init(Context context){
        if(mUtil==null){
            mUtil=new SharedPreferenceUtils(context);
        }
        return mUtil;
    }


    public  void setUsername(String str){
        mEditor.putString(USERNAME, str);
        mEditor.commit();
    }

    public void setPwd(String str){
        mEditor.putString(PASSWORD, str);
        mEditor.commit();
    }

    public void setUsed(){
        mEditor.putBoolean(ISFIRUSE,false);
        mEditor.commit();
    }

    public String getUsername(){
        return mSharedPreference.getString(USERNAME, null);
    }
    public String getPassword(){
        return mSharedPreference.getString(PASSWORD, null);
    }

    /**
     *
     * @return 第一次使用app时返回true
     */
    public boolean isFirstUse(){
        return mSharedPreference.getBoolean(ISFIRUSE,true);
    }

    public void setLongtitude(double longtitude){
        mEditor.putString(LONGTITUDE,String.valueOf(longtitude));
    }

    public double getLongtitude(){
       String ltt= mSharedPreference.getString(LONGTITUDE,"0");
       return Double.valueOf(ltt);
    }

    public void setLatitude(double latitude){
        mEditor.putString(LATITUDE,String.valueOf(latitude));

    }
    public double getLatitude(){
      String la=  mSharedPreference.getString(LATITUDE,"0");
        return Double.valueOf(la);
    }

    public String getAddress(){
      return  mSharedPreference.getString(ADDRESS,"");
    }

    public void setAddress(String address){
        mEditor.putString(ADDRESS,address);
    }

    public String getUserid(){ return mSharedPreference .getString(USERID,""); }

    public void setUserid(String userid){
        mEditor.putString(USERID,userid);
        mEditor.commit();
    }
}