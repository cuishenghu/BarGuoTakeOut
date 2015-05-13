package zykj.com.barguotakeout.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ss on 15-4-22.
 *
 * 餐厅列表
 *
 */
public class ResturantModel implements Parcelable{

    public ResturantModel(){}


    private String resid; //商铺id
    private String name;
    private String logo;
    private String addressname;
    private String longtitude;
    private String totalcredit;
    private String isonlinepay;
    private String deliverytime; //送达时间
    private String businesshours; //营业时间
    private String introduction; //介绍
    private String isopen;
    private String distance; //距离
    private String soldcount; //已售出

    private String start;
    private String end;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }



    private String begindeliveryprice;//起送价格

    private String deliveryprice;

    private List<RestActivities> activites;


    public String getDeliveryprice() {
        return deliveryprice;
    }

    public void setDeliveryprice(String deliveryprice) {
        this.deliveryprice = deliveryprice;
    }

    public String getSoldcount() {
        return soldcount;
    }

    public void setSoldcount(String soldcount) {
        this.soldcount = soldcount;
    }



    public List<RestActivities> getActivites() {
        return activites;
    }

    public void setActivites(List<RestActivities> activites) {
        this.activites = activites;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddressname() {
        return addressname;
    }

    public void setAddressname(String addressname) {
        this.addressname = addressname;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getTotalcredit() {
        return totalcredit;
    }

    public void setTotalcredit(String totalcredit) {
        this.totalcredit = totalcredit;
    }

    public String getBegindeliveryprice() {
        return begindeliveryprice;
    }

    public void setBegindeliveryprice(String begindeliveryprice) {
        this.begindeliveryprice = begindeliveryprice;
    }

    public String getIsonlinepay() {
        return isonlinepay;
    }

    public void setIsonlinepay(String isonlinepay) {
        this.isonlinepay = isonlinepay;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getBusinesshours() {
        return businesshours;
    }

    public void setBusinesshours(String businesshours) {
        this.businesshours = businesshours;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIsopen() {
        return isopen;
    }

    public void setIsopen(String isopen) {
        this.isopen = isopen;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ResturantModel(Parcel in){
        resid=in.readString();
        name=in.readString();
        logo=in.readString();
        addressname=in.readString();
        totalcredit=in.readString();
        isonlinepay=in.readString();
        begindeliveryprice=in.readString();
        deliveryprice=in.readString();
        deliverytime=in.readString();
        introduction=in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resid);
        dest.writeString(name);
        dest.writeString(logo);
        dest.writeString(addressname);
        dest.writeString(totalcredit);
        dest.writeString(isonlinepay);
        dest.writeString(begindeliveryprice);
        dest.writeString(deliveryprice);
        dest.writeString(deliverytime);
        dest.writeString(introduction);
    }

    public static final Creator<ResturantModel> CREATOR=new Creator<ResturantModel>() {
        @Override
        public ResturantModel createFromParcel(Parcel source) {
            return new ResturantModel(source);
        }

        @Override
        public ResturantModel[] newArray(int size) {
            return new ResturantModel[0];
        }
    };
}
