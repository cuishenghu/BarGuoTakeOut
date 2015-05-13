package zykj.com.barguotakeout;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.loopj.android.http.RequestParams;

import junit.framework.Test;

import org.apache.http.Header;

import zykj.com.barguotakeout.Utils.AppLog;
import zykj.com.barguotakeout.fragment.CommonProgressFragment;
import zykj.com.barguotakeout.http.HttpErrorHandler;
import zykj.com.barguotakeout.http.HttpUtil;


public class TestActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG=TestActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private TextView textView;
    private void initView() {
        Button btn_pic= (Button) findViewById(R.id.test_pic);
        Button btn_happy= (Button) findViewById(R.id.test_happy);
        Button btn_gift= (Button) findViewById(R.id.test_gift);
        Button btn_dialog= (Button) findViewById(R.id.btn_dialog);
         textView = (TextView) findViewById(R.id.tv_showjoke);
        btn_pic.setOnClickListener(this);
        btn_happy.setOnClickListener(this);
        btn_gift.setOnClickListener(this);
        btn_dialog.setOnClickListener(this);

        SliderLayout sliderLayout= (SliderLayout) findViewById(R.id.slider);

        String url1="http://img1.gtimg.com/news/pics/hv1/159/199/1820/118396404.png";
        String url2="http://img1.gtimg.com/news/pics/hv1/234/74/1820/118364604.jpg";
        TextSliderView textSliderView = new TextSliderView(this);
        textSliderView.image(url1);
        TextSliderView textSliderView2=new TextSliderView(this);
        textSliderView2.image(url2);
        sliderLayout.addSlider(textSliderView);
        sliderLayout.addSlider(textSliderView2);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    HttpErrorHandler jokeHandler=new HttpErrorHandler() {
        @Override
        public void onRecevieSuccess(JSONObject json) {
            JSONArray jsonArray = json.getJSONArray("data");
           for (int i=0;i<jsonArray.size();i++){
               JSONObject j=jsonArray.getJSONObject(i);
               String content=j.getString("content");
               textView.setText(content);
           }
        }

        @Override
        public void onRecevieFailed(String status, JSONObject json) {

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    };

    HttpErrorHandler indexImageHandler = new HttpErrorHandler() {
        @Override
        public void onRecevieSuccess(JSONObject json) {
            JSONArray data = json.getJSONArray("data");
            Toast.makeText(TestActivity.this,data.toJSONString(),Toast.LENGTH_LONG).show();

        }

        @Override
        public void onRecevieFailed(String status, JSONObject json) {
            AppLog.e(TAG,json.toJSONString());
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_pic:
                HttpUtil.indexPicUrl(indexImageHandler);
                break;
            case R.id.test_happy:
                RequestParams params = new RequestParams();
                params.add("page","1");
                params.add("num","3");
                HttpUtil.dailyJoke(jokeHandler,params);
                break;
            case R.id.test_gift:
                RequestParams gitfParam = new RequestParams();
                gitfParam.add("page","1");
                gitfParam.add("num","3");
                HttpUtil.gifts(indexImageHandler,gitfParam);
                break;
            case R.id.btn_dialog:
                CommonProgressFragment.getInstance("测试").show(getSupportFragmentManager(),"progress");
                break;

        }
    }



}
