package zykj.com.barguotakeout.model;

/**
 * Created by ss on 15-4-22. 请求排序的列表
 */
public class RequestDataType {

    private Integer resicid;

    private String title;

    private Integer requesttype;

    private Integer rightPic;

    public Integer getResicid() {
        return resicid;
    }

    public void setResicid(Integer resicid) {
        this.resicid = resicid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(Integer requesttype) {
        this.requesttype = requesttype;
    }

    public Integer getRightPic() {
        return rightPic;
    }

    public void setRightPic(Integer rightPic) {
        this.rightPic = rightPic;
    }
}
