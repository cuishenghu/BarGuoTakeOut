package zykj.com.barguotakeout.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.model.FoodType;

/**
 * Created by ss on 15-4-23.
 */
public class FoodTypeAdapter extends BaseAdapter{

    private List<FoodType> list;
    private LayoutInflater inflater;
    private int selectItem=-1;

    public FoodTypeAdapter(Context context,List<FoodType> list) {

        inflater=LayoutInflater.from(context);
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FoodType getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView == null){
            convertView= inflater.inflate(R.layout.item_restmenu_category,null);
            holder=new ViewHolder();
            holder.button= (TextView) convertView.findViewById(R.id.tv_restaurant_name);
            holder.leftImage= (ImageView) convertView.findViewById(R.id.iv_click_tip);
            holder.con= (RelativeLayout) convertView.findViewById(R.id.layout_con);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if(getItem(position)!=null ){
        holder.button.setText(getItem(position).getName());}
        else{
            holder.button.setText("未知");
        }
        if(position == selectItem){
            //绘制selecItem
            holder.button.setTextColor(Color.parseColor("#e53921"));
            if(holder.leftImage.getVisibility()==View.GONE){
                holder.leftImage.setVisibility(View.VISIBLE);
            }
            holder.con.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{
            holder.button.setTextColor(Color.parseColor("#000000"));
            holder.leftImage.setVisibility(View.GONE);
            holder.con.setBackgroundColor(Color.parseColor("#ECEDF1"));
        }
        return convertView;
    }

    public void setSelectItem(int position) {
        if(position > list.size()){
            throw  new RuntimeException("select item is not avilable");
        }
        selectItem=position;
    }

    public int getSelectItem(){
        return selectItem;
    }

    static class ViewHolder{
        TextView button;
        ImageView leftImage;
        RelativeLayout con;
    }
}
