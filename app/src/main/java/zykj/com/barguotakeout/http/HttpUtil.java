package zykj.com.barguotakeout.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.net.URI;


/**
 * Created by ss on 15-4-14.
 */
public class HttpUtil {

    private HttpUtil(){

    }

    private static final String baseUrl="http://121.42.139.60/baguo/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        //使用默认的 cacheThreadPool
        client.setTimeout(15);
        client.setConnectTimeout(15);
        client.setMaxConnections(20);
        client.setResponseTimeout(20);
    }

    public static void login(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.LOGIN),params,handler);
    }


    public static void register(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.REGISTER),params,handler);
    }

    public static void indexPicUrl(AsyncHttpResponseHandler handler){
        client.post(UrlContants.getUrl(UrlContants.INDEXIMAGEURL),handler);
    }

    public static void dailyJoke(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.DAYJOKE),params,handler);
    }

    public static  void gifts(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.GIFTURL),params,handler);
    }

    public static void chooseAddress(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.ADDRESS),params,handler);
    }

    public static void getRestaurantList(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.RESTAURANT),params,handler);
    }

    public static void getActivities(AsyncHttpResponseHandler handler){
        client.post(UrlContants.getUrl(UrlContants.ACTIVITY),handler);
    }

    public static void getFoodTypes(AsyncHttpResponseHandler hanler,RequestParams params){
         client.post(UrlContants.getUrl(UrlContants.FOOdTYPE),params,hanler);
    }

    public static void getGoods(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.GOODS),params,handler);
    }

    public static void getAllOrder(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.AllORDER),params,handler);
    }

    public static void updateOrder(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.COMMIT_ORDER),params,handler);
    }
    public static void getTotalPrice(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.TOTAL_PRICE),params,handler);
    }

    public static void getRestDetail(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.Rest_Detail),params,handler);
    }

    public static void drawlottery(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.DRAWLOTTERY), params, handler);
    }

    public static void pay(AsyncHttpResponseHandler handler, RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.PAY), params, handler);
    }

    /**
     * 获取指定城市的天气信息
     * @param code
     * @param handler
     */
    public static void weatherInfo(String code,AsyncHttpResponseHandler handler){
        client.get("http://apistore.baidu.com/microservice/cityinfo"+code,handler);
    }

    /**
     * 上传文件
     * @param handler
     * @param params
     */
    public static void uploadpic(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.UPLOAD_URL),params,handler);
    }

    /**
     * 更新用户详细信息
     */
    public static void updateUserInfo(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.UPDATE_USER),params,handler);
    }

    public static void modifyPwd(AsyncHttpResponseHandler handler,RequestParams params){
        client.post(UrlContants.getUrl(UrlContants.MODIFY_PWD),params,handler);
    }

    public static void getOrderStatus(AsyncHttpResponseHandler handler,RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.getUrl(UrlContants.ORDERDETAIL)),params,handler);
    }

    public static void getCompanyInfo(AsyncHttpResponseHandler handler) {
        client.post(UrlContants.getUrl(UrlContants.CompanyIno),handler);
    }

    public static void getMyAddress(AsyncHttpResponseHandler handler,RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.MyAddress),params,handler);
    }

    public static void AddAddress(AsyncHttpResponseHandler handler,RequestParams params) {
        client.post(UrlContants.getUrl(UrlContants.AddAddress),params,handler);
    }
}
