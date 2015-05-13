package zykj.com.barguotakeout.http;


import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;


import zykj.com.barguotakeout.Utils.AppLog;

/**
 * Created by ss on 15-4-14.
 */
public abstract class HttpErrorHandler extends AbstractHttpHandler {
    private static final String TAG="httpResponse";
    @Override
    void onJsonSuccess(JSONObject json) {
       String status= json.getString("status");
       String msg=  json.getString("msg");
        if(TextUtils.isEmpty(status) || !status.equals("1")){
            if(!TextUtils.isEmpty(msg)){
            AppLog.e(TAG,msg);}
            onRecevieFailed(status,json);
        }else{
            onRecevieSuccess(json);
        }
    }


    public abstract void onRecevieSuccess(JSONObject json);

    public abstract void onRecevieFailed(String status,JSONObject json);

}
