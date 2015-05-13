package zykj.com.barguotakeout.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.model.Order;

/**
 * Created by ss on 15-5-6.
 */
public class MyOderAdapter extends BaseAdapter {

    private List<Order> list;
    private LayoutInflater inflater;

    public MyOderAdapter(Context ctx,List<Order> list){
        inflater=LayoutInflater.from(ctx);
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_morder,null);
            holder=new ViewHolder();
            holder.iv_icon= (ImageView) convertView.findViewById(R.id.iv_oitem_restic);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_status= (TextView) convertView.findViewById(R.id.tv_oitem_status);
            holder.tv_price= (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_address= (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_restaurant_name);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
             Order order=list.get(position);

             holder.iv_icon.setImageResource(R.mipmap.iv_default);
            if(order.getReslogo()!=null){
                ImageLoader.getInstance().displayImage(order.getReslogo(),holder.iv_icon);
            }

            holder.tv_name.setText(order.getResname());
            holder.tv_time.setText(order.getUpdatetime());
        if(order.getOrderprice()!=null) {
            holder.tv_price.setText(String.format("￥%s",order.getOrderprice()));
        }
            holder.tv_status.setText(handlerStatus(Integer.valueOf(order.getStatus())));
        return convertView;
    }

    class ViewHolder {
        TextView tv_name;
       TextView tv_time;
        ImageView iv_icon;
        TextView tv_status;
        TextView tv_price;
        TextView tv_address;
    }

    private String handlerStatus(Integer status){

        String s="";
        switch (status){
            case 0:
                s="等待付款";
                break;
            case 1:
                s="等待餐厅接单";
                break;
            case 2:
                s="正在配送";
                break;
            case 3:
                s="交易成功";
                break;
            case 7:
                s="交易关闭";
                break;
            case 8:
                s="订单取消，等待退款";
                break;
            case 9:
                s="交易关闭";
                break;
        }
        return  s;
    }
}
