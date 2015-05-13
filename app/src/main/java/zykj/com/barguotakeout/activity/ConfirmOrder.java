package zykj.com.barguotakeout.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.Collection;
import java.util.Map;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.fragment.CommonProgressFragment;
import zykj.com.barguotakeout.http.HttpErrorHandler;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.model.Goods;
import zykj.com.barguotakeout.model.OrderPaper;
import zykj.com.barguotakeout.view.OrderList;

/**
 * Created by ss on 15-5-4.确认订单Activity
 */
public class ConfirmOrder extends CommonActivity implements View.OnClickListener {

    private TextView tv_address;
    private TextView tv_mobile;
    private EditText et_msg;
    private OrderPaper paper;
    private String address;
    private String mobile;
    private OrderList list;
    private String msg;

    private int payType=0;
    private Button btn_buy;
    private RadioButton rb_dapfu;
    private RadioButton rb_zhifubao;
    private Integer price;


    public static Intent newIntent(Context ctx,String mobile,String address,Parcelable paper,Integer payType,String msg,Integer price){
        Intent intent=new Intent();
        intent.setClass(ctx,ConfirmOrder.class);
        intent.putExtra("mobile",mobile);
        intent.putExtra("address",address);
        intent.putExtra("paper",paper);
        intent.putExtra("payType",payType);
        intent.putExtra("msg",msg);
        intent.putExtra("price",price);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        Intent intent=getIntent();
        mobile = intent.getStringExtra("mobile");
        address = intent.getStringExtra("address");
        paper = intent.getParcelableExtra("paper");
        payType=intent.getIntExtra("payType", 0);
        msg=intent.getStringExtra("msg");
        price=intent.getIntExtra("price", 0);
        initView();

    }

    private void initView() {
        tv_address = (TextView) findViewById(R.id.tv_confirm_address);
        tv_mobile = (TextView) findViewById(R.id.tv_confirm_mobile);
        et_msg = (EditText) findViewById(R.id.et_confirm_msg);
        list = (OrderList) findViewById(R.id.ol_buy_orderlist);
        btn_buy = (Button) findViewById(R.id.btn_buy_buy);
        rb_dapfu = (RadioButton) findViewById(R.id.rb_pay_daofu);
        rb_zhifubao = (RadioButton) findViewById(R.id.rb_buy_zhifubao);

        if(!TextUtils.isEmpty(mobile)){
            tv_mobile.setText(mobile);
        }
        if(!TextUtils.isEmpty(address)){
            tv_address.setText(address);
        }

        if(!TextUtils.isEmpty(msg)){
            et_msg.setText(msg);
        }

        switch (payType){
            case 0:
                rb_zhifubao.setChecked(true);
                break;
            case 1:
                rb_dapfu.setChecked(true);
                break;
        }

        if(paper!=null){
            list.setMap(paper.getMap());
        }

        btn_buy.setOnClickListener(this);

    }

    public void back(View v){
        finish();
    }

    @Override
    public void onClick(View v) {
        //点击确认订单按钮 提交订单
        switch (v.getId()){
            case R.id.btn_buy_buy:
                RequestParams params=new RequestParams();
                Map<String,Goods> map=paper.getMap();
                Collection<Goods> values = map.values();
                JSONArray array= (JSONArray) JSONArray.toJSON(values);
                params.add("resid",String.valueOf(paper.getResid()));
                if(price==0){
                    //订单总价不能为0
                    return;
                }
                params.add("orderprice",String.valueOf(price));
                params.add("username", Mapplication.getModel().getUsername());
                params.add("address",address);
                params.add("remark",msg);
                params.add("payway",String.valueOf(payType));
                params.add("phonenum",String.valueOf(mobile));
                params.add("goodsdetail",array.toJSONString());
                params.add("realname",Mapplication.getModel().getUsername());
                CommonProgressFragment.getInstance("正在提交订单").show(getSupportFragmentManager(),"progress");
                HttpUtil.updateOrder(new HttpErrorHandler() {
                    @Override
                    public void onRecevieSuccess(JSONObject json) {
                        CommonProgressFragment.disappear();
                        ToastUTil.shortT(ConfirmOrder.this,"已成功提交订单");
                    }

                    @Override
                    public void onRecevieFailed(String status, JSONObject json) {
                        CommonProgressFragment.disappear();
                        ToastUTil.shortT(ConfirmOrder.this,json.get("msg").toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        CommonProgressFragment.disappear();
                        ToastUTil.shortT(ConfirmOrder.this,"网络连接失败,请检查后重试");
                    }
                },params);
                break;
        }
    }
}
