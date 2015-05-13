package zykj.com.barguotakeout.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ss on 15-4-15.
 */
public class CommonProgressFragment extends DialogFragment{
    private static String key="msg";
    private static String DEFAULTMESSAGE="加载中，请稍后";
    private static String msg;
    private static CommonProgressFragment fragment;

    public static CommonProgressFragment getInstance(String msg){

        if(fragment == null){
            synchronized (CommonProgressFragment.class){
                fragment=new CommonProgressFragment();
            }
        }


        if(msg==null || msg.equals("")){
            msg=DEFAULTMESSAGE;
        }

        CommonProgressFragment.msg=msg;

        return fragment;
    }

    public static void disappear(){
        if(fragment == null){
            return;
        }
        fragment.dismiss();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(msg);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
