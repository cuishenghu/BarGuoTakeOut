package zykj.com.barguotakeout.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.ToastUTil;

/**
 * Created by ss on 15-5-6.
 */
public class SettingActivity extends CommonActivity implements View.OnClickListener {

    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();


    }

    private void initView() {
        btn_logout = (Button) findViewById(R.id.btn_set_logout);
        btn_logout.setOnClickListener(this);
    }

    public void back(View v){
        finish();
    }

    /**
     * 退出登录
     */
    public void logout(){
        Mapplication.getModel().setUsername("");
        Mapplication.getModel().setPwd("");
        Mapplication.getModel().setUserid("");
        ToastUTil.shortT(this,"已退出");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onClick(View v) {
           switch (v.getId()){
               case R.id.btn_set_logout:
                   //登出
                    logout();
                   break;
           }
    }
}
