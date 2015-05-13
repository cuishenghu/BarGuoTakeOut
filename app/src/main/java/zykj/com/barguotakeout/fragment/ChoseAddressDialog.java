package zykj.com.barguotakeout.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;

import java.util.List;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.http.EntityHandler;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.model.Address;
import zykj.com.barguotakeout.model.City;
import zykj.com.barguotakeout.model.District;

/**
 * Created by ss on 15-4-18.
 */
public class ChoseAddressDialog extends Fragment {

    private ListView lv_p;
    private ListView lv_city;
    private ListView lv_xiang;
    private ListView lv_jie;
    private List<Address> province;//省的list
    private List<City> citylist;//市的list、
    private List<District> districtList;//县的list

    private static final String TAG=ChoseAddressDialog.class.getSimpleName();



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv_p = (ListView) getView().findViewById(R.id.lv_province);
        lv_city = (ListView)getView().findViewById(R.id.lv_city);
         lv_xiang = (ListView)getView().findViewById(R.id.lv_xiang);//乡镇
        lv_jie = (ListView)getView().findViewById(R.id.lv_address);

        RequestParams provinceParams=new RequestParams();
        provinceParams.add("type","0");

        HttpUtil.chooseAddress(new EntityHandler<Address>(Address.class) {
            @Override
            public void onReadSuccess(List<Address> list) {
                province=list;
                lv_p.setAdapter(new ArrayAdapter<Address>(getActivity(),R.layout.fragment_chooseaddress,list));
            }
        }, provinceParams);

        lv_p.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击省时 根据省id 查询该省的市
              String pid=  province.get(position).getProvinceId();//省的id
                AppLog.i(TAG,pid);
              RequestParams cityParams=new RequestParams();
                cityParams.add("type","1");
                cityParams.add("areaid",pid);
                AppLog.w(TAG,cityParams.toString());
                HttpUtil.chooseAddress(new EntityHandler<City>(City.class) {
                    @Override
                    public void onReadSuccess(List<City> list) {
                        citylist=list;
                        lv_city.setAdapter(new ArrayAdapter<City>(getActivity(),R.layout.fragment_chooseaddress,list));
                    }
                },cityParams);//请求之后在获取
            }
        });

        lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dID=citylist.get(position).getCityId();
                RequestParams discParams=new RequestParams();
                discParams.add("type","2");
                discParams.add("areaid",dID);
                HttpUtil.chooseAddress(new EntityHandler<District>(District.class) {

                    @Override
                    public void onReadSuccess(List<District> list) {
                        lv_xiang.setAdapter(new ArrayAdapter<District>(getActivity(),R.layout.fragment_chooseaddress,list));
                    }
                },discParams);
            }
        });
        lv_xiang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choseaddress,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



}
