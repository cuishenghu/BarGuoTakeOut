package zykj.com.barguotakeout.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ss on 15-4-15.
 * Parceable test
 */
public class ParceAbleTest implements Parcelable {


    public ParceAbleTest(Parcel in){
        id=in.readInt();
        name=in.readString();
        pwd=in.readString();
    }

    private int id;
    private String name;
    private String pwd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(pwd);
    }

    public static final Creator<ParceAbleTest> CREATOR=
            new Creator<ParceAbleTest>(){

                @Override
                public ParceAbleTest createFromParcel(Parcel source) {
                    return new ParceAbleTest(source);
                }

                @Override
                public ParceAbleTest[] newArray(int size) {
                    return new ParceAbleTest[size];
                }
            };

}
