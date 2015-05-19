package zykj.com.barguotakeout.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

import zykj.com.barguotakeout.R;
import zykj.com.barguotakeout.Utils.Commonutils;
import zykj.com.barguotakeout.Utils.ToastUTil;
import zykj.com.barguotakeout.fragment.CommonProgressFragment;
import zykj.com.barguotakeout.http.HttpErrorHandler;
import zykj.com.barguotakeout.http.HttpUtil;
import zykj.com.barguotakeout.http.UrlContants;
import zykj.com.barguotakeout.model.User;
import zykj.com.barguotakeout.view.RoundImageView;

/**
 * Created by ss on 15-5-5. 我的账户
 */
public class MCountActivity extends CommonActivity implements View.OnClickListener {

    public static  final String KEY="user";
    private User user;
    private TextView tv_username;
    private RelativeLayout rl_avatar;
    private RelativeLayout rl_name;

    private static final int PHOTO_REQUEST_GALLERY = 4;
    private RoundImageView avatar;
    private String savepath;
    private RelativeLayout rl_modifypwd;
    private RelativeLayout rv_count_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_count);
        user= (User) getIntent().getSerializableExtra(KEY);

        initView();

    }

    private void initView() {
        avatar = (RoundImageView) findViewById(R.id.rv_count_avator);
        tv_username = (TextView) findViewById(R.id.tv_count_username);
        rl_modifypwd = (RelativeLayout) findViewById(R.id.rl_count_modyfipwd);
        rv_count_address = (RelativeLayout) findViewById(R.id.rv_count_address);
        if(user.getUsername()!=null){
            tv_username.setText(user.getUsername());
          }
        if(!TextUtils.isEmpty(user.getAvatar())){
            ImageLoader.getInstance().displayImage(UrlContants.getUrl(user.getAvatar()), avatar);
        }
        rl_avatar = (RelativeLayout) findViewById(R.id.rl_me_avatar);
        rl_name = (RelativeLayout) findViewById(R.id.rl_count_name);
        rl_avatar.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_modifypwd.setOnClickListener(this);
        rv_count_address.setOnClickListener(this);

    }

    public void back(View v){
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_me_avatar:
                //修改头像
                AlertDialog.Builder b = new AlertDialog.Builder(MCountActivity.this);
                b.setTitle("设置头像");
                b.setItems(new String[]{"从相册选择","拍照"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //从相册选择
                                gallery();
                                break;
                            case 1:
                                //拍照
                                takeCamera();
                                break;
                        }
                    }
                });
                b.create().show();
                break;
            case R.id.rl_count_name:
                //修改昵称

                break;
            case R.id.rl_count_modyfipwd:
                //修改密码
                Intent intent=new Intent(MCountActivity.this,ModifyPwd.class);
                intent.putExtra("userid",user.getUserid());
                startActivityForResult(intent,4);
                break;
            case R.id.rv_count_address:
                //我的地址
                Intent itaddress = new Intent(MCountActivity.this,WoDeShouHuoDiZhiActivity.class);
                startActivity(itaddress);
                break;
        }
    }
    public void gallery(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }
    public static final int camera_code=3;
    public void takeCamera(){
        //打开相机
        if(Commonutils.havaExternalStorage()){
            savepath = Environment.getExternalStorageDirectory()+"/temp";
        }else{
            savepath =this.getDir("temp", MODE_PRIVATE).toString();
        }

        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, savepath);
        startActivityForResult(intent, camera_code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case PHOTO_REQUEST_GALLERY:
                if(resultCode==RESULT_OK && data!=null && !data.equals("")){
                    Uri uri = data.getData();
                    uploadAvatar(uri);
                }
                break;
            case camera_code:
                //相机
                 if(resultCode==RESULT_OK && data!=null){
                     File file=new File(savepath);
                     if(file.exists()){
                        uploadAvatar(file);
                     }
                 }
                break;
        }
    }

    private void uploadAvatar(Uri uri){
        File file=new File(getPathByUri(uri));
        uploadAvatar(file);
    }
    private void uploadAvatar(File file) {
        try {
        RequestParams params=new RequestParams();
        params.put("image",file);
        CommonProgressFragment.getInstance("正在上传...").show(getSupportFragmentManager(),"progress");
        HttpUtil.uploadpic(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                CommonProgressFragment.disappear();
                String url=json.getJSONObject("data").getString("url");
                ToastUTil.shortT(MCountActivity.this,"上传成功");
                user.setAvatar(url);
                updateUser();
                ImageLoader.getInstance().displayImage(UrlContants.getUrl(url),avatar);
            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {
                CommonProgressFragment.disappear();
                ToastUTil.shortT(MCountActivity.this,json.getString("msg"));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                CommonProgressFragment.disappear();
                ToastUTil.shortT(MCountActivity.this,"上传失败");
            }
        },params);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getPathByUri(Uri uri){
        String picPath="";
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, pojo, null, null,null);
        if(cursor != null )
        {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            try{
                if(Integer.parseInt(Build.VERSION.SDK) < 14)
                {
                    cursor.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return picPath;
    }

    public void updateUser(){
        //更新用户信息

        if(user==null || TextUtils.isEmpty(user.getUserid())){
            return;
        }
        RequestParams params=new RequestParams();
        params.add("userid",user.getUserid());

        if(!TextUtils.isEmpty(user.getNickname())){
            params.add("nickname",user.getNickname());
        }

        if(!TextUtils.isEmpty(user.getAvatar())){
            params.add("avatar",user.getAvatar());
        }

        HttpUtil.updateUserInfo(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {

            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ToastUTil.shortT(MCountActivity.this,"网络连接错误,保存失败请重试");
            }
        },params);

    }


}
