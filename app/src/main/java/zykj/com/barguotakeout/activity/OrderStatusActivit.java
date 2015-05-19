package zykj.com.barguotakeout.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.http.HttpErrorHandler;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.model.OrderDetail;
import zykj.com.barguotakeout.model.OrderPaper;
import zykj.com.barguotakeout.model.ResturantModel;
import zykj.com.barguotakeout.view.BuyCarDialog;
import zykj.com.barguotakeout.view.OrderList;

/**
 * Created by ss on 15-5-7.
 */
public class OrderStatusActivit extends CommonActivity implements View.OnClickListener{

    private TextView orderText,orderInfo,ordertime,take_down_one,take_down_two,take_down_three,take_down_four,orderDianpu,orderTimerText;
    private TextView orderPrice,orderAssessInfo,order_ordernum,order_updatetime,order_payway,order_phonenum,order_address,order_allPrice;
    private ImageView order_ok,take_up_one,take_up_two,take_up_three,take_up_four;
    private Button ordercancel,orderphone,orderconfirm,order_next;
    private LinearLayout status_info;
    private RelativeLayout status_shangjia,status_assess,order_button,order_all;
    private OrderList orderGoods;

    private View take_up_end,take_down_end;
    private RequestParams detailParams;

    private AsyncHttpResponseHandler mHttpResponseHandler;
    private Runnable myRunnable;
    public static final String CODE="ordernum";
    private String ordernum;
    private Boolean hasClocks;
    private OrderDetail orderDetail;
    private JSONArray jsonArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        initView();
        initData();

    }

    private void initData() {
        ordernum = getIntent().getStringExtra(CODE);
        if(ordernum!=null){
            requestData();
        }
    }

    private void requestData() {
        HttpUtil.getOrderStatus(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                JSONObject jsonObject = json.getJSONObject("data");
                AppLog.e("-----------------",json.toString());
                OrderDetail detail = JSONObject.parseObject(jsonObject.toString(), OrderDetail.class);
                orderDetail = detail;
                renderOrderDetail(detail);
            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {
                ToastUTil.shortT(OrderStatusActivit.this, json.getString("msg"));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try {
                    String responseString=new String(responseBody, HTTP.UTF_8);
                    JSONObject json = (JSONObject) JSON.parse(responseString);
                    ToastUTil.shortT(OrderStatusActivit.this, json.getString("msg"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        },detailParams);
    }

    private void initView(){
        ordernum = getIntent().getStringExtra(CODE);
        detailParams = new RequestParams();
        detailParams.add(CODE,ordernum);
        status_info = (LinearLayout)findViewById(R.id.order_status_one);//订单计时模块
        order_all = (RelativeLayout)findViewById(R.id.order_top_all);//订单价格提醒
        status_shangjia = (RelativeLayout)findViewById(R.id.order_detail_shangjia);//订单状态
        status_assess = (RelativeLayout)findViewById(R.id.order_detail_assess);//订单状态
        order_button = (RelativeLayout)findViewById(R.id.order_detail_button);//操作按钮

        orderText = (TextView)findViewById(R.id.order_detail_text);//订单状态
        order_ok = (ImageView)findViewById(R.id.order_detail_ok);//订单图标
        order_allPrice = (TextView)findViewById(R.id.order_detail_all);//总价提醒
        orderInfo = (TextView)findViewById(R.id.order_detail_info);//订单信息
        orderTimerText = (TextView)findViewById(R.id.order_time_text);//等待信息
        ordertime = (TextView)findViewById(R.id.order_detail_time);//等待时间
        ordercancel = (Button)findViewById(R.id.order_btn_cancel);//取消订单
        orderphone = (Button)findViewById(R.id.order_btn_phone);//电话催单
        orderconfirm = (Button)findViewById(R.id.order_btn_confirm);//确认收货
        take_up_one = (ImageView)findViewById(R.id.take_up_one);//提交订单
        take_up_two = (ImageView)findViewById(R.id.take_up_two);//餐厅接单
        take_up_three = (ImageView)findViewById(R.id.take_up_three);//配送中
        take_up_four = (ImageView)findViewById(R.id.take_up_four);//已收获
        take_down_one = (TextView)findViewById(R.id.take_down_one);//提交订单
        take_down_two = (TextView)findViewById(R.id.take_down_two);//餐厅接单
        take_down_three = (TextView)findViewById(R.id.take_down_three);//配送中
        take_down_four = (TextView)findViewById(R.id.take_down_four);//已收获
        orderDianpu = (TextView)findViewById(R.id.order_detail_dianpu);//店铺名称
        orderGoods = (OrderList)findViewById(R.id.all_buy_goods);//已购商品
        orderPrice = (TextView)findViewById(R.id.order_detail_price);//订单总价
        order_next = (Button)findViewById(R.id.order_next);//再来一单
        orderAssessInfo = (TextView)findViewById(R.id.order_assess_content);//我的评价

        order_ordernum = (TextView)findViewById(R.id.order_detail_ordernum);//订单号
        order_updatetime = (TextView)findViewById(R.id.order_detail_updatetime);//提交时间
        order_payway = (TextView)findViewById(R.id.order_detail_payway);//支付方式
        order_phonenum = (TextView)findViewById(R.id.order_detail_phonenum);//收货电话
        order_address = (TextView)findViewById(R.id.order_detail_address);//收货地址

        take_up_end = findViewById(R.id.take_up_end);//删除
        take_down_end = findViewById(R.id.take_down_end);//删除
    }

    private void renderOrderDetail(OrderDetail detail){
        jsonArray = (JSONArray)JSON.parse(detail.getGoodsdetail());
        orderGoods.setMap(jsonArray);
        orderDianpu.setText(detail.getResname());
        orderGoods.setMap(new OrderPaper().getMap());
        orderPrice.setText(detail.getOrderprice());
        orderPrice.setText(String.format("￥%.1f", Double.valueOf(detail.getOrderprice())));
        order_ordernum.setText("订单号码："+detail.getOrdernum());
        order_updatetime.setText("订单时间："+detail.getUpdatetime());
        order_payway.setText("支付方式："+detail.getPayway());
        order_phonenum.setText("手机号码："+detail.getPhonenum());
        order_address.setText("收餐地址："+detail.getAddress());
        order_next.setOnClickListener(new OnNextClickListener());
        orderphone.setOnClickListener(new OnPhoneClickListener());
        renderOrderStatus(detail.getStatus());
    }

    private void renderOrderStatus(String detail){
        switch (detail){
            case "0":
                /*0->提交订单，等待付款*/
                order_ok.setBackgroundResource(R.drawable.red_circle_order);
                order_ok.setImageResource(R.mipmap.timer);//订单状态图标
                orderText.setText("提交订单，等待付款");//订单状态
                orderInfo.setVisibility(View.GONE);//订单信息(红色提醒)
                order_all.setVisibility(View.VISIBLE);//总价
                order_allPrice.setText("总价：" + orderDetail.getOrderprice() + "元");
                status_info.setVisibility(View.VISIBLE);//计时模块
                orderTimerText.setText("支付剩余时间");
                timeerCount();//15分钟倒计时
                orderconfirm.setVisibility(View.VISIBLE);
                orderconfirm.setText("付款");//付款按钮
                orderconfirm.setOnClickListener(new OnPayClickListener());//付款
                ordercancel.setOnClickListener(new OnCancelClickListener());//取消订单
                take_up_two.setBackgroundResource(R.drawable.gray_circle_order);
                take_down_one.setTextColor(R.color.greend);
                break;
            case "1":
                /*1->付款成功，等待餐厅接单*/
                orderText.setText("付款成功，等待餐厅接单");//订单状态
                orderInfo.setVisibility(View.GONE);//订单信息(红色提醒)
                order_all.setVisibility(View.VISIBLE);//预计接单时间
                order_allPrice.setText("预计接单 14点37分");
                status_info.setVisibility(View.VISIBLE);//计时模块
                orderTimerText.setText("已等待");
                timeerCount();//15分钟倒计时
                ordercancel.setOnClickListener(new OnCancelClickListener());//取消订单
                take_up_two.setBackgroundResource(R.drawable.gray_circle_order);
                take_down_one.setTextColor(R.color.greend);
                break;
            case "2":
                /*2->餐厅已接单，美食制作中*/
                order_ok.setBackgroundResource(R.drawable.red_circle_order);
                order_ok.setImageResource(R.mipmap.timer);//订单状态图标
                orderText.setText("餐厅已接单，美食制作中");//订单状态
                orderInfo.setText("  收到美食了吗，如果还未收到，\n  记得催单哦");//订单信息(红色提醒)
                orderphone.setVisibility(View.VISIBLE);
                orderconfirm.setVisibility(View.VISIBLE);
                orderconfirm.setOnClickListener(new OnConfirmClickListener());//确认收货
                ordercancel.setOnClickListener(new OnCancelClickListener());//取消订单
                take_down_two.setTextColor(R.color.greend);
                break;
            case "3":
                /*3->买家确认收货,交易成功*/
                orderText.setText("订单完成");//订单状态
                orderInfo.setText("  感谢你使用巴国外卖，欢迎你再次订餐");//订单信息(红色提醒)
                orderInfo.setTextColor(R.color.font_status);
                orderInfo.setBackgroundResource(R.color.white);
                ordercancel.setText("订单投诉");
                ordercancel.setOnClickListener(new OnLodgeClickListener());//订单投诉
                take_up_three.setBackgroundResource(R.drawable.take_out_green);
                take_up_four.setBackgroundResource(R.drawable.take_out_green);
                take_down_four.setTextColor(R.color.greend);
                break;
            case "4":
                /*4->正在配送*/
                order_ok.setBackgroundResource(R.drawable.red_circle_order);
                order_ok.setImageResource(R.mipmap.timer);//订单状态图标
                orderText.setText("正在配送");//订单状态
                orderInfo.setText("  收到美食了吗，如果还未收到，\n  记得催单哦");//订单信息(红色提醒)
                orderphone.setVisibility(View.VISIBLE);
                orderconfirm.setVisibility(View.VISIBLE);
                orderconfirm.setOnClickListener(new OnConfirmClickListener());//确认收货
                ordercancel.setOnClickListener(new OnCancelClickListener());//取消订单
                take_up_three.setBackgroundResource(R.drawable.take_out_green);
                take_down_three.setTextColor(R.color.greend);
                break;
            case "7":
                /*7->未付款,订单取消，交易关闭*/
                order_ok.setBackgroundResource(R.drawable.red_circle_order);
                order_ok.setImageResource(R.mipmap.takeout_cancel);//订单状态图标
                orderText.setText("订单取消，交易关闭");//订单状态
                orderInfo.setVisibility(View.VISIBLE);
                orderInfo.setText("  支付超时，你的订单已经取消");//订单信息(红色提醒)
                order_allPrice.setVisibility(View.GONE);
                status_info.setVisibility(View.GONE);
                orderconfirm.setVisibility(View.GONE);
                ordercancel.setText("订单投诉");
                ordercancel.setOnClickListener(new OnLodgeClickListener());//订单投诉
                take_up_three.setVisibility(View.GONE);
                take_down_three.setVisibility(View.GONE);
                take_up_end.setVisibility(View.GONE);
                take_down_end.setVisibility(View.GONE);

                take_up_two.setBackgroundResource(R.drawable.red_circle_order);
                take_up_two.setImageResource(R.mipmap.takeout_cancel);//订单状态图标
                take_down_two.setText("订单取消");
                take_up_four.setBackgroundResource(R.drawable.red_circle_order);
                take_down_four.setText("交易关闭");
                break;
            case "8":
                /*8->已付款，订单取消，等待退款*/
                order_ok.setBackgroundResource(R.drawable.red_circle_order);
                order_ok.setImageResource(R.mipmap.takeout_cancel);//订单状态图标
                orderText.setText("订单取消，等待退款");//订单状态
                orderInfo.setText("  你的订单已经取消\n  已确认退款，正在为您退款");//订单信息(红色提醒)
                ordercancel.setText("订单投诉");
                ordercancel.setOnClickListener(new OnLodgeClickListener());//订单投诉
                take_up_three.setVisibility(View.GONE);
                take_down_three.setVisibility(View.GONE);
                take_up_end.setVisibility(View.GONE);
                take_down_end.setVisibility(View.GONE);

                take_up_two.setBackgroundResource(R.drawable.red_circle_order);
                take_up_two.setImageResource(R.mipmap.takeout_cancel);//取消订单状态图标
                take_down_two.setText("订单取消");
                take_down_four.setText("交易关闭");
                break;
            case "9":
                /*9->退款成功，交易关闭*/
                order_ok.setBackgroundResource(R.drawable.red_circle_order);
                orderText.setText("退款成功，交易关闭");//订单状态
                orderInfo.setText("  退款成功，交易关闭，欢迎下次光临");//订单信息(红色提醒)
                ordercancel.setText("订单投诉");
                ordercancel.setOnClickListener(new OnLodgeClickListener());//订单投诉
                take_up_three.setVisibility(View.GONE);
                take_down_three.setVisibility(View.GONE);
                take_up_end.setVisibility(View.GONE);
                take_down_end.setVisibility(View.GONE);

                take_up_two.setBackgroundResource(R.drawable.red_circle_order);
                take_up_two.setImageResource(R.mipmap.takeout_cancel);//订单状态图标
                take_down_two.setText("订单取消");
                take_up_four.setBackgroundResource(R.drawable.red_circle_order);
                take_down_four.setText("交易关闭");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.order_btn_cancel:
                break;
            case R.id.order_btn_phone:
                break;
            case R.id.order_btn_confirm:
                break;
            case R.id.order_next:
                Intent intent = new Intent(OrderStatusActivit.this,RestaurantDetail.class);
                intent.putExtra(RestaurantDetail.KEY,new ResturantModel());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void back(View v){
        finish();
    }

    class OnNextClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            /*再来一单*/
            ResturantModel model = new ResturantModel();
            model.setResid(orderDetail.getResid());
            model.setName(orderDetail.getResname());
            //启动餐厅详情页面
            Intent intent=new Intent(OrderStatusActivit.this, RestaurantDetail.class);
            intent.putExtra(RestaurantDetail.KEY,model);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
    class OnCancelClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            /*取消订单*/
            HttpUtil.cancelOrder(httpResponseHandler(),detailParams);
        }
    }
    class OnPhoneClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            /*电话催单*/
            Intent pIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + orderDetail.getResphone()));
            startActivity(pIntent);
        }
    }
    class OnConfirmClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            /*确认收货*/
            HttpUtil.confirmOrder(new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String responseString=new String(responseBody, HTTP.UTF_8);
                        JSONObject json = (JSONObject) JSON.parse(responseString);
                        if("1".equals(json.getString("status"))){
                            ToastUTil.shortT(OrderStatusActivit.this,"取消成功");
                        }else{
                            ToastUTil.shortT(OrderStatusActivit.this,json.getString("msg"));
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    try {
                        String responseString=new String(responseBody, HTTP.UTF_8);
                        JSONObject json = (JSONObject) JSON.parse(responseString);
                        ToastUTil.shortT(OrderStatusActivit.this,json.getString("msg"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            },detailParams);

        }
    }
    class OnPayClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            /*付款*/
            Intent payIntent=new Intent();
            payIntent.setClass(OrderStatusActivit.this, ConfirmOrder.class);
            payIntent.putExtra("mobile", orderDetail.getPhonenum());
            payIntent.putExtra("address",orderDetail.getAddress());
            payIntent.putExtra("payType",Integer.valueOf(orderDetail.getPayway()));
            payIntent.putExtra("msg",orderDetail.getRemark());
            payIntent.putExtra("price",Integer.valueOf(orderDetail.getOrderprice()));
            payIntent.putExtra("ordernum",orderDetail.getOrdernum());
            payIntent.putExtra("orderdetail", orderDetail.getGoodsdetail());
            startActivity(payIntent);
        }
    }
    class OnLodgeClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            /*订单投诉*/
            Intent complaintIntent = new Intent(OrderStatusActivit.this, TouSuChuLiActivity.class);
            startActivity(complaintIntent);
        }
    }

    private void timeerCount(){
        if(myRunnable == null) {
            final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("zh_CN"));
            final Handler handler = new Handler();
            myRunnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        long residueTime = (900000 - (new Date().getTime() - df.parse(orderDetail.getUpdatetime()).getTime())) / 1000;
                        AppLog.e("residueTime*******************", residueTime + "");
                        ordertime.setText(residueTime / 60 + "分" + residueTime % 60 + "秒");
                        handler.postDelayed(this, 1000);
                        if (residueTime < 0) {
                            ordertime.setText("失败!");
                            HttpUtil.cancelOrder(httpResponseHandler(), detailParams);
                            handler.removeCallbacks(this);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            };
            handler.post(myRunnable);
        }
    }

    private AsyncHttpResponseHandler httpResponseHandler(){
        if(mHttpResponseHandler == null) {
            mHttpResponseHandler = new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String responseString = new String(responseBody, HTTP.UTF_8);
                        JSONObject json = (JSONObject) JSON.parse(responseString);
                        JSONObject jsonObject = json.getJSONObject("data");
                        ToastUTil.shortT(OrderStatusActivit.this, "取消成功");
                        renderOrderStatus(jsonObject.getString("status"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    try {
                        String responseString = new String(responseBody, HTTP.UTF_8);
                        JSONObject json = (JSONObject) JSON.parse(responseString);
                        ToastUTil.shortT(OrderStatusActivit.this, json.getString("msg"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        return mHttpResponseHandler;
    }
}
