package zykj.com.barguotakeout.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import info.hoang8f.android.segmented.SegmentedGroup;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.fragment.RestaurantList;

/**
 * Created by ss on 15-4-21.
 */
public class OrderActivity extends TopBarActivity implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG=OrderActivity.class.getSimpleName();
    private RadioButton button1;
    private RadioButton button2;

    private Integer currentItem=0;
    private RestaurantList restaurantList;
    private RestaurantList list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initFragment();
        initView();
        //保存 打开的标签

    }

    private void initFragment() {
        restaurantList = new RestaurantList();
        list2 = new RestaurantList();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frag_layout,restaurantList)
                .add(R.id.frag_layout,list2)
                .show(restaurantList)
                .hide(list2).commit();
        currentItem=0;
    }

    private void initView() {
        SegmentedGroup group= (SegmentedGroup) findViewById(R.id.radios);
        group.setTintColor(getResources().getColor(R.color.white),getResources().getColor(R.color.top_color));
        group.setOnCheckedChangeListener(this);

        button1 = (RadioButton) findViewById(R.id.radio_btn1);
        button2 = (RadioButton) findViewById(R.id.radio_btn2);

        button1.setChecked(true);//设置选中


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio_btn1:
                if(currentItem == 0){
                    return;
                }
                getSupportFragmentManager().beginTransaction()
                        .show(restaurantList).commit();
                currentItem=0;
                break;
            case R.id.radio_btn2:
                if(currentItem == 1){
                    return;
                }
                getSupportFragmentManager().beginTransaction()
                        .show(list2)
                        .commit();
                currentItem=1;
                break;
        }
    }

    public void back(View v){
        finish();
    }
}
