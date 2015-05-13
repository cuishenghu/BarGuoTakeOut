package zykj.com.barguotakeout.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * Created by ss on 15-5-7.
 */
public class AlphaShadow extends  ChangeColorIconWithText {

    private boolean b=false;
    private Paint p;
    private Context context;

    public AlphaShadow(Context context) {
        this(context, null, 0);
    }

    public AlphaShadow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    private void init() {
        int a= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2,context.getResources().getDisplayMetrics());
        p = new Paint();
        p.setAntiAlias(true);
        p.setDither(true);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(a);
    }

    public AlphaShadow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x=getMeasuredWidth()/2;
        float y=getMeasuredHeight()/2;
        if(b){
            canvas.drawCircle(x,y,x/2,p);
        }
    }

    public void drawShadowCircle(boolean b){
        this.b=b;
        this.postInvalidate();
    }
}
