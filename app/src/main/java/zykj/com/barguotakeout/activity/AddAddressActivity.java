package zykj.com.barguotakeout.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.w3c.dom.Text;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.adapter.MyOderAdapter;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.model.Order;

/**
 * lss 5/18 新增收货地址（编辑收货地址）
 */
public class AddAddressActivity extends CommonActivity implements View.OnClickListener {
    ImageView im_addaddress_back;
    TextView tv_addtitle;
    EditText tv_shouhuoren;
    EditText tv_shoujihao;
    EditText tv_youbian;
    EditText tv_szdizhi;
    EditText tv_xxdizhi;
    private RequestParams params;
    RelativeLayout rl_xzshdz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
    }

    void initView(){
        im_addaddress_back = (ImageView)findViewById(R.id.im_addaddress_back);
        tv_addtitle = (TextView)findViewById(R.id.tv_addtitle);
        tv_shouhuoren = (EditText)findViewById(R.id.tv_shouhuoren);
        tv_shoujihao = (EditText)findViewById(R.id.tv_shoujihao);
        tv_youbian = (EditText)findViewById(R.id.tv_youbian);
        tv_szdizhi = (EditText)findViewById(R.id.tv_szdizhi);
        tv_xxdizhi = (EditText)findViewById(R.id.tv_xxdizhi);
        rl_xzshdz = (RelativeLayout)findViewById(R.id.rl_xzshdz);
        im_addaddress_back.setOnClickListener(this);
        rl_xzshdz.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.im_addaddress_back:
                AddAddressActivity.this.finish();
                break;
            case R.id.rl_xzshdz:
                requestData();
                break;
        }

    }

    private void requestData() {
        if(params==null){
            params=new RequestParams();
        }
        params.put("userid", Mapplication.getModel().getUserid());
        params.add("realname",tv_shouhuoren.getText().toString());
        params.add("phonenum", tv_shoujihao.getText().toString());
        params.add("address",tv_szdizhi.getText().toString());
        params.add("addressdetail",tv_xxdizhi.getText().toString());
        params.add("postcode",tv_youbian.getText().toString());
        HttpUtil.AddAddress(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                String response = new String(responseBody);
//                JSONObject json = (JSONObject) JSON.parse(response);
                ToastUTil.shortT(AddAddressActivity.this,"添加地址成功");
//                if (json.getString("status").equals("1")) {
//                    JSONArray array = json.getJSONArray("data");
//                    list = JSONArray.parseArray(array.toString(), Order.class);
//                    AppLog.i("orderactivity", array.toJSONString());
//                    adapter = new MyOderAdapter(MyOrderActivity.this, list);
//                    lv_order.setAdapter(adapter);
//                    page = page + 1;
//                } else {
//                    ToastUTil.shortT(MyOrderActivity.this, json.getString("msg"));
//                }
//                if (lv_order.isRefreshing()) {
//                    lv_order.onRefreshComplete();
//                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ToastUTil.shortT(AddAddressActivity.this, "网络连接错误");
            }
        }, params);
    }
}
