package zykj.com.barguotakeout.Utils;

import android.util.Log;

import zykj.com.barguotakeout.BuildConfig;

/**
 * Created by ss on 15-4-14.
 */
public class AppLog {
    private static final String appTag="巴国外卖:";
    public static void e(String tag,String msg){
        if(BuildConfig.DEBUG){
            Log.e(appTag+tag,msg);
        }
    }
    public static void i(String tag,String msg){
        if(BuildConfig.DEBUG){
            Log.i(appTag+tag,msg);
        }
    }
    public static void w(String tag,String msg){
        if(BuildConfig.DEBUG){
            Log.w(appTag+tag,msg);
        }
    }

}
