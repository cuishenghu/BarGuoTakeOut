package zykj.com.barguotakeout.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.fragment.CommonProgressFragment;
import zykj.com.barguotakeout.http.HttpErrorHandler;
import zykj.com.barguotakeout.http.HttpUtil;

/**
 * Created by ss on 15-5-6.
 */
public class ModifyPwd extends CommonActivity implements View.OnClickListener {

    private Button btn_save;
    private EditText oldpwd;
    private EditText newpwd;
    private String userid;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifypwd);
        userid=getIntent().getStringExtra("userid");
        initView();
    }

    private void initView() {
        oldpwd = (EditText) findViewById(R.id.et_pwd_old);
        newpwd = (EditText) findViewById(R.id.et_pwd_new);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_save){
            //提交修改密码
            string = oldpwd.getText().toString().trim();
            String newpwd = this.newpwd.getText().toString().trim();

            if(!string.equals(Mapplication.getModel().getPwd())){
                ToastUTil.shortT(ModifyPwd.this,"原密码输入错误");
                return;
            }

           if(checkPWd(newpwd)){
               //请求修改密码
               updatePwd(newpwd);
           }else{
               ToastUTil.shortT(ModifyPwd.this,"密码格式输入错误");
           }


        }
    }

    private boolean checkPWd(String str) {
        if(str.length()<6 || str.length()>10){
            return false;
        }
        return true;
    }

    public void back(View v){
        finish();
    }

    private void updatePwd(final String newpwd){
        RequestParams params=new RequestParams();
        if(TextUtils.isEmpty(userid)){
            ToastUTil.shortT(ModifyPwd.this,"系统内部错误");
            return;
        }
        params.add("userid",userid);
        params.add("oldpwd",string);//旧密码
        params.add("newpwd",newpwd);
        CommonProgressFragment.getInstance("正在提交...").show(getSupportFragmentManager(),"progress");
        HttpUtil.modifyPwd(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                //修改成功
                CommonProgressFragment.disappear();
                ToastUTil.shortT(ModifyPwd.this,json.getString("msg"));
                Mapplication.getModel().setPwd(newpwd);
                finish();
            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {
                CommonProgressFragment.disappear();
                ToastUTil.shortT(ModifyPwd.this,json.getString("msg"));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        },params);



    }
}
