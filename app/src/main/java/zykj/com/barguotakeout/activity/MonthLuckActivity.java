package zykj.com.barguotakeout.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.adapter.JokeAdapter;
import zykj.com.barguotakeout.http.EntityHandler;
import zykj.com.barguotakeout.http.HttpErrorHandler;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.model.JokeEntity;

/**
 * Created by ss on 15-4-17.
 */
public class MonthLuckActivity extends CommonActivity implements View.OnClickListener{

    private TextView luckText;
    private Button luckSubmit;
    private String userid;

    private AsyncHttpResponseHandler myHttpResponseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTag(MonthLuckActivity.class.getSimpleName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck);
        initView();
    }

    private void initView() {
        luckText = (TextView) findViewById(R.id.luck_tv_content);
        luckSubmit = (Button) findViewById(R.id.btn_luck_submit);

        userid = Mapplication.getModel().getUserid();
        if(TextUtils.isEmpty(userid))
            luckSubmit.setBackgroundResource(R.drawable.gray_angle_shape);
        luckSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(TextUtils.isEmpty(userid)){
            //还没有登录 弹出对话框立即登录
            showToastText(MonthLuckActivity.this, "提醒", "请先登录!", "确定");
            return;
        }else{
            final RequestParams params=new RequestParams();
            params.add("userid", userid);
            HttpUtil.drawlottery(new HttpErrorHandler() {
                @Override
                public void onRecevieSuccess(JSONObject json) {
                    showToastText(MonthLuckActivity.this, "抽奖详情", "你的抽奖号码为123456，抽奖结果将于5月31日公布，请注意查收。", "确定");
                }

                @Override
                public void onRecevieFailed(String status, JSONObject json) {
                    showToastText(MonthLuckActivity.this, "提醒", json.getString("msg"), "确定");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            }, params);
        }
    }

    public void back(View v){
        finish();
    }

    private void showToastText(Context context, String title, String msg, String button){
        ToastUTil.alertDialogBuilder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(button,null)
                .show();
    }

}
