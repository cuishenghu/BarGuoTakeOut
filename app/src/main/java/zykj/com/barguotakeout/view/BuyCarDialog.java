package zykj.com.barguotakeout.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.adapter.GoodsAdapter;
import zykj.com.barguotakeout.model.Goods;
import zykj.com.barguotakeout.model.GoodsModel;
import zykj.com.barguotakeout.model.OrderPaper;

/**
 * Created by ss on 15-4-29.弹出对话框
 */
public class BuyCarDialog extends LinearLayout{

    private LayoutInflater inflater;

    public BuyCarDialog(Context context) {
        this(context, null, 0);
    }

    public BuyCarDialog(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BuyCarDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BuyCarDialog(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context ctx) {
        this.setBackgroundResource(R.drawable.gray_devide_bt);
        inflater = LayoutInflater.from(ctx);
    }

    public void setMap(Map<String,Goods> maps){
        Collection<Goods> c=maps.values();
        Iterator iterator=c.iterator();
        while (iterator.hasNext()){
            Object key= iterator.next();
            Goods goods= (Goods) key;
            AppLog.i("orderlist", goods.toString());
            RelativeLayout rl_orderitem= (RelativeLayout) inflater.inflate(R.layout.rl_buy_orderitem,null);
            TextView tv_goodname= (TextView) rl_orderitem.findViewById(R.id.tv_buy_goodname);
            TextView tv_price= (TextView) rl_orderitem.findViewById(R.id.tv_buy_price);
            TextView tv_count= (TextView) rl_orderitem.findViewById(R.id.tv_buy_count);

            tv_goodname.setText(goods.getGoodname());
            tv_count.setText(String.format("x%d", goods.getCount()));
            //计算价格
            Double tPrice=goods.getCount()*Double.valueOf( goods.getPrice());
            tv_price.setText(String.format("￥%.1f",tPrice));
            this.addView(rl_orderitem);
        }
    }

    public void setMap(final OrderPaper paper, final GoodsAdapter goodsAdapter, final TextView currentNum){
        Map<String,Goods> maps = paper.getMap();
        Collection<Goods> c=maps.values();
        Iterator iterator=c.iterator();
        while (iterator.hasNext()){
            Object key= iterator.next();
            final Goods goods= (Goods) key;
            AppLog.i("orderlist", goods.toString());
            final RelativeLayout rl_orderitem= (RelativeLayout) inflater.inflate(R.layout.buy_car_orderitem,null);
            TextView tv_goodname= (TextView) rl_orderitem.findViewById(R.id.tv_buy_goodname);
            final TextView tv_price= (TextView) rl_orderitem.findViewById(R.id.tv_buy_price);
            ImageView tv_jian= (ImageView) rl_orderitem.findViewById(R.id.iv_buy_jian);
            final TextView tv_count= (TextView) rl_orderitem.findViewById(R.id.tv_buy_num);
            ImageView tv_jia= (ImageView) rl_orderitem.findViewById(R.id.iv_buy_jia);

            tv_goodname.setText(goods.getGoodname());
            tv_count.setText(String.format("x%d", goods.getCount()));
            //计算价格
            Double tPrice=goods.getCount()*Double.valueOf( goods.getPrice());
            tv_price.setText(String.format("￥%.1f",tPrice));

            tv_jia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    paper.addGood(goods);
                    goodsAdapter.notifyDataSetChanged();
                    tv_count.setText(String.format("x%d", goods.getCount()));
                    Double tPrice=goods.getCount()*Double.valueOf(goods.getPrice());
                    tv_price.setText(String.format("￥%.1f",tPrice));
                    currentNum.setText(paper.getTotalNum()+"");
                }
            });
            tv_jian.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    paper.jianGood(goods);
                    if(goods.getCount()==0){
                        BuyCarDialog.this.removeView(rl_orderitem);
                    }else{
                        tv_count.setText(String.format("x%d", goods.getCount()));
                        Double tPrice=goods.getCount()*Double.valueOf( goods.getPrice());
                        tv_price.setText(String.format("￥%.1f",tPrice));
                    }
                    goodsAdapter.notifyDataSetChanged();
                    currentNum.setText(paper.getTotalNum()+"");
                }
            });
            this.addView(rl_orderitem);
        }
    }
}
