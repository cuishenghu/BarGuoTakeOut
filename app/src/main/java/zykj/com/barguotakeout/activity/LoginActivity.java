package zykj.com.barguotakeout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.Utils.SharedPreferenceUtils;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.fragment.CommonProgressFragment;
import zykj.com.barguotakeout.http.HttpErrorHandler;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.model.User;

/**
 * Created by ss on 15-5-4.
 */
public class LoginActivity extends CommonActivity implements View.OnClickListener {

    private Button btn_login;
    private EditText et_username;
    private EditText et_pwd;
    private RequestParams params;
    private TextView tv_register;

    private static final String TAG=LocatActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        init();
    }

    private void init() {
        params = new RequestParams();
    }

    private void initView() {
        //登录
        et_username = (EditText) findViewById(R.id.et_login_username);
        et_pwd = (EditText) findViewById(R.id.et_login_pwd);
        btn_login = (Button) findViewById(R.id.btn_login_login);
        tv_register = (TextView) findViewById(R.id.tv_login_register);
        tv_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_login:
                final String username=et_username.getText().toString().trim();
                final String pwd=et_pwd.getText().toString().trim();
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)){
                    ToastUTil.shortT(LoginActivity.this,"请先输入账号密码");
                    return;
                }
                params.add("phonenum",username);
                params.add("password",pwd);
                CommonProgressFragment.getInstance("正在登录中").show(getSupportFragmentManager(),"progress");
                HttpUtil.login(new HttpErrorHandler() {
                    @Override
                    public void onRecevieSuccess(JSONObject json) {
                        Mapplication.getModel().setUsername(username);
                        Mapplication.getModel().setPwd(pwd);
                        AppLog.i(TAG, json.toString());
                        JSONObject jsonObject = json.getJSONObject("data");
                        String userid = jsonObject.getString("userid");
                        Mapplication.getModel().setUserid(userid);
                        ToastUTil.shortT(LoginActivity.this,"登录成功");
                        CommonProgressFragment.disappear();
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onRecevieFailed(String status, JSONObject json) {
                        CommonProgressFragment.disappear();
                        ToastUTil.shortT(LoginActivity.this,"登录失败,"+json.get("msg").toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        CommonProgressFragment.disappear();
                        ToastUTil.shortT(LoginActivity.this,"网络连接失败,请检查后再试");
                    }
                },params);
                break;
            case R.id.tv_login_register:
                //注册
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                            registerUser(country, phone);
                        }
                    }
                });
                registerPage.show(LoginActivity.this);
                break;
        }
    }

    private void registerUser(String country, String phone) {
        //短信验证成功 填写密码请求注册
        Intent register=new Intent(LoginActivity.this,RegiSterActivity.class);
        register.putExtra(RegiSterActivity.KEY,phone);
        startActivity(register);
    }

    public void back(View v){
        finish();
    }

}
