package zykj.com.barguotakeout.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by ss on 15-4-28.
 */
public class ResturantMenuList extends ListView {
    public ResturantMenuList(Context context) {
        super(context);
    }

    public ResturantMenuList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResturantMenuList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ResturantMenuList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
