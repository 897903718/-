package chanlytech.com.laborsupervision.activity;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arialyy.frame.util.show.T;
import com.chanlytech.unicorn.imageloader.utils.L;

import java.io.File;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.entiy.UserEntity;
import chanlytech.com.laborsupervision.module.SetModule;
import chanlytech.com.laborsupervision.util.Imageload;
import chanlytech.com.laborsupervision.web.AboutActivity;

public class SetActivity extends BaseActivity<SetModule> implements View.OnClickListener {
    @InjectView(R.id.fileCacheSize)
    TextView tv_Cache;
    @InjectView(R.id.tv_code)
    TextView tv_code;
    @InjectView(R.id.clear_cache)
    RelativeLayout mRelativeLayout_clean;
    @InjectView(R.id.rl_feeback)
    RelativeLayout mRelativeLayout_feeback;
    @InjectView(R.id.rl_about)
    RelativeLayout mRelativeLayout_about;
    @InjectView(R.id.tv_user_logout)
    TextView tv_logout;
    @InjectView(R.id.tv_title)
    TextView mTextView_title;
    @InjectView(R.id.iv_back)
    ImageView mImageView_back;
    private PackageInfo info;
    private static final String APP_CACAHE_DIRNAME = "/webcache";

    @Override
    public int setContentView() {
        return R.layout.activity_set;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        ininLinster();
        BaseApplication.getAppLoctin().addActivity(this);
    }



    @Override
    public SetModule initModule() {
        return new SetModule(this);
    }

    private void initView() {
        mTextView_title.setText("设置");
        if(AppManager.isLogin()){
            tv_logout.setVisibility(View.VISIBLE);
        }else{
            tv_logout.setVisibility(View.GONE);
        }
        tv_Cache.setText(getModule().getFolderSize()+"M");
        PackageManager pm = getPackageManager();
        try {
            assert pm != null;
            info = pm.getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
            tv_code.setText("V" + info.versionName);
        } catch (Exception e) {
            tv_code.setVisibility(View.GONE);
        }
    }
    private void ininLinster() {
        mRelativeLayout_clean.setOnClickListener(this);
        mRelativeLayout_feeback.setOnClickListener(this);
        mRelativeLayout_about.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
        mImageView_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.clear_cache:
                Imageload.cleanCache();//清除Imageload缓存的图片
                getModule().deleteCacheFile();
                cleanWebCache();// 这里清不清除webview缓存？
                tv_Cache.setText("0M");
                break;
            case R.id.rl_feeback://意见反馈
                Intent intent=new Intent(this,FreeBackActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_about://关于我们
                Intent intent1=new Intent(this, AboutActivity.class);

                startActivity(intent1);
//                T.showLong(this, "抱歉暂时还未开放关于我们");
                break;
            case R.id.tv_user_logout://退出登录
                UserEntity mUserEntity=new UserEntity();
                AppManager.saveUser(mUserEntity);
                AppManager.setLoginState(false);
                tv_logout.setVisibility(View.GONE);
                T.showLong(this, "退出成功");
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void cleanWebCache(){
//        CookieSyncManager.createInstance(this);
//        CookieSyncManager.getInstance().startSync();
//        CookieManager.getInstance().removeSessionCookie();
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        }catch (Exception e){
            e.printStackTrace();
        }
        //webView缓存文件
        File appCacheDir=new File(getCacheDir().getAbsolutePath()+APP_CACAHE_DIRNAME);
//        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");
        File webviewCacheDir = new File(getCacheDir().getAbsolutePath());
        if(webviewCacheDir.exists()){
            deleteFile(webviewCacheDir);
        }
        if (appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
    }

    private void deleteFile(File file) {
        if(file.exists()){
            if(file.isFile()){
                file.delete();
            }else if(file.isDirectory()){
                File files[]=file.listFiles();
                for (int i=0;i<files.length;i++){
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }else {
            L.i("delete file no exists " + file.getAbsolutePath());
        }
    }
}
