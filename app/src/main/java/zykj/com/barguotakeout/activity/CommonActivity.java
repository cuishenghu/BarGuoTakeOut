package zykj.com.barguotakeout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import zykj.com.barguotakeout.Utils.AppLog;

/**
 * Created by ss on 15-4-14.
 */
public class CommonActivity extends FragmentActivity {

    private String TAG="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.i(TAG,"onCreate");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        AppLog.i(TAG,"onRestoreInstanceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppLog.i(TAG,"onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppLog.i(TAG,"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppLog.i(TAG,"onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppLog.i(TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppLog.i(TAG,"onStop");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppLog.i(TAG,"onActivityResult");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        AppLog.i(TAG,"onNewIntent");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        AppLog.i(TAG,"onSaveInstanceState");
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        AppLog.i(TAG,"onAttachFragment");
    }

    public void setTag(String str){
        this.TAG=str;
    }
}
