package zykj.com.barguotakeout.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import java.util.List;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.http.UrlContants;
import zykj.com.barguotakeout.model.Goods;
import zykj.com.barguotakeout.model.GoodsModel;
import zykj.com.barguotakeout.model.OrderPaper;

/**
 * Created by ss on 15-4-28.
 */
public class GoodsAdapter extends BaseAdapter {
    private List<GoodsModel> list;
    private LayoutInflater inflater;
    private OrderCallBack callBack;
    private static final String TAG="GoodsAdapter";

    private OrderPaper paper;

    public GoodsAdapter(Context context,List<GoodsModel> list,OrderPaper paper,OrderCallBack callBack) {
        inflater=LayoutInflater.from(context);
        this.list=list;
        this.paper=paper;
        this.callBack=callBack;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public GoodsModel getItem(int position) {
        if(position > list.size()){
            AppLog.e(TAG,"item is not avaliable");
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            convertView=inflater.inflate(R.layout.item_goods,null);
            holder=new ViewHolder();
            holder.header= (ImageView) convertView.findViewById(R.id.iv_goodheader);
            holder.tv_title= (TextView) convertView.findViewById(R.id.tv_good_title);
            holder.tv_info= (TextView) convertView.findViewById(R.id.tv_good_info);
            holder.tv_price= (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_no_tip= (TextView) convertView.findViewById(R.id.no_tipe);
            holder.tv_order_num= (TextView) convertView.findViewById(R.id.tv_goods_num);
            holder.add= (ImageView) convertView.findViewById(R.id.iv_jia);
            holder.jian= (ImageView) convertView.findViewById(R.id.iv_jian);
            holder.btn_group= (LinearLayout) convertView.findViewById(R.id.button_group);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        final GoodsModel model=this.getItem(position);
        final Goods good=paper.getGood(model.getGoodsid());//已经购买的good

        holder.tv_title.setText(model.getName());//标题

        ImageLoader.getInstance().displayImage(UrlContants.getUrl(model.getPic()),holder.header);//header

        holder.tv_info.setText(String.format("已售%1s份 推荐%2s",model.getSoldnum(),model.getRecommendnum()));

        holder.tv_price.setText(String.format("￥%s",model.getPrice()));

        if(model.getIsavailable()!=null && model.getIsavailable().equals("0")){
            //已卖完
            holder.tv_no_tip.setVisibility(View.VISIBLE);//显示已卖光
            holder.btn_group.setVisibility(View.GONE); //取消"+,-"显示
        }

        //如果购物车中有的话 显示出来 如果没有 不显示 减号
        //加号 增加 要买的数量

        if(good!=null && good.getCount()!=0){
            holder.tv_order_num.setVisibility(View.VISIBLE);
            holder.tv_order_num.setText(String.valueOf(good.getCount()));
            holder.jian.setVisibility(View.VISIBLE);
        }else{
            holder.tv_order_num.setVisibility(View.INVISIBLE);
            holder.jian.setVisibility(View.INVISIBLE);
        }



        holder.jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goods cGood = paper.getMap().get(model.getGoodsid());
                if(good!=null){
                    paper.jianGood(good);
                }
                holder.tv_order_num.setText(String.valueOf(good.getCount()));
                if(callBack != null){
                    callBack.delete(model);
                }
            }

        });

        //点击加号回调方法
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.tv_order_num.getVisibility()== View.INVISIBLE){
                    holder.tv_order_num.setVisibility(View.VISIBLE);
                }
                paper.addGood(model);
                if(callBack!=null){
                    callBack.add(model);//更新ui
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    static class ViewHolder{
        ImageView header;//tou
        TextView tv_title;//biaoti

        TextView tv_info; //xinxi
        TextView tv_price;//jiage

        TextView tv_no_tip;//mai guang tishi

        TextView tv_order_num;//dinggou shuliang

        ImageView add;// tianjia
        ImageView jian;//jianshao

        LinearLayout btn_group;

    }

    public interface OrderCallBack{

        void add(GoodsModel price);//增加一个商品

        void delete(GoodsModel price);//删除一个商品

    }

}
