package zykj.com.barguotakeout.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import zykj.com.barguotakeout.Mapplication;
import zykj.com.barguotakeout.R;

/**
 * Created by ss on 15-4-17.
 *
 */
public class CommonLoadFragment extends CommonFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpTopBar();
    }

    public void setUpTopBar(){

        View view=getView().findViewById(R.id.top_bar);
        if(view == null){
            throw new RuntimeException("id is error");
        }

        TextView tv_address= (TextView) view.findViewById(R.id.index_title);

        if(tv_address== null){
            throw new RuntimeException("id is error");
        }

        tv_address.setText(Mapplication.getModel().getAddress());

    }
}
