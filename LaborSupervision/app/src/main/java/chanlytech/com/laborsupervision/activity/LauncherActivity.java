package chanlytech.com.laborsupervision.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.show.T;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.chanlytech.unicorn.imageloader.utils.L;
import com.chanlytech.unicorn.utils.AndroidUtils;
import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.google.code.microlog4android.appender.Appender;
import com.google.code.microlog4android.appender.FileAppender;
import com.google.code.microlog4android.config.PropertyConfigurator;
import com.igexin.sdk.PushManager;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.BaseDataEntity;
import chanlytech.com.laborsupervision.entiy.BaseEntity;
import chanlytech.com.laborsupervision.entiy.UserEntity;
import chanlytech.com.laborsupervision.entiy.VersionEntity;
import chanlytech.com.laborsupervision.module.LauncherModule;
import chanlytech.com.laborsupervision.sqlite.MessageDao;
import chanlytech.com.laborsupervision.util.AspLog;
import chanlytech.com.laborsupervision.util.Imageload;
import chanlytech.com.laborsupervision.util.MyLog;

public class LauncherActivity extends BaseActivity<LauncherModule> implements View.OnClickListener {

    @InjectView(R.id.launch_image)
    ImageView mImageView;
    @InjectView(R.id.more)
    TextView mTextView_more;
    private LocationClient mLocationClient;
    private String url;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private MyTimer myTimer;
    private boolean state = false;//用于判断是否跳转了浏览器
//    private static final Logger logger = LoggerFactory.getLogger(LauncherActivity.class);
    @Override
    public int setContentView() {
        return R.layout.activity_launcher;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        //个推初始化
        PushManager.getInstance().initialize(this.getApplicationContext());

        initLinster();
        //开启定位
        mLocationClient = ((BaseApplication) getApplication()).mLocationClient;
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setLocationMode(tempMode);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        getModule().getStartimg();
        getModule().update();
        getModule().getUserInfo();
             /*
        * 日志收集
        * */
        //绑定百度推送id
//        if (AppManager.isLogin() && SharedPreferData.readString(this, "CID").length() > 0) {
//            getModule().bingCid();
//        }
        myTimer = new MyTimer(5000, 1000);
        StringBuffer sb=new StringBuffer();
        sb.append(AndroidUtils.getDeviceModel());
        sb.append(AndroidUtils.getPackageInfo(this).versionName);
//        MyLog.i("手机型号及android版本", sb.toString());
//        PropertyConfigurator.getConfigurator(this).configure();
//        logger.debug("my debug");
////        final FileAppender fa=logger.getAppender(1);
//        FileAppender appender =(FileAppender)logger.getAppender(1);
//        appender.setAppend(true);
//        MyLog.getLog();


    }

    private void initLinster() {
        mTextView_more.setOnClickListener(this);
    }

    @Override
    public LauncherModule initModule() {
        return new LauncherModule(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (state) {
            Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            state = false;
        }
//        TelephonyManager telephonyManager= (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        String deviceId = telephonyManager.getDeviceId();//设备号
//        L.i("设备号",deviceId);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //结束定位
        mLocationClient.stop();

        MobclickAgent.onPause(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.more:
                state = true;
                Intent mIntent = new Intent();
                mIntent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                mIntent.setData(content_url);
                startActivity(mIntent);
                myTimer.cancel();
                break;
        }
    }

    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.UPDATE_INFO:
                BaseEntity baseEntity1 = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity1.getStatus() == 1) {
                    VersionEntity entity = JSON.parseObject(baseEntity1.getData(), VersionEntity.class);
                    int code = AndroidUtils.getPackageInfo(this).versionCode;
                    if (Integer.parseInt(entity.getVersionCode()) > code) {
                        showDownLoadDialog(entity);
                    }
                }
                break;
            case ResultCode.START_IMG:
                myTimer.start();
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    BaseDataEntity baseDataEntity = JSON.parseObject(baseEntity.getData(), BaseDataEntity.class);
                    if (baseDataEntity.getUrl().length() > 0) {
                        url = baseDataEntity.getUrl();
                        mTextView_more.setVisibility(View.VISIBLE);
                    } else {
                        mTextView_more.setVisibility(View.GONE);
                    }
                    Imageload.LoadImag(this, baseDataEntity.getImageUrl(), mImageView);
                    Animation animation = AnimationUtils.loadAnimation(LauncherActivity.this, R.anim.launcher_scale);
                    mImageView.startAnimation(animation);
                }
                break;
            case ResultCode.EORROR_CODE:
                myTimer.start();
                break;
            case ResultCode.GET_USER_INFO://获取用户信息，用于判断用户是否存在
                BaseEntity entity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (entity.getStatus() == 1) {
                    UserEntity userEntity = JSON.parseObject(entity.getData(), UserEntity.class);
                    if (userEntity.getUserId().equals("0")) {
                        AppManager.setLoginState(false);//用户未登录
                        AppManager.saveUser(userEntity);
                    }
                }
                break;
        }
    }

    /**
     * 显示更新窗口
     *
     * @param entity 版本升级实体
     */
    protected void showDownLoadDialog(final VersionEntity entity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LauncherActivity.this);
        builder.setCancelable(false);
        builder.setMessage(entity.getDescription());
        builder.setTitle("升级提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                state=true;
                myTimer.cancel();
                dialog.dismiss();
                Intent mIntent = new Intent();
                mIntent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(entity.getDownload());
                mIntent.setData(content_url);
                startActivity(mIntent);

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //取消更新立即进入主界面关闭倒计时
                myTimer.cancel();
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
//                initWorkflow();
            }
        });
        builder.create().show();
    }

    class MyTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
