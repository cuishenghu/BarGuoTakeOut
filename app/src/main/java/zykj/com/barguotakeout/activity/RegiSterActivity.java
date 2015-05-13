package zykj.com.barguotakeout.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.fragment.CommonProgressFragment;
import zykj.com.barguotakeout.http.HttpErrorHandler;
import zykj.com.barguotakeout.http.HttpUtil;

/**
 * Created by ss on 15-5-4.
 */
public class RegiSterActivity extends CommonActivity implements View.OnClickListener {

    public static final String KEY="phone";
    private String phonenum;
    private EditText et_pwd;
    private EditText et_confirm;
    private Button register;
    private RequestParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phonenum = getIntent().getStringExtra(KEY);
        initView();

    }

    private void initView() {
        et_pwd = (EditText) findViewById(R.id.et_register_pwd);
        et_confirm = (EditText) findViewById(R.id.et_register_confirm);

        register = (Button) findViewById(R.id.btn_register_register);

        register.setOnClickListener(this);

        params = new RequestParams();
    }

    public void back(View v){
        finish();
    }

    @Override
    public void onClick(View v) {
       String pwd=et_pwd.getText().toString();
        String confirm=et_confirm.getText().toString();

        if(TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confirm)){
            ToastUTil.shortT(RegiSterActivity.this,"请先填写密码");
            return;
        }
        if(!pwd.equals(confirm)){
            ToastUTil.shortT(RegiSterActivity.this,"两次密码输入不一致");
            return;
        }

        if(phonenum==null || phonenum.equals("")){
            ToastUTil.shortT(RegiSterActivity.this,"内部错误,请重试");
            return ;
        }

        params.add("phonenum",phonenum);
        params.add("password",pwd);
        CommonProgressFragment.getInstance("注册中,请稍候").show(getSupportFragmentManager(),"progress");
        HttpUtil.register(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                //注册成功
                ToastUTil.shortT(RegiSterActivity.this,"注册成功"+json.toString());
                CommonProgressFragment.disappear();
            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {
                //注册出错
                ToastUTil.shortT(RegiSterActivity.this,json.get("msg").toString());
                CommonProgressFragment.disappear();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                CommonProgressFragment.disappear();
                ToastUTil.shortT(RegiSterActivity.this,"网络连接错误,请检查后重试");
            }
        },params);

    }
}
