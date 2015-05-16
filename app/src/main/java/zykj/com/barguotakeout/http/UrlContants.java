package zykj.com.barguotakeout.http;

/**
 * Created by ss on 15-4-14.
 */
public class UrlContants {

    public static final String BASE_URL="http://121.42.139.60/baguo/";


    public static final String BASEURL=BASE_URL+"%s";

    public static final String INDEXIMAGEURL="indexAds.php";

    public static final String DAYJOKE="dailyjokes.php";

    public static final String DRAWLOTTERY="drawlottery.php";

    public static final String GIFTURL="gifts.php";

    public static final String REGISTER="register.php";

    public static final String LOGIN="login.php";

    public static final String jsonData="data";

    public static final String ADDRESS="getaddress.php";

    public static final String RESTAURANT="server/Home/Node/api_getRestaurants";

    public static final String ACTIVITY="getactivites.php";

    public static final String FOOdTYPE="getgoodscat.php";

    public static final String GOODS="getgoods.php";

    public static final String AllORDER="getorders.php";

    public static final String COMMIT_ORDER="server/Home/Order/api_commitOrder";

    public static final String TOTAL_PRICE="gettotalprice.php";

    public static final String Rest_Detail="server/Home/Node/getResDetail";

    public static final String UPLOAD_URL="server/Home/Upload/uploadImage";

    public static final String UPDATE_USER="edituserinfo.php";

    public static  final String MODIFY_PWD="resetpwd.php";

    public static final String ORDERDETAIL="getorderdetail.php";

    public static final String CompanyIno="server/Home/My/apiget_basicdetail.php";

    public static final String PAY="ping++/pay.php";

    public static final String CANCELORDER="server/Home/Order/api_cancelOrder";

    public static final String CONFIRMORDER="server/Home/Order/api_ensureOrder";

    public static String getUrl(String token){
        if(token==null || token.equals("")){
            return BASE_URL;
        }
      return  String.format(BASEURL,token);
    }

}
