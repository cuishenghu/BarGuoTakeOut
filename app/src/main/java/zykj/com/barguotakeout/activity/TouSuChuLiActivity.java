package zykj.com.barguotakeout.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import zykj.com.barguotakeout.R;

/**
 * lss 2015/5/15 投诉处理
 */
public class TouSuChuLiActivity extends CommonActivity implements View.OnClickListener {
    ImageView im_tscl_back;
    EditText et_tscl_text;
    Button bt_tscl_tijiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tou_su_chu_li);
        initView();
    }

    private void initView() {
        im_tscl_back = (ImageView) findViewById(R.id.im_tscl_back);
        et_tscl_text = (EditText) findViewById(R.id.et_tscl_text);
        bt_tscl_tijiao = (Button) findViewById(R.id.bt_tscl_tijiao);
        im_tscl_back.setOnClickListener(this);
        bt_tscl_tijiao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.im_tscl_back:
            this.finish();
            break;
        case R.id.bt_tscl_tijiao:
            //提交处理todo
            break;

        }
    }
}
