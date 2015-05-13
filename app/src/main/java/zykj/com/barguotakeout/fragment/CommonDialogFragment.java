package zykj.com.barguotakeout.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by ss on 15-4-23.
 */
public class CommonDialogFragment extends DialogFragment{

    public static CommonDialogFragment dialogFragment;

    private static String mmsg;

    private static String mtitle;

    public static CommonDialogFragment getInstance(String title,String msg){

        if(dialogFragment==null){
            synchronized (CommonDialogFragment.class){
                dialogFragment=new CommonDialogFragment();
            }
        }
        mmsg=msg;
        mtitle=title;

        return dialogFragment;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

            builder.setMessage(mmsg);

            return    builder.create();

    }
}
