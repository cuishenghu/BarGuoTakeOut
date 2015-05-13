package zykj.com.barguotakeout.model;

import java.util.List;

/**
 * Created by ss on 15-4-23.
 */
public class FoodType {

    private String catid;//账号


    private String name;//名称

    private String introduction;//介绍

    private List<GoodsModel> goods;

    public List<GoodsModel> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsModel> goods) {
        this.goods = goods;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
