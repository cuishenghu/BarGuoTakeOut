package zykj.com.barguotakeout.model;

/**
 * Created by ss on 15-4-21.
 */
public class Address {

    private String provinceName;
    private String provinceId;


    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public String toString() {
        return provinceName;
    }
}
