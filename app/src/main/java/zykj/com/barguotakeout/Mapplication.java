package zykj.com.barguotakeout;

import android.app.Application;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.smssdk.SMSSDK;
import zykj.com.barguotakeout.Utils.BgContants;

/**
 * Created by ss on 15-4-14.
 */
public class Mapplication extends Application{

    private static AppModel model;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        //初始化SMSdk
        SMSSDK.initSDK(getApplicationContext(), BgContants.MOBKEY,BgContants.MOBSECRET);

        initModel();//初始化 数据

    }

    private void initModel() {
        model=AppModel.init(this);
    }

    public static AppModel getModel(){
        if(model == null){
            Log.e("application","appmodel is null");
        }
        return model;
    }



    /**
     * 初始化imageLoader
     */
    private void initImageLoader() {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();

        ImageLoader.getInstance().init(config.build());
    }


}
