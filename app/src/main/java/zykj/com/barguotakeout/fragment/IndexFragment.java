package zykj.com.barguotakeout.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.apache.http.Header;
import org.w3c.dom.Text;

import java.util.List;

import zykj.com.barguotakeout.AppModel;
import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
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
                //startActivity(new Intent(getActivity(), BaGuoGiftActivity.class));
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
