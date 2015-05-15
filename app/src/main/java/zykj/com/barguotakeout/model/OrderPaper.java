package zykj.com.barguotakeout.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ss on 15-4-29. 订单也是购物车
 */
public class OrderPaper implements Parcelable{

    private int totalPrice=0;

    private int resid=0;

    private int totalNum=0;//购物车中所有物品的数量

    private Map<String,Goods> map=new HashMap<String,Goods>();

    public Map<String, Goods> getMap() {
        return map;
    }

    public void setMap(Map<String, Goods> map) {
        this.map = map;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }


    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }

    public synchronized Goods getGood(String gid){
        if(map!=null){
            return map.get(gid);
        }
        return null;
    }

    public synchronized void jianGood(Goods good){
        if(map!=null){
           if( map.get(good.getGoodsid())!=null){
               if(good.getCount()!=0)
                   totalNum=totalNum-1;
               if( !good.delete()){
                   map.remove(good.getGoodsid());
               }
            }
        }
    }

    public synchronized void addGood(Goods good){
        if (map.get(good.getGoodsid())!=null){
            good.add();
            totalNum=totalNum+1;
        }
    }

    public synchronized Goods addGood(GoodsModel model){
        Goods goods;
        if(map == null){
            map=new HashMap<String ,Goods>();
        }

        if(map.get(model.getGoodsid())!=null){
            goods =map.get(model.getGoodsid());
        }else{
            goods = dealGoodModel(model);
            map.put(goods.getGoodsid(),goods);
            goods=map.get(model.getGoodsid());
        }
        goods.add();
        totalNum=totalNum+1;
        return goods;
    }

    public synchronized Goods dealGoodModel(GoodsModel goodsModel){
        Goods good=new Goods();
        good.setGoodsid(goodsModel.getGoodsid());
        good.setGoodname(goodsModel.getName());
        good.setPrice(goodsModel.getPrice());
        return good;
    }

    public String getTotalPrice(){
        //获取总价
        double total=0;
        if(map==null){
            return "";
        }
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext()) {
            Goods goods=map.get(iterator.next());
            Double pric=Double.valueOf(goods.getPrice());
            pric=pric*goods.getCount();
            total=total+pric;
        }
        return String.valueOf(total);
    }

    public void clear(){
        totalPrice = 0;
        resid=0;totalNum=0;
        map.clear();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalPrice);
        dest.writeInt(resid);
        dest.writeInt(totalNum);
        dest.writeMap(map);
    }

    public static final Parcelable.Creator<OrderPaper> CREATOR = new Parcelable.Creator<OrderPaper>() {

        @Override
        public OrderPaper createFromParcel(Parcel source) {
            OrderPaper p = new OrderPaper();
            p.totalPrice=source.readInt();
            p.resid=source.readInt();
            p.totalNum=source.readInt();
            p.map=source.readHashMap(Goods.class.getClassLoader());
            return p;
        }

        @Override
        public OrderPaper[] newArray(int size) {
            return new OrderPaper[size];
        }
    };

}
