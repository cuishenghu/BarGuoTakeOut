package zykj.com.barguotakeout.activity;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.Utils.Commonutils;
import zykj.com.barguotakeout.fragment.ChoseAddressDialog;
import zykj.com.barguotakeout.location.LocationActivity;

/**
 * Created by ss on 15-4-20. 定位
 */
public class LocatActivity extends LocationActivity implements View.OnClickListener {


    private LinearLayout btn_autoLocate;

    private AMapLocationListener mapLocationListener;
    private TextView tv_address;
    private TextView tv_detail;

    private String addr;

    private double longtitude=0;
    private double latitude=0;

    public static final int REQUESTCODE=0;

    private static final  String TAG=LocatActivity.class.getSimpleName();
    private TextView title;
    private Button btn_confirm;
    private ProgressBar pb;
    private TextView tv_manually;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locat_activity);
        initView();
        initLocatListener();
    }

    private AMapLocationListener initLocatListener() {

        if(mapLocationListener == null){
            mapLocationListener = new AMapLocationListener() {
                @Override
                public void onLocationChanged(AMapLocation aMapLocation) {
                    //活取到位置 是否需要先检查网络
                    if(aMapLocation.getAMapException().getErrorCode()==0) {
                        //错误码为0 代表定位成功
                        AppLog.i(TAG, aMapLocation.getStreet() + "\n" + aMapLocation.getCity()+"\n"+aMapLocation.getRoad());
                        String street = aMapLocation.getStreet();

                        String address = aMapLocation.getAddress();//详细信息 省 市 街道
                        double latitude = aMapLocation.getLatitude();//维度
                        double longitude = aMapLocation.getLongitude();//经度
                        addr=Commonutils.handlerAddress(address);
                        title.setText(addr);
                        String code=aMapLocation.getCityCode();
                        Mapplication.getModel().setAddress(addr);
                        // 还要保存到 帮助类中
                        Mapplication.getModel().setLongitude(longitude);
                        Mapplication.getModel().setLatitude(latitude);
                        Mapplication.getModel().setCitycode(code);
                        //保存
                        pb.setVisibility(View.GONE);

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
            };
        }
        return mapLocationListener;

    }


    private void initView() {
         btn_autoLocate = (LinearLayout) findViewById(R.id.ll_autolocate);
         //tv_address = (TextView) findViewById(R.id.tv_address);
        tv_detail = (TextView) findViewById(R.id.tv_detail_address);
        title = (TextView) findViewById(R.id.index_title);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        pb = (ProgressBar) findViewById(R.id.locat_progress);
        tv_manually = (TextView) findViewById(R.id.btn_manually);
        tv_manually.setOnClickListener(this);
        pb.setVisibility(View.GONE);

        if(!TextUtils.isEmpty(Mapplication.getModel().getAddress())){
            title.setText(Mapplication.getModel().getAddress());
        }

        title.setMovementMethod(new ScrollingMovementMethod());

        btn_autoLocate.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    public void back(View v){
        finish();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.ll_autolocate:
                pb.setVisibility(View.VISIBLE);
                super.requestLocation(initLocatListener());
                break;
            case R.id.btn_confirm:
                break;
            case R.id.btn_manually:
                //手动切换地址
                ChoseAddressDialog dialog=new ChoseAddressDialog();
                getSupportFragmentManager().beginTransaction().add(R.id.chooseFragment,dialog).commit();
                break;
        }

    }
}
