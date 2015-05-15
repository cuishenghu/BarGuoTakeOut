package zykj.com.barguotakeout.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import zykj.com.barguotakeout.R;

/**
 * lss 2015/5/15 诚聘
 */
public class ChengPinActivity  extends CommonActivity implements View.OnClickListener {
    ImageView im_chengpin_back;
    Button bt_chengpin_tijiao;
    EditText et_chengpin_xiangfa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheng_pin);
        initView();
    }

    private void initView() {
        im_chengpin_back = (ImageView)findViewById(R.id.im_chengpin_back);
        bt_chengpin_tijiao = (Button)findViewById(R.id.bt_chengpin_tijiao);
        et_chengpin_xiangfa = (EditText)findViewById(R.id.et_chengpin_xiangfa);
        im_chengpin_back.setOnClickListener(this);
        bt_chengpin_tijiao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_chengpin_back:
                this.finish();
                break;
            case R.id.bt_chengpin_tijiao:
                //提交按钮操作（todo）

                break;
        }
    }
}
