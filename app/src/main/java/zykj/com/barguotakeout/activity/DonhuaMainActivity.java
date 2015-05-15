package zykj.com.barguotakeout.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import zykj.com.barguotakeout.R;

public class DonhuaMainActivity extends Activity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View startView = View
                .inflate(this, R.layout.activity_donhua_main, null);
        setContentView(startView);
        context = this;
        // 渐变
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(1000);
        startView.setAnimation(aa);
        aa.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                redirectto();
            }
        });
    }

    private void redirectto() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        // Intent intent = new Intent(this,B2_ProductdetailsActivity.class);

        startActivity(intent);
        finish();
    }
}
