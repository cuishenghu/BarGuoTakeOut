package zykj.com.barguotakeout.model;

/**
 * Created by ss on 15-4-23.
 */
public class CategoryModel {

    private String[] title=new String[]{
            "默认排序","已通过认证","距离最近","销量最大","评价最高","价位高到底","价位低到高"
    };

    private String[] resid=new String[]{

    };

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[] getResid() {
        return resid;
    }

    public void setResid(String[] resid) {
        this.resid = resid;
    }
}
