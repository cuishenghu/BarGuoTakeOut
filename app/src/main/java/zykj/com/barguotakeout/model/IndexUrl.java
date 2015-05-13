package zykj.com.barguotakeout.model;

import zykj.com.barguotakeout.http.UrlContants;

/**
 * Created by ss on 15-4-17.
 */
public class IndexUrl {
    private String id;
    private String adurl;
    private String linkurl;

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdurl() {
        return UrlContants.BASE_URL+adurl;
    }

    public void setAdurl(String adurl) {
        this.adurl = adurl;
    }


}
