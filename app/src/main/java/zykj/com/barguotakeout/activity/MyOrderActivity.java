package zykj.com.barguotakeout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.adapter.MyOderAdapter;
import zykj.com.barguotakeout.http.EntityHandler;
import zykj.com.barguotakeout.http.HttpErrorHandler;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.model.Order;
import zykj.com.barguotakeout.model.OrderDetail;

/**
 * Created by ss on 15-5-4. 查看我的订单
 */
public class MyOrderActivity extends CommonActivity implements AdapterView.OnItemClickListener {

    private PullToRefreshListView lv_order;
    private RequestParams params;

    private MyOderAdapter adapter;
    private static final int NUM=10;
    private List<Order> list;

    private Integer page=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        initView();
        requestData();
    }

    private void requestData() {
        if(params==null){
            params=new RequestParams();
        }
        page=1;
        params.add("username", Mapplication.getModel().getUsername());
        params.put("num", NUM);
        params.put("page",page);
        HttpUtil.getAllOrder(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response=new String(responseBody);
                JSONObject json = (JSONObject) JSON.parse(response);
                if(json.getString("status").equals("1")){
                JSONArray array=json.getJSONArray("data");
                list = JSONArray.parseArray(array.toString(), Order.class);
                AppLog.i("orderactivity",array.toJSONString());
                adapter=new MyOderAdapter(MyOrderActivity.this, list);
                lv_order.setAdapter(adapter);
                page=page+1;}else{
                    ToastUTil.shortT(MyOrderActivity.this,json.getString("msg"));
                }
                if(lv_order.isRefreshing()){
                    lv_order.onRefreshComplete();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ToastUTil.shortT(MyOrderActivity.this,"网络连接错误");
            }
        },params);
    }

    private void initView() {
        lv_order = (PullToRefreshListView) findViewById(R.id.lv_myorder_order);
        lv_order.setMode(PullToRefreshBase.Mode.BOTH);
        lv_order.setOnItemClickListener(this);
        lv_order.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉加载更多
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上啦加载更多
                nextPage();
            }
        });
    }

    public void back(View v){
        finish();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //订单详情界面
        Order order= (Order) adapter.getItem(position-1);
        Intent intent=new Intent(MyOrderActivity.this, OrderStatusActivit.class);
        if(order.getOrdernum()!=null){
            intent.putExtra("ordernum",order.getOrdernum());
        }
        startActivity(intent);
    }

    public void nextPage(){
        if(params==null){
            params=new RequestParams();
        }

        params.add("username", Mapplication.getModel().getUsername());
        params.put("num", NUM);
        params.put("page",page);
        HttpUtil.getAllOrder(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response=new String(responseBody);
                JSONObject json = (JSONObject) JSON.parse(response);
                if(json.getString("status").equals("1")){
                JSONArray array=json.getJSONArray("data");
                List<Order> list2 = JSONArray.parseArray(array.toString(), Order.class);
                list.addAll(list2);
                adapter.notifyDataSetChanged();
                page=page+1;}
                else{
                    ToastUTil.shortT(MyOrderActivity.this,json.getString("msg"));
                }
                lv_order.onRefreshComplete();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(isDestroyed()){
                    return;
                }
                ToastUTil.shortT(MyOrderActivity.this,"网络连接错误");
                lv_order.onRefreshComplete();
            }
        },params);
    }


}
