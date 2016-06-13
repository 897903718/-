package chanlytech.com.laborsupervision.web;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.arialyy.frame.util.AndroidUtils;
import com.arialyy.frame.util.NetUtils;
import com.arialyy.frame.util.show.T;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.entiy.WebEntity;
import chanlytech.com.laborsupervision.web.js.JsInterface;

public class ServerWebActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    private WebView mWebView;
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private ImageView mImageView, iv_load;
    private AnimationDrawable animationDrawable;
    private LocationClient mLocationClient;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private WebEntity webEntity;
    private static int FILECHOOSER_RESULTCODE = 0;
    public ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_web);
        webEntity = getIntent().getParcelableExtra("webEntity");
        initView();
        initLinster();
        BaseApplication.getAppLoctin().addActivity(this);
        //开启定位
        mLocationClient = ((BaseApplication) getApplication()).mLocationClient;
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setLocationMode(tempMode);
        mLocationClient.setLocOption(option);
        if (webEntity.getUrl().contains("showgps")) {
            mLocationClient.start();
        }
    }

    @SuppressLint("JavascriptInterface")
    private void initView() {
        textView = (TextView) findViewById(R.id.title);
        mImageView = (ImageView) findViewById(R.id.back);
        iv_load = (ImageView) findViewById(R.id.iv_load);
        textView.setText(webEntity.getTitle());
        mWebView = (WebView) findViewById(R.id.webview);
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
        //设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setUserAgentString("laodongjianguan/" + AppManager.getUser().getUserId() + "/UserType=" + AppManager.getUser().getUserType() + "/" + mWebView.getSettings().getUserAgentString());
//       T.showLong(this,mWebView.getSettings().getUserAgentString());
        mWebView.loadUrl(webEntity.getUrl());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                } else {
                    if(url.startsWith("http")||url.startsWith("www")){
                        view.loadUrl(url);
                    }
                }
                return true;
//                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                view.loadUrl("file:///android_asset/errorh5/errorh.html");
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

        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            //android 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                selectFileChooser();
                return true;
            }
            @Deprecated
            // Android < 3.0 调用这个方法 
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                selectFileChooser();
            }
            @Deprecated
            // 3.0 + 调用这个方法 
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                selectFileChooser();
            }

            @Deprecated
            // Android > 4.1.1 调用这个方法 
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                if (AndroidUtils.getDeviceModel().equals("GT-I9300I")) {
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("*/*");
                    ServerWebActivity.this.startActivityForResult(
                            Intent.createChooser(i, "File Browser"),
                            FILECHOOSER_RESULTCODE);
                } else {
                    selectFileChooser();
                }

            }
        });
        mWebView.addJavascriptInterface(new JsInterface(this, webEntity.getUrl()), "AppJsInterface");
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void openAnimation() {
        iv_load.setVisibility(View.VISIBLE);
        animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.refresh_01), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.refresh_02), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.refresh_02), 100);
        animationDrawable.setOneShot(false);
        iv_load.setBackgroundDrawable(animationDrawable);
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    private void initLinster() {
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
//                openAnimation();
                if (mWebView.canGoBack()) {
                    mWebView.goBack();

                } else {
                    finish();
                }
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        try {
            mWebView.getClass().getMethod("onResume").invoke(mWebView, (Object[]) null);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        //结束定位

//        if(getIntent().getStringExtra("url").contains("showgps")){
//
//        }
        mLocationClient.stop();
        MobclickAgent.onPause(this);
        try {
            mWebView.getClass().getMethod("onPause").invoke(mWebView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            } else {
                mUploadMessage.onReceiveValue(imageUri);
                mUploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }

        return;
    }
    private void selectFileChooser() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        this.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }
}
