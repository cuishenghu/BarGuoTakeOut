package zykj.com.barguotakeout.model;

/**
 * Created by ss on 15-4-28. 食品具体的
 *"goodsid":"5","name":"水饺","category":"2","cookstyle":"1",
 * "pic":"http:\/\/img4.imgtn.bdimg.com\/it\/u=936263098,1123507482&fm=21&gp=0.jpg",
 * "price":"12","isavailable":"1",
 * "recommendnum":"32","soldnum":"16","introduction":"好吃的水饺","resid":"2"
 */
public class GoodsModel {
    private String goodsid;
    private String name;
    private String category;
    private String cookstyle;
    private String pic;
    private String price;
    private String isavailable;//是否已经售完
    private String recommendnum;//推荐
    private String soldnum;//已售
    private String introduction;
    private String resid;

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCookstyle() {
        return cookstyle;
    }

    public void setCookstyle(String cookstyle) {
        this.cookstyle = cookstyle;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIsavailable() {
        return isavailable;
    }

    public void setIsavailable(String isavailable) {
        this.isavailable = isavailable;
    }

    public String getRecommendnum() {
        return recommendnum;
    }

    public void setRecommendnum(String recommendnum) {
        this.recommendnum = recommendnum;
    }

    public String getSoldnum() {
        return soldnum;
    }

    public void setSoldnum(String soldnum) {
        this.soldnum = soldnum;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }
}
