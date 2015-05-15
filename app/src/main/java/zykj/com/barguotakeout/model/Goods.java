package zykj.com.barguotakeout.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ss on 15-4-29. 要买的商品
 */
public class Goods implements Parcelable{

    private String goodsid;

    private String goodname;

    private String price;

    private int count=0;

    private int position;

    public int getPosition() {return position; }

    public void setPosition(int position) { this.position = position; }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void add(){
        this.count=count+1;
    }

    public boolean delete(){
        if(count<=1){
            count=0;
            return false;
        }else{
            count=count-1;
            return true;
        }
    }

    @Override
    public String toString() {
        return goodsid+goodname+price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goodsid);
        dest.writeString(goodname);
        dest.writeString(price);
        dest.writeInt(count);
    }

    public static final Parcelable.Creator<Goods> CREATOR = new Parcelable.Creator<Goods>() {

        @Override
        public Goods createFromParcel(Parcel source) {
            Goods gs=new Goods();
            gs.goodsid=source.readString();
            gs.goodname=source.readString();
            gs.price=source.readString();
            gs.count=source.readInt();
            return  gs;
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };
}
