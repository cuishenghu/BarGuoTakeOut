package zykj.com.barguotakeout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.adapter.MyAddressAdapter;
import zykj.com.barguotakeout.http.HttpUtil;

public class WoDeShouHuoDiZhiActivity extends CommonActivity implements View.OnClickListener {
    ImageView im_wdshdz_back;
    TextView tv_wdshdz_tianjia;
    ListView lv_wdshdz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_shou_huo_di_zhi);
        initView();
//        requestData();
    }

    private void initView() {
        im_wdshdz_back = (ImageView) findViewById(R.id.im_wdshdz_back);
        tv_wdshdz_tianjia = (TextView) findViewById(R.id.tv_wdshdz_tianjia);
        lv_wdshdz = (ListView) findViewById(R.id.lv_wdshdz);
        im_wdshdz_back.setOnClickListener(this);
        tv_wdshdz_tianjia.setOnClickListener(this);
    }

    private void requestData(){
        if(TextUtils.isEmpty(Mapplication.getModel().getUsername())){
            //还没有登录
        }else {
            //已经登陆
            ToastUTil.shortT(WoDeShouHuoDiZhiActivity.this,"11111111");
            final RequestParams params=new RequestParams();
            params.add("page","1");
            params.add("num","5");
            params.add("userid","23");
              HttpUtil.getMyAddress(new AsyncHttpResponseHandler() {
                  @Override
                  public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                      //请求成功
                      try {
                          ToastUTil.shortT(WoDeShouHuoDiZhiActivity.this, "222222222");
                          String address = new String(responseBody, HTTP.UTF_8);
                          JSONObject jobe = JSON.parseObject(address);
                          AppLog.e("--------------", jobe.toJSONString());
                          JSONArray jarry = jobe.getJSONArray("data");
                          List<String> list = new ArrayList<String>();
                          list = JSONArray.parseArray(jarry.toString(), String.class);
                          MyAddressAdapter myadapter = new MyAddressAdapter(WoDeShouHuoDiZhiActivity.this, list);
                          lv_wdshdz.setAdapter(myadapter);
                      } catch (UnsupportedEncodingException e) {
                          e.printStackTrace();
                      }
                  }

                  @Override
                  public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                      ToastUTil.shortT(WoDeShouHuoDiZhiActivity.this, "3333333");
                  }
              }, params);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_wdshdz_back:
                this.finish();
                break;
            case R.id.tv_wdshdz_tianjia:
                //添加收货地址
                Intent intent = new Intent(WoDeShouHuoDiZhiActivity.this,AddAddressActivity.class);
                startActivity(intent);
                break;
        }
    }
}
