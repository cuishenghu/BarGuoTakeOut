package zykj.com.barguotakeout.model;

/**
 * Created by ss on 15-5-7.
 * resname":"狗不理包子s","reslogo":","status":"0","updatetime":"2015-05-07 09:13:58","orderprice":"12"}]}
 */
public class Order {
    private String ordernum;
    private String resname;
    private String reslogo;
    private String status;
    private String updatetime;
    private String orderprice;

    public String getResname() {
        return resname;
    }

    public void setResname(String resname) {
        this.resname = resname;
    }

    public String getReslogo() {
        return reslogo;
    }

    public void setReslogo(String reslogo) {
        this.reslogo = reslogo;
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

    public String getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(String orderprice) {
        this.orderprice = orderprice;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }
}
