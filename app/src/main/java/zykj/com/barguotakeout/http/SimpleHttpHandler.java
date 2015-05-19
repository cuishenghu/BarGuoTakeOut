package zykj.com.barguotakeout.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.activity.MainActivity;

/**
 * Created by ss on 15-4-14.
 */
public abstract class SimpleHttpHandler extends AsyncHttpResponseHandler{

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        try {
            String responseString=new String(responseBody, HTTP.UTF_8);
            JSONObject json = (JSONObject) JSON.parse(responseString);
            onJsonSuccess(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public abstract void onJsonSuccess(JSONObject json);

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
    }
}
