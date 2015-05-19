package zykj.com.barguotakeout.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pingplusplus.android.PaymentActivity;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
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
 * Created by ss on 15-5-4.确认订单Activity
 */
public class ConfirmOrder extends CommonActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_PAYMENT = 1;
    private static final String CHANNEL_WECHAT = "wechat";
    private static final String CHANNEL_ALIPAY = "alipay";

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
    private RadioButton rb_wechat;
    private RadioButton alipay;
    private Integer price;
    private String ordernum;
    private String orderdetail;


    public static Intent newIntent(Context ctx,String mobile,String address,Parcelable paper,Integer payType,String msg,Integer price,String ordernum){
        Intent intent=new Intent();
        intent.setClass(ctx, ConfirmOrder.class);
        intent.putExtra("mobile",mobile);
        intent.putExtra("address",address);
        intent.putExtra("paper",paper);
        intent.putExtra("payType",payType);
        intent.putExtra("msg",msg);
        intent.putExtra("price",price);
        intent.putExtra("ordernum",ordernum);
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
        ordernum=intent.getStringExtra("ordernum");
        orderdetail=intent.getStringExtra("orderdetail");
        initView();

    }

    private void initView() {
        tv_address = (TextView) findViewById(R.id.tv_confirm_address);//送货电话
        tv_mobile = (TextView) findViewById(R.id.tv_confirm_mobile);//送货地址
        et_msg = (EditText) findViewById(R.id.et_confirm_msg);//留言
        list = (OrderList) findViewById(R.id.ol_buy_orderlist);//订单列表
        btn_buy = (Button) findViewById(R.id.btn_buy_buy);//提交订单
        rb_wechat = (RadioButton) findViewById(R.id.rb_buy_wechat);//餐到付款
        alipay = (RadioButton) findViewById(R.id.rb_buy_zhifubao);//支付宝付款

        if(!TextUtils.isEmpty(mobile)){
            tv_mobile.setText(mobile);
        }
        if(!TextUtils.isEmpty(address)){
            tv_address.setText(address);
        }

        if(!TextUtils.isEmpty(msg)){
            et_msg.setText(msg);
        }

        alipay.setChecked(true);

        if(paper!=null){
            list.setMap(paper.getMap());
        }else{
            JSONArray jsonArray = (JSONArray)JSON.parse(orderdetail);
            list.setMap(jsonArray);
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
                String channel = alipay.isChecked()?CHANNEL_ALIPAY:CHANNEL_WECHAT;
                RequestParams params=new RequestParams();
                params.add("channel",channel);
                params.add("amount",String.valueOf(price));
                params.add("ordernum",ordernum);
                params.add("subject","Foods");
                params.add("body", "this is a pay for online");
                HttpUtil.pay(mAsyncHttpResponseHandler, params);
                break;
        }
    }

    private AsyncHttpResponseHandler mAsyncHttpResponseHandler = new HttpErrorHandler() {
        @Override
        public void onRecevieSuccess(JSONObject json) {
            new PaymentTask().execute(json);
        }

        @Override
        public void onRecevieFailed(String status, JSONObject json) {
            //服务器没写status,所以该请求成功!
            new PaymentTask().execute(json);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(ConfirmOrder.this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    };

    class PaymentTask extends AsyncTask<JSONObject, Void, String> {

        @Override
        protected void onPreExecute() {
            btn_buy.setOnClickListener(null);
        }

        @Override
        protected String doInBackground(JSONObject... pr) {
            JSONObject json = pr[0];
            return json.toString();
        }

        @Override
        protected void onPostExecute(String data) {
            AppLog.i("charge", data);
            Intent intent = new Intent();
            String packageName = getPackageName();
            ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
            intent.setComponent(componentName);
            intent.putExtra(PaymentActivity.EXTRA_CHARGE, data);
            startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             *
             * 如果是银联渠道返回 invalid，调用 UPPayAssistEx.installUPPayPlugin(this); 安装银联安全支付控件。
             */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                Toast.makeText(this, result.equals("success")?"支付成功":"支付失败", Toast.LENGTH_SHORT).show();
                if(result.equals("success")||result.equals("fail")){
                    Intent intent = new Intent(ConfirmOrder.this,OrderStatusActivit.class);
                    intent.putExtra(OrderStatusActivit.CODE,ordernum);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "User canceled", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "An invalid Credential was submitted.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}