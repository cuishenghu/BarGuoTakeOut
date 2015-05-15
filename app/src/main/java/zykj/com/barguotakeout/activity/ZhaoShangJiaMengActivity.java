package zykj.com.barguotakeout.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import zykj.com.barguotakeout.R;

/**
 * lss 2015/5/15 招商加盟
 */
public class ZhaoShangJiaMengActivity extends CommonActivity implements View.OnClickListener {
    ImageView im_zsjm_back;
    Button bt_zsjm_tijiao;
    EditText et_zsjm_xiangfa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhao_shang_jia_meng);
        initView();
    }

    private void initView() {
        im_zsjm_back = (ImageView)findViewById(R.id.im_zsjm_back);
        bt_zsjm_tijiao = (Button)findViewById(R.id.bt_zsjm_tijiao);
        et_zsjm_xiangfa = (EditText)findViewById(R.id.et_zsjm_xiangfa);
        im_zsjm_back.setOnClickListener(this);
        bt_zsjm_tijiao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_zsjm_back:
                this.finish();
                break;
            case R.id.bt_zsjm_tijiao:
                //提交按钮操作（todo）

                break;
        }
    }
}
