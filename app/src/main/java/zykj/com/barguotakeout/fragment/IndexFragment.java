package zykj.com.barguotakeout.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.List;

import zykj.com.barguotakeout.AppModel;
import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.activity.CreditActivity;
import zykj.com.barguotakeout.activity.CreditActivity.CreditsListener;
import zykj.com.barguotakeout.activity.DayJokeActivity;
import zykj.com.barguotakeout.activity.LocatActivity;
import zykj.com.barguotakeout.activity.MonthLuckActivity;
import zykj.com.barguotakeout.activity.OrderActivity;
import zykj.com.barguotakeout.http.EntityHandler;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.model.IndexUrl;

/**
 * Created by ss on 15-4-17.
 */
public class IndexFragment extends CommonFragment implements View.OnClickListener,LocationChangeListener {

    public static final String APPKEY = "3Zxiw1dny8Msn4P8TmyoeECqtKdu";
    public static final String APPSECRET = "3zW6J9ZaqMJq6jxK3NqEWmYQrKTx";

    private SliderLayout mSliderLayout;
    private CommonNetErrorFragment errorFragment;
    private TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTAG(IndexFragment.class.getSimpleName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.index_fragment,container,false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        RequesData();//获取首页轮播图地址
        setUpLocation();
    }

    private void RequesData() {
        HttpUtil.indexPicUrl(new EntityHandler<IndexUrl>(IndexUrl.class) {

            @Override
            public void onReadSuccess(List<IndexUrl> list) {
                if(mSliderLayout ==null){
                    AppLog.i("readDa","mSlider is null");
                    return;
                }
                for(IndexUrl url:list){
                    TextSliderView view=new TextSliderView(getActivity());
                    view.setScaleType(BaseSliderView.ScaleType.Fit)
                            .image(url.getAdurl())
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView baseSliderView) {
                                    //轮播图点击事件
                                }
                            });
                    mSliderLayout.addSlider(view);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
              //  errorFragment = new CommonNetErrorFragment();
                //getChildFragmentManager().beginTransaction().replace(R.id.container, errorFragment).show(errorFragment).commit();
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setUpLocation() {
        if(TextUtils.isEmpty(Mapplication.getModel().getAddress())){
            return;
        }else {
            located(Mapplication.getModel().getAddress());//显示定位的地址
        }
    }

    private void initView() {
        mSliderLayout = (SliderLayout) getView().findViewById(R.id.index_slider);
        RelativeLayout btn_joke= (RelativeLayout) getView().findViewById(R.id.btn_joke);
        RelativeLayout btn_order= (RelativeLayout) getView().findViewById(R.id.btn_order);
        RelativeLayout btn_luck= (RelativeLayout) getView().findViewById(R.id.btn_luck);
        RelativeLayout topbar= (RelativeLayout) getView().findViewById(R.id.top_bar);
        RelativeLayout index_gift= (RelativeLayout) getView().findViewById(R.id.tv_index_gift);
        title = (TextView) getView().findViewById(R.id.index_title);

        btn_joke.setOnClickListener(this);
        btn_order.setOnClickListener(this);
        btn_luck.setOnClickListener(this);
        topbar.setOnClickListener(this);
        index_gift.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.local_btn:
                //定位
                startActivity(new Intent(getActivity(), LocatActivity.class));
                break;
            case R.id.btn_joke:
                //每日笑话
                startActivity(new Intent(getActivity(), DayJokeActivity.class));
                break;
            case R.id.btn_luck:
                //每月幸运星
                startActivity(new Intent(getActivity(), MonthLuckActivity.class));
                break;
            case R.id.top_bar:
                //定位
                startActivityForResult(new Intent(getActivity(), LocatActivity.class),LocatActivity.REQUESTCODE);
                break;
            case R.id.btn_order:
                //我要订外卖
                startActivity(new Intent(getActivity(), OrderActivity.class));
                break;
            case R.id.tv_index_gift:
                //巴国礼品站
                if(TextUtils.isEmpty(Mapplication.getModel().getUserid())){
                    ToastUTil.showToastText(getActivity(),"提示","请先登录!","确定");
                    return;
                }
                RequestParams params = new RequestParams();
                //appkey:兑吧的appkey,必填；appsecret：兑吧的appsecret，必填；userid：用户的userid,必填；
                params.add("appkey",APPKEY);
                params.add("appsecret",APPSECRET);
                params.add("userid",Mapplication.getModel().getUserid());
                HttpUtil.getduibaurl(new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            String responseString = new String(responseBody, HTTP.UTF_8);
                            JSONObject jsonObject = (JSONObject)JSON.parse(responseString);
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            String url = jsonData.getString("url");
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), CreditActivity.class);
                            intent.putExtra("navColor", "#0acbc1");    //配置导航条的背景颜色，请用#ffffff长格式。
                            intent.putExtra("titleColor", "#ffffff");    //配置导航条标题的颜色，请用#ffffff长格式。
                            intent.putExtra("url", url);    //配置自动登陆地址，每次需服务端动态生成。
                            startActivity(intent);
                            CreditActivity.creditsListener = new CreditsListener() {
                                /**
                                 * 当点击分享按钮被点击
                                 * @param shareUrl 分享的地址
                                 * @param shareThumbnail 分享的缩略图
                                 * @param shareTitle 分享的标题
                                 * @param shareSubtitle 分享的副标题
                                 */
                                public void onShareClick(WebView webView, String shareUrl,String shareThumbnail, String shareTitle,  String shareSubtitle) {
                                    //当分享按钮被点击时，会调用此处代码。在这里处理分享的业务逻辑。
                                    new AlertDialog.Builder(webView.getContext())
                                            .setTitle("分享信息")
                                            .setItems(new String[] {"标题："+shareTitle,"副标题："+shareSubtitle,"缩略图地址："+shareThumbnail,"链接："+shareUrl}, null)
                                            .setNegativeButton("确定", null)
                                            .show();
                                }

                                /**
                                 * 当点击登录
                                 * @param webView 用于登录成功后返回到当前的webview并刷新。
                                 * @param currentUrl 当前页面的url
                                 */
                                public void onLoginClick(WebView webView, String currentUrl) {
                                    //当未登录的用户点击去登录时，会调用此处代码。
                                    //为了用户登录后能回到之前未登录前的页面。
                                    //当用户登录成功后，需要重新动态生成一次自动登录url，需包含redirect参数，将currentUrl放入redirect参数。
                                    new AlertDialog.Builder(webView.getContext())
                                            .setTitle("跳转登录")
                                            .setMessage("跳转到登录页面？")
                                            .setPositiveButton("是", null)
                                            .setNegativeButton("否", null)
                                            .show();
                                }
                            };
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        ToastUTil.showToastText(getActivity(),"提醒","请求失败,请稍后重试!","确定");
                    }
                },params);
                break;
        }
    }

    @Override
    public void startLoad() {
    }

    @Override
    public void located(String loca) {
        //设置标题栏文字信息
        if(title != null && !TextUtils.isEmpty(loca)){
            title.setText(loca);
        }
    }
}