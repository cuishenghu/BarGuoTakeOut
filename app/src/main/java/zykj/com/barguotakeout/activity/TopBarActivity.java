package zykj.com.barguotakeout.activity;

import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;

/**
 * Created by ss on 15-4-21.
 */
public class TopBarActivity extends CommonActivity {

    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void initTopbar(){
        findViewById(R.id.top_bar);
        tv_title = (TextView) findViewById(R.id.index_title);
        String address=Mapplication.getModel().getAddress();
        if(!TextUtils.isEmpty(address)){
            tv_title.setText(address);
        }
        //StateListDrawable stateListDrawable=new StateListDrawable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initTopbar();
    }
}
