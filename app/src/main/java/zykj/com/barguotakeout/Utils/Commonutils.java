package zykj.com.barguotakeout.Utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.view.WindowManager;

/**
 * Created by ss on 15-4-14.
 */
public class Commonutils {
    /**
     *
     * 获取当前屏幕宽高 返回point对象11
     * @param ctx
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getDisplayInfo(Context ctx){
        Point info=new Point();
        WindowManager wm=(WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(info);;
        return info;
    }

    /**
     * 检查当前网络连接状态 有网返回true 没网返回false
     * @return
     */
    public static boolean checkNetwork(Context ctx){
        ConnectivityManager mConnectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null)
            return mNetworkInfo.isAvailable();
        return false;
    }

    public static String handlerAddress(String str){
        int index=str.indexOf("省");
        return  str.substring(index+1,str.length());
    };

    /**
     *检测是否有外部内存卡
     * @return boolean
     */
    public static boolean havaExternalStorage(){
        //是否有外部内存卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }

}
