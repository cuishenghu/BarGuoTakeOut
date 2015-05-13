package zykj.com.barguotakeout.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by ss on 15-4-17.
 */
public abstract class EntityHandler<T> extends HttpErrorHandler{

    private  Class<T> c;

    public EntityHandler(Class<T> clzz){
        this.c=clzz;
    }


    @Override
    public void onRecevieSuccess(JSONObject json) {
        JSONArray jsonArray = json.getJSONArray(UrlContants.jsonData);
        List<T> list=JSONArray.parseArray(jsonArray.toString(), c);
        onReadSuccess(list);
    }

    @Override
    public void onRecevieFailed(String status, JSONObject json) {

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

    }

    public abstract void  onReadSuccess(List<T> list);
}
