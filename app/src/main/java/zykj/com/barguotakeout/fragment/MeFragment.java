package zykj.com.barguotakeout.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.activity.LoginActivity;
import zykj.com.barguotakeout.activity.MCountActivity;
import zykj.com.barguotakeout.activity.MyOrderActivity;
import zykj.com.barguotakeout.activity.SettingActivity;
import zykj.com.barguotakeout.http.HttpErrorHandler;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.http.UrlContants;
import zykj.com.barguotakeout.model.User;
import zykj.com.barguotakeout.view.RoundImageView;

/**
 * Created by ss on 15-4-28.
 */
public class MeFragment extends CommonLoadFragment implements View.OnClickListener {

    private TextView tv;
    private LinearLayout ll_order;
    private RoundImageView rv_avator;
    private LinearLayout ll_top;
    private TextView tv_mobile;
    private RelativeLayout rl_me;
    private User user;
    private RelativeLayout rl_set;

    public static final int LOGOUT=5;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_me,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpTopBar();
        initView();
        setTopView();
    }

    private void initView() {
        tv = (TextView) getView().findViewById(R.id.login);
        ll_order = (LinearLayout) getView().findViewById(R.id.ll_me_order);
        ll_top = (LinearLayout) getView().findViewById(R.id.ll_me_top);
        rv_avator = (RoundImageView) getView().findViewById(R.id.rv_me_avatar);//头像
        tv_mobile = (TextView) getView().findViewById(R.id.tv_me_mobile);
        rl_me = (RelativeLayout) getView().findViewById(R.id.rl_me_top);
        rl_set = (RelativeLayout) getView().findViewById(R.id.rl_me_setting);
        rl_me.setOnClickListener(this);
        ll_order.setOnClickListener(this);
        tv.setOnClickListener(this);
        rl_set.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == Activity.RESULT_OK){
            //刷新头像和
            setTopView();
        }
        if(requestCode==LOGOUT && resultCode==Activity.RESULT_OK){
            setTopView();
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_me_order:
                //查看用户订单
                startActivity(new Intent(getActivity(), MyOrderActivity.class));
                break;
            case R.id.rl_me_top:
                //查看用户详情
                Intent intent = new Intent(getActivity(), MCountActivity.class);
                if(user != null && !TextUtils.isEmpty(Mapplication.getModel().getUsername())){
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                break;
            case R.id.rl_me_setting:
                Intent intent1=new Intent(getActivity(), SettingActivity.class);
                startActivityForResult(intent1,LOGOUT);
                break;
            case R.id.login:
                //登录界面
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), 0);
                break;
        }
    }

    public void setTopView(){
        if(TextUtils.isEmpty(Mapplication.getModel().getUsername())){
            //还没有登录
            tv.setVisibility(View.VISIBLE);
            hideTop();
        }else{
            //已经登陆
            showTop();
            RequestParams params=new RequestParams();
            params.add("phonenum",Mapplication.getModel().getUsername());
            params.add("password",Mapplication.getModel().getPwd());
            HttpUtil.login(new HttpErrorHandler() {
                @Override
                public void onRecevieSuccess(JSONObject json) {
                    //初始化头像 保存信息
                   JSONObject data=json.getJSONObject("data");
                   user=JSONObject.parseObject(data.toJSONString(),User.class);
                    if(user.getAvatar()!=null){
                    ImageLoader.getInstance().displayImage(UrlContants.getUrl(user.getAvatar()),rv_avator);}
                }

                @Override
                public void onRecevieFailed(String status, JSONObject json) {

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            },params);
            tv.setVisibility(View.GONE);
            tv_mobile.setText(Mapplication.getModel().getUsername());
        }

    }

    private void hideTop(){
        rv_avator.setVisibility(View.GONE);
        ll_top.setVisibility(View.GONE);
    }
    private void showTop(){
        rv_avator.setVisibility(View.VISIBLE);
        ll_top.setVisibility(View.VISIBLE);
    }



}
