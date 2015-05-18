package zykj.com.barguotakeout.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ss on 15-5-7.
 */
public class OrderDetail implements Parcelable {
    private String address;
    private String comment;
    private String goodsdetail;
    private String orderid;
    private String ordernum;
    private String orderprice;
    private String payway;
    private String phonenum;
    private String realname;
    private String remark;
    private String resid;
    private String resname;
    private String resphone;
    private String resreceivetime;
    private String status;
    private String updatetime;
    private String userid;
    private String wanttime;
    private Boolean hasClocks;

    public Boolean getHasClocks(){ return hasClocks; }

    public void setHasClocks(Boolean hasClocks){ this.hasClocks = hasClocks; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getGoodsdetail() {
        return goodsdetail;
    }

    public void setGoodsdetail(String goodsdetail) {
        this.goodsdetail = goodsdetail;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(String orderprice) {
        this.orderprice = orderprice;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getResname() {
        return resname;
    }

    public void setResname(String resname) {
        this.resname = resname;
    }

    public String getResphone() {
        return resphone;
    }

    public void setResphone(String resphone) {
        this.resphone = resphone;
    }

    public String getResreceivetime() {
        return resreceivetime;
    }

    public void setResreceivetime(String resreceivetime) {
        this.resreceivetime = resreceivetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getWanttime() {
        return wanttime;
    }

    public void setWanttime(String wanttime) {
        this.wanttime = wanttime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
