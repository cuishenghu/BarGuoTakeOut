package zykj.com.barguotakeout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by ss on 15-5-7.
 */
public class OrderDetailView  extends LinearLayout{
    public OrderDetailView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderDetailView(Context context) {
        this(context, null, 0);
    }

    public OrderDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {


    }

    public OrderDetailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
