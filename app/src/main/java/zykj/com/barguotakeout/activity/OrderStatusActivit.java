package zykj.com.barguotakeout.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.http.HttpErrorHandler;
import zykj.com.barguotakeout.http.HttpUtil;

/**
 * Created by ss on 15-5-7.
 */
public class OrderStatusActivit extends CommonActivity{

    public static final String CODE="ordernum";
    private String ordernum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        initData();

    }

    private void initData() {
        ordernum = getIntent().getStringExtra(CODE);
        if(ordernum!=null){
            requestData();
        }
    }

    private void requestData() {
        RequestParams params=new RequestParams();
        params.add("ordernum",ordernum);
        HttpUtil.getOrderStatus(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {

            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        },params);
    }

    public void back(View v){
        finish();
    }

}
