package zykj.com.barguotakeout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.model.ResturantModel;

/**
 * Created by ss on 15-4-22.
 */
public class RestaurantAdapter extends BaseAdapter {

    private List<ResturantModel> list;
    private LayoutInflater inflater;

    public RestaurantAdapter(Context context ,List<ResturantModel> list) {
        this.list=list;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ResturantModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResturantModel model=list.get(position);
         ViewHolder holder;
        if(convertView == null ){
            holder =new ViewHolder();
            convertView =inflater.inflate(R.layout.restaurant_item,null);
            holder.ic_restaurant= (ImageView) convertView.findViewById(R.id.iv_restaurant_ic);
            holder.tv_restname= (TextView) convertView.findViewById(R.id.tv_restaurant_name);
            holder.tv_beginprice= (TextView) convertView.findViewById(R.id.tv_beginprice);
            holder.tv_address= (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_have= (TextView) convertView.findViewById(R.id.tv_have_sold);
            holder.tv_distance= (TextView) convertView.findViewById(R.id.tv_distance);
            holder.rela_onlinepay= (RelativeLayout) convertView.findViewById(R.id.pay_online);
            holder.ratingBar= (RatingBar) convertView.findViewById(R.id.item_rating);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(model.getLogo(),holder.ic_restaurant);

        holder.tv_restname.setText(model.getName());//名
        holder.tv_beginprice.setText(String.format("起送价￥%s",model.getBegindeliveryprice()));
        if(model.getDistance()!=null){
            double distance=Double.valueOf(model.getDistance());//距离单位是米
            distance=distance/1000;
            holder.tv_distance.setText(String.format("%.1f公里",distance));
        }

        holder.tv_address.setText(String.format("地址:%s",model.getAddressname()));
        holder.tv_have.setText(String.format("已售%s单",model.getSoldcount()));

        if(model.getIsonlinepay().equals("1")){
            holder.rela_onlinepay.setVisibility(View.VISIBLE);
        }else{
            holder.rela_onlinepay.setVisibility(View.GONE);
        }

        holder.ratingBar.setRating(Integer.valueOf(model.getTotalcredit()));


        return convertView;
    }

    class ViewHolder{
        ImageView ic_restaurant; //餐厅logo
        TextView tv_restname;//餐厅名
        TextView tv_have;//餐厅已交易量
        TextView tv_address;//餐厅地址
        TextView tv_beginprice;//起送价

        TextView tv_distance;//距离

        RelativeLayout rela_onlinepay;
        RatingBar ratingBar;//星级评分
    }
}
