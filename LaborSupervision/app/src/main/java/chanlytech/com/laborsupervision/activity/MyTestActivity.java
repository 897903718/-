package chanlytech.com.laborsupervision.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.arialyy.frame.util.NetUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.web.js.JsInterface;

/**
 * 我的测试和我的预约
 */
public class MyTestActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView mTextView;
    @InjectView(R.id.webview)
    WebView mWebView;
    @InjectView(R.id.iv_load)
    ImageView iv_load;
    private AnimationDrawable animationDrawable;
    private static final String APP_CACAHE_DIRNAME = "/webcache";



    @Override
    public int setContentView() {
        return R.layout.activity_my_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        openAnimation();
        BaseApplication.getAppLoctin().addActivity(this);
    }
    @SuppressLint("JavascriptInterface")
    private void initView(){
        mTextView.setText(getIntent().getStringExtra("title"));
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (NetUtils.isConnected(this)) {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  //设置 缓存模式
        } else {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        Log.i("", "cacheDirPath=" + cacheDirPath);
        //设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setUserAgentString("laodongjianguan/" + AppManager.getUser().getUserId() + "/" + mWebView.getSettings().getUserAgentString());
        mWebView.loadUrl(getIntent().getStringExtra("url"));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                openAnimation();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (animationDrawable != null && animationDrawable.isRunning()) {
                    animationDrawable.stop();
                    iv_load.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                view.loadUrl("file:///android_asset/errorh5/errorh.html");
            }
        });
        mWebView.addJavascriptInterface(new JsInterface(this,getIntent().getStringExtra("url")), "AppJsInterface");


    }

    private void openAnimation(){
        iv_load.setVisibility(View.VISIBLE);
        animationDrawable=new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.refresh_01),100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.refresh_02),100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.refresh_02),100);
        animationDrawable.setOneShot(false);
        iv_load.setBackgroundDrawable(animationDrawable);
        if(animationDrawable!=null&&!animationDrawable.isRunning()){
            animationDrawable.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
