package chanlytech.com.laborsupervision.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.AndroidUtils;
import com.arialyy.frame.util.NetUtils;
import com.arialyy.frame.util.show.L;
import com.arialyy.frame.util.show.T;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.BaseEntity;
import chanlytech.com.laborsupervision.entiy.MessageEntity;
import chanlytech.com.laborsupervision.module.WebModule;
import chanlytech.com.laborsupervision.share.ShareHelper;
import chanlytech.com.laborsupervision.util.MyLog;
import chanlytech.com.laborsupervision.web.js.JsInterface;

public class WebActivity extends BaseActivity<WebModule> implements View.OnClickListener {

    @InjectView(R.id.title)
    TextView textView;
    @InjectView(R.id.webview)
    WebView mWebView;
    @InjectView(R.id.iv_share)
    ImageView mImageView_share;
    private String objId;
    private int type;
    private MessageEntity mMessageEntity;
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private static int FILECHOOSER_RESULTCODE = 0;
    public ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private Uri imageUri;
    @Override
    public int setContentView() {
        return R.layout.activity_web;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        BaseApplication.getAppLoctin().addActivity(this);
//        mMessageEntity=new MessageEntity();
        initLinster();
        textView.setText(getIntent().getStringExtra("title"));
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (NetUtils.isConnected(this)) {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        } else {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        L.i(TAG, "cacheDirPath=" + cacheDirPath);
        //设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setUserAgentString("laodongjianguan/" + AppManager.getUser().getUserId() + "/UserType=" + AppManager.getUser().getUserType() + "/" + mWebView.getSettings().getUserAgentString());
        mWebView.loadUrl(getIntent().getStringExtra("url"));
        objId=getIntent().getStringExtra("objid");
        type=getIntent().getIntExtra("type", 1);
        getModule().getShareInfo(objId, String.valueOf(type));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                view.loadUrl("file:///android_asset/errorh5/errorh.html");
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
            public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                if (AndroidUtils.getDeviceModel().equals("GT-I9300I")) {
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("*/*");
                    WebActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"),FILECHOOSER_RESULTCODE);
                } else {
                    selectFileChooser();
                }

            }
        });
        mWebView.addJavascriptInterface(new JsInterface(this,getIntent().getStringExtra("url")), "AppJsInterface");
    }

    @Override
    public WebModule initModule() {
        return new WebModule(this);
    }

    private void initLinster() {
        mImageView_share.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_share:
                if(mMessageEntity!=null){
                ShareHelper shareHelper=new ShareHelper(this);
                shareHelper.showSharePop(mMessageEntity.getTitle(),mMessageEntity.getUrl(),mMessageEntity.getDescription(),mMessageEntity.getImageUrl());
                }else {
                    T.showLong(this,"没有内容可以分享");
                }
                break;
        }
    }

    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.SHARE_CODE:
//                MyLog.i("分享的信息返回",data.toString());
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    mImageView_share.setEnabled(true);
                    mMessageEntity = JSON.parseObject(baseEntity.getData(), MessageEntity.class);
                } else {
                    mImageView_share.setEnabled(false);
                    ErrorToast.showError(this, baseEntity.getErrorCode());
                }
                break;
            case ResultCode.EORROR_CODE:
                T.showLong(this, data.toString());
                break;
        }
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
