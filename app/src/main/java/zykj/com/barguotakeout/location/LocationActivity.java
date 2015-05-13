package zykj.com.barguotakeout.location;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.activity.CommonActivity;

/**
 * Created by ss on 15-4-15.
 */
public class LocationActivity extends CommonActivity{
    private LocationManagerProxy mProxy;
    private static final String TAG="LOCATION ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLocationManager();
    }


    /***
     * 初始化 定位api
     */
    private void initLocationManager() {
       AppLog.i(TAG,"location manager init");
       mProxy= LocationManagerProxy.getInstance(this);
       mProxy.setGpsEnable(false);
    }

    /**
     * 发起定位请求
     */
    public void requestLocation(AMapLocationListener mapListener){
        AppLog.i(TAG,"request location data");
        if(mProxy==null){
          initLocationManager();
         }

        mProxy.requestLocationData(LocationProviderProxy.AMapNetwork,-1,15,mapListener);
    }


    private LocationManagerProxy getLocationManager() throws Exception {
        if(mProxy!=null){
            return mProxy;
        }
        else{
            throw new Exception("location manager not init");
        }
    }

    /**
     * 停止定位 在activity pause 时取消定位
     * @param listener
     */
    public void destoryLocationManager(AMapLocationListener listener){
        AppLog.i(TAG,"destory location manager");
        if(mProxy!=null){
            mProxy.removeUpdates(listener);
            mProxy.destroy();
            mProxy=null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
