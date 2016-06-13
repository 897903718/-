package chanlytech.com.laborsupervision.web.js;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.webkit.JavascriptInterface;


import com.alibaba.fastjson.JSONObject;
import com.arialyy.frame.util.show.T;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.chanlytech.unicorn.imageloader.utils.L;
import com.google.gson.GsonBuilder;


import java.io.File;
import java.io.FileInputStream;

import chanlytech.com.laborsupervision.activity.IWantToJoinActivity;
import chanlytech.com.laborsupervision.activity.IdentityAuthenActivity;
import chanlytech.com.laborsupervision.activity.LawyersToJoin;
import chanlytech.com.laborsupervision.activity.LoginActivity;
import chanlytech.com.laborsupervision.activity.MainActivity;
import chanlytech.com.laborsupervision.activity.MapActivity;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.config.ServerConfig;
import chanlytech.com.laborsupervision.share.ShareHelper;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.web.TestActivity;

/**
 * Created by Lyy on 2015/10/12.
 * 与js交互的方法
 */
public class JsInterface {
    private Context mContext;
    private Intent mIntent;
    private long mFirstClickTime = 0;
    private String url;
    public static String imgPath;
    private LocationClient mLocationClient;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    public JsInterface(Context context, String url) {
        this.mContext = context;
        this.url = url;
    }

    /**
     * 页面跳转()
     */
    @JavascriptInterface
    public void towebpage(String tourl) {
//         long time=System.currentTimeMillis();
//        if(time-mFirstClickTime<2000){
//
//        }else {
//            mIntent = new Intent(mContext, TestActivity.class);
//            mIntent.putExtra("url", tourl);
//            mContext.startActivity(mIntent);
//        }
//        mFirstClickTime=time;
        if (!isFastClick()) {
            mIntent = new Intent(mContext, TestActivity.class);
            mIntent.putExtra("url", tourl);
            L.i("js界面跳转url", tourl);
            mContext.startActivity(mIntent);
        }
    }

    /**
     * 导航
     */
    @JavascriptInterface
    public void tomapnavigation(double lat, double lng, String name, String addr) {
        mIntent = new Intent(mContext, MapActivity.class);
        mIntent.putExtra("lat", lat);
        mIntent.putExtra("lng", lng);
        mIntent.putExtra("name", name);
        mIntent.putExtra("addr", addr);
        mContext.startActivity(mIntent);

    }

    /**
     * 向js提交区域位置
     */
    @JavascriptInterface
    public String getcurentarea() {

        return AppManager.getUser().getAddress();

    }

    /**
     * 向js提交经纬度
     */
    @JavascriptInterface
    public String getlocation() {
        JSONObject object = new JSONObject();
        object.put("lng", AppManager.getCity().getLongitude());
        object.put("lat", AppManager.getCity().getLatitude());
        return toJsonStr(object);
//         latitude : 30.548485  lontitude : 104.059012

    }

    /**
     * 获取用户信息
     */
    @JavascriptInterface
    public String getuserinfo() {
        JSONObject object = new JSONObject();
        if (AppManager.isLogin()) {
            object.put("userId", AppManager.getUser().getUserId());
            object.put("nickName", AppManager.getUser().getNickName());
            object.put("avatar", AppManager.getUser().getAvatar());
            object.put("cityId", AppManager.getUser().getCityId());
            object.put("telPhone", AppManager.getUser().getTelPhone());
            object.put("address", AppManager.getUser().getAddress());
            object.put("Mail", AppManager.getUser().getMail());
            object.put("userType", AppManager.getUser().getUserType());
        } else {
            object.put("userId", "");
            object.put("nickName", "");
            object.put("avatar", "");
            object.put("cityId", "");
            object.put("telPhone", "");
            object.put("address", "");
            object.put("Mail", "");
            object.put("userType", "");
        }

        return toJsonStr(object);
    }


    /**
     * 提交图片路径
     */
    @JavascriptInterface
    public String getImgPath() {
        byte[] buffer = null;
        try {
//    		File file=new File(Constants.getSDPath() + "/IMG_2015.jpg");
            File file = new File(imgPath);
            FileInputStream inputFile = new FileInputStream(file);
            buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;

        }
        return "data:image/jpg;base64," + Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * 返回上一页
     */

    @JavascriptInterface
    public void toprepage() {
        ((Activity) mContext).finish();
    }

    /**
     * 分享
     */
    @JavascriptInterface
    public void jsshare(String title, String content, String imgurl, String url) {
        ShareHelper shareHelper = new ShareHelper(mContext);
        shareHelper.showSharePop(title, url, content, imgurl);
//        T.showLong(mContext, title + content);
    }

    /**
     * 跳转到加入我们
     */
    @JavascriptInterface
    public void toJoinUs(String title, int type) {
        Intent mIntent = null;
        if (type == 1) {
            mIntent = new Intent(mContext, LawyersToJoin.class);
            mIntent.putExtra("title", title);
            mIntent.putExtra("type", type);
            mContext.startActivity(mIntent);
        } else {
            mIntent = new Intent(mContext, IWantToJoinActivity.class);
            mIntent.putExtra("title", title);
            mIntent.putExtra("type", type);
            mContext.startActivity(mIntent);
        }
    }

    /**
     * 跳转到登录页面
     */
    @JavascriptInterface
    public void tologinpage() {
        mIntent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(mIntent);
    }

    /**
     * 返回首页
     */
    @JavascriptInterface
    public void tophomepage() {
        mIntent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(mIntent);
        ((Activity) mContext).finish();
    }

    /**
     * 跳转到身份认证
     */
    @JavascriptInterface
    public void toRegistpage() {
        mIntent = new Intent(mContext, IdentityAuthenActivity.class);
        mContext.startActivity(mIntent);
    }

    /**
     * 重新加载界面
     */
    @JavascriptInterface
    public String reload() {
        return url;
    }


    /**
     * 获取当前位置
     * */
    @JavascriptInterface
    public String getAddress( ){
        //开启定位
        mLocationClient=BaseApplication.getAppLoctin().mLocationClient;
//        mLocationClient = ((BaseApplication) mContext).mLocationClient;
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setLocationMode(tempMode);
        int span = 5000;
        option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        return  AppManager.getCity().getAddress();
    }

    @JavascriptInterface
    public void closeLocation(){
//        mLocationClient=BaseApplication.getAppLoctin().mLocationClient;
//        if(mLocationClient.isStarted()){
//            mLocationClient.stop();
//        }


        if(mLocationClient!=null){
            if(mLocationClient.isStarted()){
                mLocationClient.stop();
            }
        }
    }

    /**
     * 返回Json字符串
     *
     * @param object
     * @return
     */
    public static String toJsonStr(Object object) {
        String result = "{}";
        if (object != null) {
            result = new GsonBuilder().create().toJson(object);
        }
        return result;
    }

    private boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - mFirstClickTime < 2000) {
            return true;
        }
        mFirstClickTime = time;
        return false;
    }


}
