package zykj.com.barguotakeout.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import zykj.com.barguotakeout.R;

/**
 * lss 2015/5/15 公司简介
 */
public class GongSiJianJieActivity extends CommonActivity implements View.OnClickListener {
    ImageView im_gsjj_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gong_si_jian_jie);
        initView();
    }

    private void initView() {
        im_gsjj_back = (ImageView) findViewById(R.id.im_gsjj_back);
        im_gsjj_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_gsjj_back:
                this.finish();
                break;
        }
    }
}
