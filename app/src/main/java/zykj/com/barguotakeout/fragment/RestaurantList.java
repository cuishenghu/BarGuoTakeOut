package zykj.com.barguotakeout.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import java.util.List;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.activity.RestaurantDetail;
import zykj.com.barguotakeout.adapter.ActivityModelAdapter;
import zykj.com.barguotakeout.adapter.CategoryAdapter;
import zykj.com.barguotakeout.adapter.RestaurantAdapter;
import zykj.com.barguotakeout.http.EntityHandler;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.model.ActivitiesModel;
import zykj.com.barguotakeout.model.ResturantModel;

/**
 * Created by ss on 15-4-22.
 */
public class RestaurantList extends CommonFragment implements View.OnClickListener {

    //分类 活动

    private static Integer category=1;//
    private static Integer huodong=1;//默认为1 全部
    private static Integer order=1;//排序方式 默认为0 全部

    private RelativeLayout btn_paixu;
    private RelativeLayout btn_fenlei;
    private RelativeLayout btn_huodong;

    private int page=1;
    private int num=7;
    private PullToRefreshListView listView;
    private RestaurantAdapter adapter;

    private List<ResturantModel> list;

    private PopupWindow popupWindow;
    private ListView pList;
    private String[] titles;
    private String[] paixutitles;

    private List<ActivitiesModel> activitiesModels;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initPop();
    }

    private void initPop() {
        titles = new String[]{"全部","巴国推荐","超市","汉餐","清真"
                ,"早餐","正餐","其他"};

        paixutitles=new String[]{"默认排序","已通过验证","距离最近","销量最大","评价最高","价位高到低","价位低到高"};

        initData();
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.popu_layout,null);
        pList = (ListView) view.findViewById(R.id.lv_content);
        if(popupWindow == null){
            popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            ColorDrawable cd = new ColorDrawable(-0000);
            popupWindow.setBackgroundDrawable(cd);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
        }
    }

    private void initData() {
        HttpUtil.getActivities(new EntityHandler<ActivitiesModel>(ActivitiesModel.class) {
            @Override
            public void onReadSuccess(List<ActivitiesModel> list) {
                activitiesModels=list;
            }
        });
    }



    private void initView() {
        btn_paixu = (RelativeLayout) getView().findViewById(R.id.btn_paixu);
        btn_fenlei = (RelativeLayout) getView().findViewById(R.id.btn_fenlei);
        btn_huodong = (RelativeLayout) getView().findViewById(R.id.btn_huodong);

        btn_paixu.setOnClickListener(this);
        btn_fenlei.setOnClickListener(this);
        btn_huodong.setOnClickListener(this);

        listView = (PullToRefreshListView) getView().findViewById(R.id.lv_restaurant);
        listView.setMode(PullToRefreshBase.Mode.BOTH);


        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //重新加载数据
                requestData(Mapplication.getModel().getLatitude(),Mapplication.getModel().getLongitude());

            }
        });

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉加载更多
                requestData(Mapplication.getModel().getLatitude(),Mapplication.getModel().getLongitude());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadmore(Mapplication.getModel().getLatitude(), Mapplication.getModel().getLongitude());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //判断餐厅是否营业
                ResturantModel model=adapter.getItem(position-1);
                if(model.getIsopen()!=null){
                    if(model.getIsopen().equals("0")){
                        CommonDialogFragment.getInstance("提示","当前时间餐厅没有营业，请稍后再来").show(getFragmentManager(),"dialog");
                    }else{
                        //启动餐厅详情页面
                        Intent intent=new Intent(getActivity(), RestaurantDetail.class);
                        intent.putExtra(RestaurantDetail.KEY,model);
                        startActivity(intent);
                    }
                }
            }
        });

        requestData(Mapplication.getModel().getLatitude(), Mapplication.getModel().getLongitude());

    }

    /**
     * @param lat 纬度
     * @param lo 经度
     */
    private void requestData(double lat,double lo){
        //page:页数 num：每页的条数 order：0代表默认排序 1代表 信誉度排序 2代表起送费
        // 排序 lat 纬度 ：long ：经度 activity:促销活动 '1'代表立减15元 '2'代表支持在线支付 '3'代表 该餐厅可使用优惠券 ,默认不传
        page=1;
        RequestParams params=new RequestParams();
        params.put("page",page);
        params.put("num",num);
        params.put("lat",lat);//纬度
        params.put("long",lo);
        params.put("order",String.valueOf(order));
        params.put("activity",String.valueOf(huodong));
        params.put("category",String.valueOf(category));
        HttpUtil.getRestaurantList(new EntityHandler<ResturantModel>(ResturantModel.class) {
            @Override
            public void onReadSuccess(List<ResturantModel> llist) {
                list=llist;
                adapter = new RestaurantAdapter(getActivity(),list);
                listView.setAdapter(adapter);
                if(listView.isRefreshing()){
                    listView.onRefreshComplete();
                }
            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {
                super.onRecevieFailed(status, json);
                if(status.equals("0")){
                    Toast.makeText(getActivity(),"没有更多数据",Toast.LENGTH_SHORT).show();
                    if(listView.isRefreshing()){
                        listView.onRefreshComplete();
                    }
                    listView.setAdapter(null);

                }else{
                    if(listView.isRefreshing()){
                        listView.onRefreshComplete();
                    }
                    Toast.makeText(getActivity(),json.get("msg").toString(),Toast.LENGTH_SHORT).show();
                }
            }
        },params);
    }

    private void loadmore(double lat,double lo){
        ++page;
        RequestParams params=new RequestParams();
        params.put("page",page);
        params.put("num",num);
        params.put("lat",lat);//纬度
        params.put("long",lo);
        params.put("order",String.valueOf(order));
        params.put("actid",String.valueOf(huodong));
        params.put("category",String.valueOf(category));
        HttpUtil.getRestaurantList(new EntityHandler<ResturantModel>(ResturantModel.class) {
            @Override
            public void onReadSuccess(List<ResturantModel> llist) {
                list.addAll(llist);
                adapter = new RestaurantAdapter(getActivity(),list);
                listView.setAdapter(adapter);
                if(listView.isRefreshing()){
                    listView.onRefreshComplete();
                }
            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {
                super.onRecevieFailed(status, json);
                if(status.equals("0")){
                    Toast.makeText(getActivity(),"没有数据了",Toast.LENGTH_SHORT).show();
                }
                listView.onRefreshComplete();
            }
        },params);
    }





    @Override
    public void onClick(View v) {

        //点击顶部的排序按钮 弹出popu window 点击popuwindow 按钮 重新获取数据
        switch (v.getId()){
            case R.id.btn_fenlei:
                //分类
                pList.setAdapter(new CategoryAdapter(getActivity(),titles,new String[0]));
                pList.setOnItemClickListener(new categoryItemClickListener() {
                    @Override
                    void setUpType(int position) {
                        RestaurantList.category=position+1;
                    }
                });
                popupWindow.showAsDropDown(v);
                break;
            case R.id.btn_paixu:
                //排序
                pList.setAdapter(new CategoryAdapter(getActivity(),paixutitles,new String[0]));
                pList.setOnItemClickListener(new categoryItemClickListener() {
                    @Override
                    void setUpType(int position) {
                        RestaurantList.order=position;
                    }
                });
                popupWindow.showAsDropDown(v);
                break;
            case R.id.btn_huodong:
                if(activitiesModels != null){
                    pList.setAdapter(new ActivityModelAdapter(getActivity(),activitiesModels));
                    pList.setOnItemClickListener(new categoryItemClickListener() {
                        @Override
                        void setUpType(int position) {

                        }
                    });
                    popupWindow.showAsDropDown(v);
                }
                //活动
                break;
        }
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getHuodong() {
        return huodong;
    }

    public void setHuodong(Integer huodong) {
        this.huodong = huodong;
    }

    abstract class categoryItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setUpType(position);
                if(popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
            listView.setRefreshing();
        }
        abstract void setUpType(int position);
    }


}
