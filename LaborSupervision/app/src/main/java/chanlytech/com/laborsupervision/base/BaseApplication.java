package chanlytech.com.laborsupervision.base;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

import com.arialyy.frame.application.AbsApplication;
import com.arialyy.frame.util.show.L;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.chanlytech.unicorn.core.app.AbstractApplication;

import java.util.ArrayList;
import java.util.List;

import chanlytech.com.laborsupervision.entiy.CityEntity;
import chanlytech.com.laborsupervision.entiy.UserEntity;
import chanlytech.com.laborsupervision.http.ServerUtil;
import chanlytech.com.laborsupervision.util.AspLog;
import chanlytech.com.laborsupervision.util.CrashHandler;

/**
 * Created by Lyy on 2015/8/31.
 */
public class BaseApplication extends AbstractApplication {
    private static BaseApplication mInstance;
    private List<Activity> activities=new ArrayList<Activity>();
    private List<Activity> activities1=new ArrayList<Activity>();
    public LocationClient mLocationClient = null;
    public  BDLocationListener myListener;
    public  GeofenceClient mGeofenceClient;
    public  Vibrator mVibrator;
    private  LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private CityEntity mCityEntity;
    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用百度 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(getApplicationContext());//初始化百度组件
        ServerUtil.newInstance(getApplicationContext());//初始化网络请求
        AppManager.newInstance(getApplicationContext());//初始化存储
        mInstance = this;
        mCityEntity=new CityEntity();
        myListener = new MyLocationListener();
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mGeofenceClient = new GeofenceClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        InitLocation();

        /**
         * 错误日志收集器
         * */
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        creatUser();

//        L.isDebug=false;//关闭日志打印
    }



    public void addActivity(Activity activity){
        activities.add(activity);
    }
    public void addOtherActivity(Activity activity){
        activities1.add(activity);
    }

    public void killActivity(){
        for (Activity activity : activities1) {
            if(activity!=null){
                activity.finish();
            }

        }
    }

    public void exit(){
        for (Activity activity : activities) {
            if(activity!=null){
                activity.finish();
            }

        }
    }
    public static BaseApplication getAppLoctin() {
        return mInstance;
    }


    public  static BaseApplication getApp(){
        return  getAppLoctin();
    }

    /**
     * 创建游客
     * */
    private void creatUser(){
        if (!AppManager.isLogin()){
            UserEntity userEntity=new UserEntity();
            AppManager.saveUser(userEntity);
        }
    }
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            sb.append("\n城市:");
            sb.append(location.getCity());
            sb.append("\n区/县信息:");
            sb.append(location.getDistrict());//获取区县
            sb.append("\n城市编码");
            sb.append(location.getCityCode());
            sb.append("\n街道信息：");
            sb.append(location.getStreet());
            sb.append("\n街道号码");
            sb.append(location.getStreetNumber());
            sb.append("\n所在楼层");
            sb.append(location.getFloor());//获取楼层信息仅限室内
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());//获取gps定位是锁定的卫星数
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
            }
            if(location.getLocType()==61){
                mCityEntity.setLatitude(location.getLatitude());
                mCityEntity.setLongitude(location.getLongitude());
            }else if(location.getLocType()==65){
                mCityEntity.setLatitude(location.getLatitude());
                mCityEntity.setLongitude(location.getLongitude());
//                AppManager.saveCity(mCityEntity);
            }else if(location.getLocType()==66){
                mCityEntity.setLatitude(location.getLatitude());
                mCityEntity.setLongitude(location.getLongitude());
//                AppManager.saveCity(mCityEntity);
            }else if(location.getLocType()==161){
                mCityEntity.setLatitude(location.getLatitude());
                mCityEntity.setLongitude(location.getLongitude());
//                AppManager.saveCity(mCityEntity);
            }
            mCityEntity.setAddress(location.getAddrStr());
            AppManager.saveCity(mCityEntity);
//            if (location.getCity()!=null){
//                mCityEntity.setCityId(location.getCityCode());
//                mCityEntity.setLatitude(location.getLatitude());
//                mCityEntity.setLongitude(location.getLongitude());
//                if(location.getCity().contains("市")){
//                    mCityEntity.setName(location.getCity().substring(0, location.getCity().indexOf("市")));
//                }else {
//                    mCityEntity.setName(location.getCity());
//                }
//                AppManager.saveCity(mCityEntity);
//            }

            L.i("当前位置", location.getAddrStr() + sb.toString());
//            logMsg(sb.toString());
        }


    }

    //配置定位类型
    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);//设置定位模式
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02
        int span = 5000;
        option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

}
