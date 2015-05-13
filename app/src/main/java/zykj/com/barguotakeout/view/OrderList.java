package zykj.com.barguotakeout.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.model.Goods;

/**
 * Created by ss on 15-5-4.
 */
public class OrderList extends LinearLayout {

    private LayoutInflater inflater;

    public OrderList(Context context) {
        this(context, null, 0);
    }

    public OrderList(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public OrderList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
            AppLog.i("orderlist",goods.toString());
            RelativeLayout rl_orderitem= (RelativeLayout) inflater.inflate(R.layout.rl_buy_orderitem,null);
            TextView tv_goodname= (TextView) rl_orderitem.findViewById(R.id.tv_buy_goodname);
            TextView tv_count= (TextView) rl_orderitem.findViewById(R.id.tv_buy_count);
            TextView tv_price= (TextView) rl_orderitem.findViewById(R.id.tv_buy_price);
            tv_goodname.setText(goods.getGoodname());
            tv_count.setText(String.format("x%d", goods.getCount()));
            //计算价格
            Double tPrice=goods.getCount()*Double.valueOf(goods.getPrice());
            tv_price.setText(String.format("￥%.1f",tPrice));
            this.addView(rl_orderitem);
        }
    }

}
