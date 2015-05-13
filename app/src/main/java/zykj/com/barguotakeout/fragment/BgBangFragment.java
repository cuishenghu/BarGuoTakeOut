package zykj.com.barguotakeout.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zykj.com.barguotakeout.R;

/**
 * Created by ss on 15-5-5.
 */
public class BgBangFragment extends CommonFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_baguobang,container,false);
    }
}
