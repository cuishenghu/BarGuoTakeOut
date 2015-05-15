package zykj.com.barguotakeout.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.Collection;
import java.util.Map;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.fragment.CommonProgressFragment;
import zykj.com.barguotakeout.http.HttpErrorHandler;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.model.Goods;
import zykj.com.barguotakeout.model.OrderPaper;
import zykj.com.barguotakeout.view.OrderList;

/**
 * Created by ss on 15-5-4.
 */
public class BuyActivity extends CommonActivity implements View.OnClickListener {

    public static final String DATA_KEY="orderpaper";
    private OrderPaper paper;
    private EditText et_mobile;
    private EditText et_address;
    private EditText et_message;
    private Button btn_buy;
    private RadioButton rb_zhifubao;
    private RadioButton rb_daofu;

    private int payType=0;// 支付方式 1 为货到付款 0 为在线支付
    private TextView tv_total;
    private ProgressBar pb_load;
    private Integer  price;
    private EditText viewById;

    public static Intent newIntent(Context context,Parcelable parcelable){
        Intent intent=new Intent();
        intent.setClass(context,BuyActivity.class);
        intent.putExtra(DATA_KEY,parcelable);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        paper = getIntent().getParcelableExtra(DATA_KEY);
        initView();
        getTotalPrice();
    }

    private void initView() {
        et_mobile = (EditText) findViewById(R.id.et_buy_mobile);//手机号
        et_address = (EditText) findViewById(R.id.et_buy_address);//送餐地址
        et_message = (EditText) findViewById(R.id.et_buy_message);//留言
        btn_buy = (Button) findViewById(R.id.btn_buy_buy);//提交订单
        rb_zhifubao = (RadioButton) findViewById(R.id.rb_buy_zhifubao);///支付宝支付
        rb_daofu = (RadioButton) findViewById(R.id.rb_pay_daofu);//餐到付款
        tv_total = (TextView) findViewById(R.id.tv_buy_total);//合计
        pb_load = (ProgressBar) findViewById(R.id.pb_buy_totalloading);//合计加载
        rb_daofu.setOnClickListener(this);
        rb_zhifubao.setOnClickListener(this);

        OrderList ol_order= (OrderList) findViewById(R.id.ol_buy_orderlist);//订单列表
        btn_buy.setOnClickListener(this);
        if(paper!=null){
            ol_order.setMap(paper.getMap());
        }
        et_mobile.setText(Mapplication.getModel().getUsername());
        et_address.setText(Mapplication.getModel().getAddress());
    }

    public void back(View v){
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_buy_buy:
                final String mobile=et_mobile.getText().toString().trim();
                final String address=et_address.getText().toString().trim();
                final String msg=et_message.getText().toString().trim();

                if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(address)){
                    ToastUTil.shortT(BuyActivity.this,"清先输入手机号和地址");
                    return;
                }
                RequestParams params=new RequestParams();
                Map<String,Goods> map=paper.getMap();
                Collection<Goods> values = map.values();
                JSONArray array= (JSONArray) JSONArray.toJSON(values);
                params.add("resid",String.valueOf(paper.getResid()));
                params.add("orderprice",String.valueOf(price));
                params.add("userid", Mapplication.getModel().getUserid());
                params.add("address",address);
                params.add("remark",msg);
                params.add("payway",String.valueOf(payType));
                params.add("phonenum",String.valueOf(mobile));
                params.add("goodsdetail",array.toJSONString());
                params.add("realname",Mapplication.getModel().getUsername());//获取真实姓名
                params.add("username",Mapplication.getModel().getUsername());

                CommonProgressFragment.getInstance("正在提交订单").show(getSupportFragmentManager(),"progress");
                HttpUtil.updateOrder(new HttpErrorHandler() {
                    @Override
                    public void onRecevieSuccess(JSONObject json) {
                        //ToastUTil.shortT(BuyActivity.this,"已成功提交订单");
                        JSONObject jsonObject = json.getJSONObject("data");
                        String ordernum = jsonObject.getString("ordernum");
                        CommonProgressFragment.disappear();
                        startActivity(ConfirmOrder.newIntent(BuyActivity.this, mobile, address, paper, payType, msg, price, ordernum));
                    }

                    @Override
                    public void onRecevieFailed(String status, JSONObject json) {
                        CommonProgressFragment.disappear();
                        ToastUTil.shortT(BuyActivity.this, json.get("msg").toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        CommonProgressFragment.disappear();
                        ToastUTil.shortT(BuyActivity.this, "网络连接失败,请检查后重试");
                    }
                }, params);
                break;
            case R.id.rb_buy_zhifubao:
                //在线交易
                payType=0;
                break;
            case R.id.rb_pay_daofu:
                //餐到付款
                payType=1;
                break;
        }
    }

    /**
     * 获取总价
     */
    public void getTotalPrice(){
        Map<String,Goods> map=paper.getMap();
        Collection<Goods> values = map.values();
        JSONArray array= (JSONArray) JSONArray.toJSON(values);
        //AppLog.i("json",array.toJSONString());
        RequestParams params =new RequestParams();
        params.add("resid",String.valueOf(paper.getResid()));
        params.add("goodsdetail",array.toJSONString());
        HttpUtil.getTotalPrice(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                JSONObject data=json.getJSONObject("data");
                price=data.getInteger("totalprice");
                tv_total.setText(String.format("￥%d",price));
                pb_load.setVisibility(View.GONE);
                btn_buy.setEnabled(true);
            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {
                ToastUTil.shortT(BuyActivity.this,"获取总价失败,请重新下单");
                pb_load.setVisibility(View.GONE);
                btn_buy.setEnabled(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ToastUTil.shortT(BuyActivity.this,"网络连接失败,请检查网络连接");
                pb_load.setVisibility(View.GONE);
                btn_buy.setEnabled(false);
            }
        },params);
    }

}
