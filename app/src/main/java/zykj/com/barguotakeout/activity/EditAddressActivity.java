package zykj.com.barguotakeout.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.ToastUTil;

public class EditAddressActivity extends CommonActivity implements View.OnClickListener {
    ImageView im_editaddress_back;
    TextView tv_edit_xiugai;
    TextView tv_edit_shouhuoren;
    TextView tv_edit_shoujihao;
    TextView tv_edit_youbian;
    TextView tv_edit_szdizhi;
    TextView tv_edit_xxdizhi;
    LinearLayout ll_edit_scshdz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        initView();
    }

    void initView(){
        im_editaddress_back = (ImageView)findViewById(R.id.im_editaddress_back);
        tv_edit_xiugai = (TextView)findViewById(R.id.tv_edit_xiugai);
        tv_edit_shouhuoren = (TextView)findViewById(R.id.tv_edit_shouhuoren);
        tv_edit_shoujihao = (TextView)findViewById(R.id.tv_edit_shoujihao);
        tv_edit_youbian = (TextView)findViewById(R.id.tv_edit_youbian);
        tv_edit_szdizhi = (TextView)findViewById(R.id.tv_edit_szdizhi);
        tv_edit_xxdizhi = (TextView)findViewById(R.id.tv_edit_xxdizhi);
        ll_edit_scshdz = (LinearLayout)findViewById(R.id.ll_edit_scshdz);
        im_editaddress_back.setOnClickListener(this);
        tv_edit_xiugai.setOnClickListener(this);
        ll_edit_scshdz.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.im_editaddress_back:
                EditAddressActivity.this.finish();
                break;
            case R.id.tv_edit_xiugai:
                ToastUTil.shortT(EditAddressActivity.this, "修改成功");
                break;
            case R.id.ll_edit_scshdz:
                ToastUTil.shortT(EditAddressActivity.this,"删除成功");
                break;
        }

    }
}
