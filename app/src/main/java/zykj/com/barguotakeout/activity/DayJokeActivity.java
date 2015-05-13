package zykj.com.barguotakeout.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.adapter.JokeAdapter;
import zykj.com.barguotakeout.http.EntityHandler;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.model.JokeEntity;

/**
 * Created by ss on 15-4-17.
 */
public class DayJokeActivity extends CommonActivity{

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTag(DayJokeActivity.class.getSimpleName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        initView();
        requestData();

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.joke_lv);
    }

    private void requestData() {
        final RequestParams params=new RequestParams();
        params.add("page","1");
        params.add("num","5");
        HttpUtil.dailyJoke(new EntityHandler<JokeEntity>(JokeEntity.class) {
            @Override
            public void onReadSuccess(List<JokeEntity> list) {
                //请求成功
               JokeAdapter jokeAdapter= new JokeAdapter(DayJokeActivity.this,list);
                listView.setAdapter(jokeAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);

            }
        },params);
    }

    public void back(View v){
        finish();
    }


}
