package zykj.com.barguotakeout.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zykj.com.barguotakeout.Utils.AppLog;

/**
 * Created by ss on 15-4-17.
 */
public  class CommonFragment extends Fragment {
    private  String TAG="";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
         AppLog.i(TAG,"onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.i(TAG,"onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLog.i(TAG,"onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppLog.i(TAG,"onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppLog.i(TAG,"onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        AppLog.i(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        AppLog.i(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        AppLog.i(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        AppLog.i(TAG,"onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppLog.i(TAG,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        AppLog.i(TAG,"onDetach");
    }

    public void setTAG(String tag){
        TAG=tag;
    }
}
