package chanlytech.com.laborsupervision.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arialyy.frame.util.show.T;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.chanlytech.unicorn.imageloader.utils.L;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.net.URISyntaxException;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;

/**
 * 地图导航
 */
public class MapActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.bmapView)
    MapView mMapView;
    @InjectView(R.id.iv_back)
    ImageView mImageView_back;
    @InjectView(R.id.tv_title)
    TextView mTextView_title;
    @InjectView(R.id.rl_navigation)
    RelativeLayout mRelativeLayout_navigation;
    @InjectView(R.id.tv_name)
    TextView textView;
    @InjectView(R.id.tv_addr)
    TextView textView_addr;
    @InjectView(R.id.tv_navigation)
    TextView textView_navigation;
    private BaiduMap mBaiduMap;
    private Marker mMarker;
    private BitmapDescriptor bitmap;
    private Intent mIntent;
    private double lat,lng;
    private String name,addr;

    @Override
    public int setContentView() {

        return R.layout.activity_map;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        initView();
        initLinster();
        BaseApplication.getAppLoctin().addActivity(this);
    }

    private void initView() {
        lat=getIntent().getDoubleExtra("lat",30.663515);
        lng=getIntent().getDoubleExtra("lng", 104.07215);
        name = getIntent().getStringExtra("name");
        addr=getIntent().getStringExtra("addr");
        mTextView_title.setText(name);
        textView_addr.setText(addr);
        bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_position);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        mBaiduMap.setMapStatus(msu);
        textView.setText(name);
        initOverlay();
    }

    private void initLinster() {
        mImageView_back.setOnClickListener(this);
        textView_navigation.setOnClickListener(this);
        mRelativeLayout_navigation.setOnClickListener(this);
    }

    private void initOverlay() {
        LatLng point = new LatLng(lng, lat);
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap)
                .zIndex(0).draggable(true);
        //构建MarkerOption，用于在地图上添加Marker
        mMarker = (Marker) (mBaiduMap.addOverlay(option));
        mBaiduMap.addOverlay(option);
        // 将地图移到到最后一个经纬度位置
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(point);
        mBaiduMap.setMapStatus(u);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_navigation:
//                String distance = getDistance();
//                T.showLong(this, "距离" + distance + "Km");
                //调用百度地图客户端
                if (isInstallByread("com.baidu.BaiduMap")) {
                    try {//mode导航模式，固定为transit、driving、walking，分别表示公交、驾车和步行
                        //region 城市名或县名 当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市
                        mIntent = Intent.getIntent("intent://map/direction?origin=latlng:+"+lng+",+"+lat+"|name:我的位置&destination="+addr+"&mode=driving®ion=成都&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
//                        105.164957,31.638494
//                        mIntent = Intent.getIntent("intent://map/direction?origin=latlng:31.638494,105.164957|name:我的位置&destination="+addr+"&mode=driving&region=成都&src=千立网络科技有限公司|劳动社保#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    startActivity(mIntent);
                } else {
                    showDialog();
                }
                break;
            case R.id.rl_navigation:
                //调用百度地图客户端
                if (isInstallByread("com.baidu.BaiduMap")) {
                    try {//mode导航模式，固定为transit、driving、walking，分别表示公交、驾车和步行
                        //region 城市名或县名 当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市
                        mIntent = Intent.getIntent("intent://map/direction?origin=latlng:+"+lng+",+"+lat+"|name:我的位置&destination="+addr+"&mode=driving®ion=成都&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
//                        105.164957,31.638494
//                        mIntent = Intent.getIntent("intent://map/direction?origin=latlng:31.638494,105.164957|name:我的位置&destination="+addr+"&mode=driving&region=成都&src=千立网络科技有限公司|劳动社保#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    startActivity(mIntent);
                } else {
                    showDialog();
                }
                break;
        }
    }


    @Override
    protected void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
        super.onDestroy();
        // 回收 bitmap 资源
        bitmap.recycle();

    }

    /**
     * 坐标计算
     * return 单位 km
     */

    private String getDistance() {
        // 移动互联创业大厦坐标 104.0627,30.544777
        double mLat1 = 30.544777;
        double mLon1 = 104.0627;
        // 益州国际广场坐标 104.062368,30.533463  104.072177,30.663523
        double mLat2 = 30.663523;
        double mLon2 = 104.072177;
        LatLng start=new LatLng(mLat1,mLon1);
        LatLng end=new LatLng(mLat2,mLon2);

//        double pk = (double) (180 / 3.14169);
//        double a1 = mLat1 / pk;
//        double a2 = mLon1 / pk;
//        double b1 = mLat2 / pk;
//        double b2 = mLon2 / pk;
//        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
//        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
//        double t3 = Math.sin(a1) * Math.sin(b1);
//        double tt = Math.acos(t1 + t2 + t3);
//        String distance = String.format("%.1f", (6366000 * tt)/1000);
        double distance1 = DistanceUtil.getDistance(start, end);
        String distance = String.format("%.1f", distance1/1000);


        return distance;

    }

    /**
     *  
     *  * 判断是否安装目标应用 
     *  * @param packageName 目标应用安装后的包名 
     *  * @return 是否已安装目标应用 
     *  
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }


    /**
     * 启动百度地图驾车路线规划
     *
     */
    public void startRoutePlanDriving(double lat,double lng,String addr) {
        LatLng pt_start = new LatLng(lat, lng);
        // 构建 route搜索参数
        RouteParaOption para = new RouteParaOption()
//                .startPoint(pt_start)
			.startName("我的位置")
//			.endPoint(pt_end);
                .endName(addr)
                .cityName("成都");
        try {
            BaiduMapRoutePlan.openBaiduMapDrivingRoute(para, this);
        } catch (Exception e) {
            e.printStackTrace();
            showDialog();
        }

    }

    /**
     * 提示未安装百度地图app或app版本过低
     *
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(MapActivity.this);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }
}
