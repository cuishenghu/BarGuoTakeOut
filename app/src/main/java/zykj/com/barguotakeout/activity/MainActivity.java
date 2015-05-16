package zykj.com.barguotakeout.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

import java.util.ArrayList;
import java.util.List;

import cn.smssdk.EventHandler;
import zykj.com.barguotakeout.AppModel;
import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.Commonutils;
import zykj.com.barguotakeout.fragment.BgBangFragment;
import zykj.com.barguotakeout.fragment.FindFragment;
import zykj.com.barguotakeout.fragment.IndexFragment;
import zykj.com.barguotakeout.fragment.LocationChangeListener;
import zykj.com.barguotakeout.fragment.MeFragment;
import zykj.com.barguotakeout.location.LocationActivity;
import zykj.com.barguotakeout.view.ChangeColorIconWithText;


public class MainActivity extends LocationActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {


    private ViewPager pager;
    private List<ChangeColorIconWithText> tabList=new ArrayList<ChangeColorIconWithText>();
    private List<Fragment> mFragmentList=new ArrayList<Fragment>();

    private FragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTag(MainActivity.class.getSimpleName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //当应用打开时 自动定位
       // setLocation();

        initView();
        initEvents();
        initLocation();
    }

    private void initLocation() {
        requestLocation(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                //当位置改变时 保存数据
                if(aMapLocation.getAMapException().getErrorCode()==0) {
                    String addr=Commonutils.handlerAddress(aMapLocation.getAddress());
                    Mapplication.getModel().setLongitude(aMapLocation.getLongitude());
                    Mapplication.getModel().setLatitude(aMapLocation.getLatitude());
                    Mapplication.getModel().setCitycode(aMapLocation.getCityCode());
                    Mapplication.getModel().setAddress(addr);
                    for (int i = 0; i < mFragmentList.size(); i++) {
                        Fragment fragment = mFragmentList.get(i);
                        if (fragment instanceof LocationChangeListener) {
                            ((LocationChangeListener) fragment).located(addr);
                        }
                    }
                }
            }

            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    private void initEvents() {


    }


    /**
     * 短信验证回调
     */
    private EventHandler smsCallback=new EventHandler(){
        @Override
        public void onRegister() {
            super.onRegister();
        }

        @Override
        public void beforeEvent(int i, Object o) {
            super.beforeEvent(i, o);
        }

        @Override
        public void afterEvent(int i, int i2, Object o) {
            super.afterEvent(i, i2, o);
        }

        @Override
        public void onUnregister() {
            super.onUnregister();
        }
    };


    private void initView() {

        ChangeColorIconWithText tab_index= (ChangeColorIconWithText) findViewById(R.id.tab_index);
        tabList.add(tab_index);
        ChangeColorIconWithText tab_bang= (ChangeColorIconWithText) findViewById(R.id.tab_bang);
        tabList.add(tab_bang);
        ChangeColorIconWithText tab_find= (ChangeColorIconWithText) findViewById(R.id.tab_find);
        tabList.add(tab_find);
        ChangeColorIconWithText tab_i= (ChangeColorIconWithText) findViewById(R.id.tab_i);
        tabList.add(tab_i);




        tab_index.setOnClickListener(this);
        tab_bang.setOnClickListener(this);
        tab_find.setOnClickListener(this);
        tab_i.setOnClickListener(this);

        tab_index.setIconAlpha(1.0f);

        IndexFragment indexFragment=new IndexFragment();
        BgBangFragment indexFragment2=new BgBangFragment();
        FindFragment indexFragment3=new FindFragment();
        MeFragment indexFragment4=new MeFragment();

        mFragmentList.add(indexFragment);
        mFragmentList.add(indexFragment2);
        mFragmentList.add(indexFragment3);
        mFragmentList.add(indexFragment4);

        pager = (ViewPager) findViewById(R.id.index_pager);
        pager.setOnPageChangeListener(this);

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        };

        pager.setAdapter(mPagerAdapter);


       /* register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler(){
                    @Override
                    public void onRegister() {
                        super.onRegister();
                        //注册
                    }

                    @Override
                    public void beforeEvent(int i, Object o) {
                        super.beforeEvent(i, o);
                        //注册之前
                    }

                    @Override
                    public void afterEvent(int i, int result, Object o) {
                        super.afterEvent(i, result, o);
                        //注册之后 it's important
                        if(result == SMSSDK.RESULT_COMPLETE){
                            HashMap<String,Object> map= (HashMap<String, Object>) o;
                            String phone= (String) map.get("phone");
                            Toast.makeText(getApplicationContext(),phone,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onUnregister() {
                        super.onUnregister();
                    }
                });
                registerPage.show(MainActivity.this);
            }
        });*/



    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.tab_index:
                clickTab(v);
                break;
            case R.id.tab_bang:
                clickTab(v);
                break;
            case R.id.tab_find:
                clickTab(v);
                break;
            case R.id.tab_i:
                clickTab(v);
                break;
        }


    }

    private void clickTab(View v)
    {
        resetTab();

        switch (v.getId())
        {
            case R.id.tab_index:
                tabList.get(0).setIconAlpha(1.0f);
                pager.setCurrentItem(0, false);
                break;
            case R.id.tab_bang:
                tabList.get(1).setIconAlpha(1.0f);
                pager.setCurrentItem(1, false);
                break;
            case R.id.tab_find:
                tabList.get(2).setIconAlpha(1.0f);
                pager.setCurrentItem(2, false);
                break;
            case R.id.tab_i:
                tabList.get(3).setIconAlpha(1.0f);
                pager.setCurrentItem(3, false);
                break;
        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0)
        {
            ChangeColorIconWithText left = tabList.get(position);
            ChangeColorIconWithText right = tabList.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 重置其他的TabIndicator的颜色
     */
    public void resetTab(){

            for (int i = 0; i < tabList.size(); i++)
            {
                tabList.get(i).setIconAlpha(0);
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case LocatActivity.REQUESTCODE:
                //请求定位的结果
                if(resultCode== Activity.RESULT_OK){
                    //请求成功
                }
                break;
        }
    }
    // 退出操作
    private boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_LONG).show();
                Handler mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        isExit = false;
                    }
                };
                mHandler.sendEmptyMessageDelayed(0, 3000);
                return true;
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
                return false;
            }
        }
        return true;
    }
}
