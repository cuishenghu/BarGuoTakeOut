package zykj.com.barguotakeout.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * 弹窗
 * Created by ss on 15-4-23.
 */
public class ToastUTil {

    public static void shortT(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }

    public synchronized static AlertDialog.Builder alertDialogBuilder(Context context){
        return new AlertDialog.Builder(context);
    }
}
