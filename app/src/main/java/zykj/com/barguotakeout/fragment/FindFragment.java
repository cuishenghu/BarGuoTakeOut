package zykj.com.barguotakeout.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import zykj.com.barguotakeout.AppModel;
import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.http.HttpUtil;

/**
 * Created by ss on 15-4-29.
 */
public class FindFragment extends CommonLoadFragment{

    private String[] zixuns=new String[]{"百度","新浪","搜狐","腾讯","凤凰"};
    private int[] ziunics=new int[]{R.mipmap.ic_baidu,R.mipmap.ic_xinlang,R.mipmap.ic_souhu,R.mipmap.ic_tengxun,R.mipmap.ic_fenghuang};
    private String[] ad=new String[]{
            "https://www.baidu.com/","http://www.sina.com.cn/","http://www.sohu.com/","http://www.qq.com/","http://www.ifeng.com/"
    };

    private String[] gouwus=new String[]{"淘宝","京东","天猫","一号店","唯品会"};
    private int[] gouwusics=new int[]{R.mipmap.ic_taobao,R.mipmap.ic_jingdong,R.mipmap.ic_tianmao,R.mipmap.ic_yihaodian,R.mipmap.ic_weipinhui};
    private String[] gouwuh=new String[]{
            "http://www.taobao.com/","http://www.jd.com/","http://www.tmall.com/","http://www.yhd.com/","http://www.vip.com/"
    };

    private String[] shenghuos=new String[]{"赶集","有缘","搜狐视频","游戏","电子书"};
    private int[] shenghuosics=new int[]{R.mipmap.ic_ganji,R.mipmap.ic_youyuan,R.mipmap.ic_souhushipin,R.mipmap.ic_youxi,R.mipmap.ic_dianzishu};
    private String[] shenghuosd=new String[]{
            "http://linyi.ganji.com/","http://www.youyuan.com/","http://tv.sohu.com/","http://android.d.cn/","http://www.qidian.com//"
    };

    private String[] chaxuns=new String[]{"去哪儿","火车票","汽车违章","快递","彩票开奖"};
    private int[] chaxunsics=new int[]{R.mipmap.ic_qunaer,R.mipmap.ic_huochepiao,R.mipmap.ic_qicheweizhang,R.mipmap.ic_kuaidi,R.mipmap.ic_caipiao};
    private String[] chaxunsd=new String[]{
            "http://www.qunar.com/","http://www.12306.cn/","http://www.ip138.com/weizhang.htm","http://www.kuaidi100.com/","http://baidu.lecai.com/"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWeather();
        initView();
    }

    private void initView() {
        GridView gv_zixun= (GridView) getView().findViewById(R.id.gv_find_zixun);
        gv_zixun.setAdapter(new GridIcAdapter(getActivity(),zixuns,ziunics));
        gv_zixun.setOnItemClickListener(new FindItemClick(ad));

        GridView gv_gouwu= (GridView) getView().findViewById(R.id.gv_find_gouwu);
        gv_gouwu.setAdapter(new GridIcAdapter(getActivity(),gouwus,gouwusics));
        gv_gouwu.setOnItemClickListener(new FindItemClick(gouwuh));


        GridView gv_shenghuo= (GridView) getView().findViewById(R.id.gv_find_shenghuo);
        gv_shenghuo.setAdapter(new GridIcAdapter(getActivity(),shenghuos,shenghuosics));
        gv_shenghuo.setOnItemClickListener(new FindItemClick(shenghuosd));

        GridView gv_chaxun = (GridView)getView().findViewById(R.id.gv_find_chaxun);
        gv_chaxun.setAdapter(new GridIcAdapter(getActivity(),chaxuns,chaxunsics));
        gv_chaxun.setOnItemClickListener(new FindItemClick(chaxunsd));
    }

    private void initWeather() {
        final TextView tv_city= (TextView) getView().findViewById(R.id.tv_find_city);
        final TextView tv_weather= (TextView) getView().findViewById(R.id.tv_find_weather);
        final TextView tv_wendu= (TextView) getView().findViewById(R.id.tv_find_wendu);
        final LinearLayout ll_top= (LinearLayout) getView().findViewById(R.id.ll_find_top);
       String c= Mapplication.getModel().getCitycode();
        if(TextUtils.isEmpty(c)){
           return;
        }
        HttpUtil.weatherInfo(c,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response=new String(responseBody);
                JSONObject json=JSONObject.parseObject(response);
                if(json.getInteger("errNum")==0){
                    JSONObject d=json.getJSONObject("retData");
                    String city=d.getString("city");
                    String weather=d.getString("weather");
                    String temp=d.getString("temp");
                    tv_city.setText(city);
                    tv_weather.setText(weather);
                    tv_wendu.setText(String.format("°%s",temp));
                }else{
                    ll_top.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ll_top.setVisibility(View.GONE);
            }
        });

    }

    class GridIcAdapter extends BaseAdapter {
        String[] names;
        int[] ids;
        LayoutInflater inflater;
        GridIcAdapter(Context ctx,String[] name,int[] id) {
            inflater=LayoutInflater.from(ctx);
            names=name;
            ids=id;
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=inflater.inflate(R.layout.item_find_grid,null);
            ImageView img= (ImageView) view.findViewById(R.id.iv_find_grid);
            TextView tv_name= (TextView) view.findViewById(R.id.tv_find_name);
            img.setImageResource(ids[position]);
            tv_name.setText(names[position]);
            return view;
        }
    }

    class FindItemClick implements AdapterView.OnItemClickListener{
          String[] a;
        public FindItemClick(String[] a){
            this.a=a;
        }


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            loadUrl(a[position]);
        }

        public void loadUrl(String url){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            startActivity(intent);
        }
    }

}
