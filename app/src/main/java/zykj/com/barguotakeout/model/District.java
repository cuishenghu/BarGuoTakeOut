package zykj.com.barguotakeout.model;

/**
 * Created by ss on 15-4-21.
 */
public class District {

    private String DistrictId;
    private String DistrictName;

    public String getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(String districtId) {
        DistrictId = districtId;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    @Override
    public String toString() {
         return DistrictName;
    }
}
