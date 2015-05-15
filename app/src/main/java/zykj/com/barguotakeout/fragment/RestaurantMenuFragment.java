package zykj.com.barguotakeout.fragment;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.activity.BuyActivity;
import zykj.com.barguotakeout.adapter.FoodTypeAdapter;
import zykj.com.barguotakeout.adapter.GoodsAdapter;
import zykj.com.barguotakeout.http.EntityHandler;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.model.FoodType;
import zykj.com.barguotakeout.model.Goods;
import zykj.com.barguotakeout.model.GoodsModel;
import zykj.com.barguotakeout.model.OrderPaper;
import zykj.com.barguotakeout.view.OrderList;

/**
 * Created by ss on 15-4-23. 餐厅菜单详情 左边是一个listview 右边显示
 */
public class RestaurantMenuFragment extends  CommonFragment implements AdapterView.OnItemClickListener,
        AdapterView.OnItemSelectedListener, View.OnClickListener ,GoodsAdapter.OrderCallBack {


    private ListView lv;

    private String id;//店铺id

    private List<FoodType> mlist;
    private ListView goodsLv;//商品lv
    private FoodTypeAdapter adapter;
    private GoodsAdapter goodsAdapter;
    private LinearLayout lv_toptitle;
    private TextView title;
    private Button buy;
    private TextView tv_total;
    private ImageView ic_bycar;
    private LinearLayout all_buy_goods;

    private OrderPaper orderPaper;//购物车订单

    private boolean hasGood=false;//底部菜单状态
    private TextView tv_goodnum;

    private static final String TAG=RestaurantMenuFragment.class.getSimpleName();
    private LinearLayout content;
    private PopupWindow popupWindow;
    private LinearLayout bottom_view;

    public static RestaurantMenuFragment newInstance(String resid){

        RestaurantMenuFragment fragment=new RestaurantMenuFragment();
        Bundle bundle = new Bundle();
        bundle.putString("resid",resid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        id=bundle.getString("resid");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_rest_menu,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        orderPaper=new OrderPaper();
        orderPaper.setResid(Integer.valueOf(id));
        initView();
        reqesutData();
    }

    private void reqesutData() {
        //获取所有分类信息，默认选择第一个
        RequestParams params=new RequestParams();
        params.add("resid",id);
        HttpUtil.getFoodTypes(new EntityHandler<FoodType>(FoodType.class) {
            @Override
            public void onReadSuccess(List<FoodType> list) {
                mlist=list;
                adapter = new FoodTypeAdapter(getActivity(),list);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(RestaurantMenuFragment.this);
                if(adapter.getCount()>0){
                    adapter.setSelectItem(0);
                    getFoodsByType(adapter.getItem(0));
                }
            }
        },params);
    }

    private void initView() {
        lv = (ListView) getView().findViewById(R.id.lv_menu);//菜单分类
        goodsLv = (ListView) getView().findViewById(R.id.lv_goods);//菜单列表
        lv.setOnItemSelectedListener(this);
        lv_toptitle= (LinearLayout) getView().findViewById(R.id.lv_top_title);//菜单标题
        lv_toptitle.setVisibility(View.GONE);

        buy = (Button) getView().findViewById(R.id.btn_buy);//选好了
        buy.setOnClickListener(this);

        tv_total = (TextView) getView().findViewById(R.id.tv_total);//显示价格
        ic_bycar = (ImageView) getView().findViewById(R.id.ic_buycar);//购物车图标
        all_buy_goods = (LinearLayout) getView().findViewById(R.id.all_buy_goods);//所有购物车商品

        title = (TextView) getView().findViewById(R.id.tv_top_title);//菜单标题文字

        tv_goodnum = (TextView) getView().findViewById(R.id.tv_goods_num);//购物车里点菜数量

        content = (LinearLayout) getView().findViewById(R.id.content);//页面内容

        bottom_view = (LinearLayout) getView().findViewById(R.id.order_car);//底部购物车

        ic_bycar.setOnClickListener(this);

        resetBottomMenu();
    }

    //初始化底部菜单
    public void resetBottomMenu(){
        //设置底部菜单初始化
        tv_goodnum.setVisibility(View.INVISIBLE);
        buy.setBackgroundColor(Color.parseColor("#808080"));
        tv_total.setText("购物车是空的");
        tv_total.setTextColor(Color.parseColor("#eeeeee"));
        //设置成黑底
        ic_bycar.setBackgroundResource(R.drawable.black_circle);
        hasGood=false;
    }
//加载底部菜单栏
    public void RenderBottomMenu(){
        if(hasGood || orderPaper.getTotalNum()==0){
            return;
        }
        AppLog.e(TAG, "renderBottomMenu");
        tv_goodnum.setVisibility(View.VISIBLE);
        buy.setBackgroundColor(Color.parseColor("#e53921"));
        ic_bycar.setBackgroundResource(R.drawable.yellowcircke);
        tv_total.setTextColor(Color.parseColor("#e53921"));
        tv_total.setText(orderPaper.getTotalPrice());
        if(orderPaper!=null){
            tv_total.setText(String.valueOf(orderPaper.getTotalNum()));
        }
        hasGood=true;
    }

    //增加商品
    public void addGood(){
        RenderBottomMenu();
        tv_goodnum.setText(String.valueOf(orderPaper.getTotalNum()));
        tv_total.setText(String.format("共￥%s", orderPaper.getTotalPrice()));
        if(orderPaper.getTotalNum()==0){
            resetBottomMenu();
        }
    }


    /**
     * 点击 FoodType 事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position == adapter.getSelectItem()){ return; }
        adapter.setSelectItem(position);
        adapter.notifyDataSetChanged();
        final FoodType foodType = mlist.get(position);
        //点击之后
        getFoodsByType(foodType);
    }

    private void getFoodsByType(final FoodType foodType){
        RequestParams goodParams=new RequestParams();
        goodParams.add("catid",foodType.getCatid());
        HttpUtil.getGoods(new EntityHandler<GoodsModel>(GoodsModel.class) {
            @Override
            public void onReadSuccess(List<GoodsModel> list) {
                goodsAdapter=new GoodsAdapter(getActivity(),list,orderPaper,RestaurantMenuFragment.this);
                goodsLv.setAdapter(goodsAdapter);
                title.setText(foodType.getName());
                if(lv_toptitle.getVisibility()==View.GONE){
                    lv_toptitle.setVisibility(View.VISIBLE);
                }
            }
        },goodParams);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ic_buycar:
               //使用popwindow
                if(orderPaper.getTotalNum()==0){ return; }
                initPopWindow(orderPaper.getTotalNum()+"");
                ic_bycar.setVisibility(View.GONE);
                tv_goodnum.setVisibility(View.GONE);
                content.setAlpha(0.1f);
                //popupWindow.showAsDropDown(content);
                popupWindow.showAtLocation(bottom_view, Gravity.BOTTOM,0,
                        (int)getResources().getDimension(R.dimen.bottom_order_height));
                break;
            case R.id.btn_buy:
                //点击购买按钮
                if(orderPaper.getTotalNum()<=0){
                    ToastUTil.shortT(getActivity(),"购物车中还有没商品");
                    return;
                }
                if(TextUtils.isEmpty(Mapplication.getModel().getUsername())){
                    //还没有登录 弹出对话框立即登录
                    ToastUTil.shortT(getActivity(),"请先登录");
                    return;
                }else{
                    /**
                     * 确认订单
                     */
                    startActivity(BuyActivity.newIntent(getActivity(),orderPaper));
                }
                break;
            case R.id.clear_all_buy:
                TextView tv_goods_num = (TextView) getView().findViewById(R.id.tv_goods_num);//菜单标题文字
                tv_goods_num.setText("0");
                orderPaper.getMap().clear();
                popupWindow.dismiss();
                break;
        }
    }

    private void initPopWindow(String goodnum) {
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_buy_car,null);
        TextView currentNum = (TextView)view.findViewById(R.id.tv_goods_num);
        OrderList ol_order= (OrderList)view.findViewById(R.id.all_buy_goods);
        TextView clearAll = (TextView)view.findViewById(R.id.clear_all_buy);
        clearAll.setOnClickListener(this);
        if(orderPaper!=null){
            ol_order.setMap(orderPaper.getMap());
        }
        currentNum.setText(goodnum);
        currentNum.setVisibility(View.VISIBLE);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //消失时
                content.setAlpha(1.0f);
                ic_bycar.setVisibility(View.VISIBLE);
                tv_goodnum.setVisibility(View.VISIBLE);
            }
        });
        ColorDrawable cd = new ColorDrawable(-0000);
        popupWindow.setAnimationStyle(R.style.MyDialogStyleBottom);
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
    }

    @Override
    public void add(GoodsModel price) { addGood(); }

    @Override
    public void delete(GoodsModel price) { addGood(); }
}
