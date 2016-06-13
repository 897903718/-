package chanlytech.com.laborsupervision.web;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.arialyy.frame.util.AndroidUtils;
import com.arialyy.frame.util.NetUtils;
import com.arialyy.frame.util.show.T;
import com.chanlytech.unicorn.imageloader.utils.L;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.FileUtils;
import chanlytech.com.laborsupervision.util.PictureUtil;
import chanlytech.com.laborsupervision.web.js.JsInterface;

public class TestActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.title)
    TextView mTextView_title;
    @InjectView(R.id.myweb)
    WebView mWebView;
    @InjectView(R.id.back)
    ImageView mImageView;
    @InjectView(R.id.iv_load)
    ImageView iv_load;
    private AnimationDrawable animationDrawable;
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private static int FILECHOOSER_RESULTCODE = 0;
    public ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private Uri imageUri;

    @Override
    public int setContentView() {
        return R.layout.activity_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
        BaseApplication.getAppLoctin().addActivity(this);
    }

    @SuppressLint("JavascriptInterface")
    private void initView() {

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (NetUtils.isConnected(this)) {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//设置 缓存模式LOAD_DEFAULT
        } else {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        Log.i("", "cacheDirPath=" + cacheDirPath);
        //设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setUserAgentString("laodongjianguan/" + AppManager.getUser().getUserId() + "/UserType=" + AppManager.getUser().getUserType() + "/" + mWebView.getSettings().getUserAgentString());
//        L.d("UserAgent", mWebView.getSettings().getUserAgentString());
//        T.showLong(this,mWebView.getSettings().getUserAgentString());
        mWebView.loadUrl(getIntent().getStringExtra("url"));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                }else {
                    Log.i("拦截到的url", url);
                    if(url.startsWith("http")||url.startsWith("www")){
                        view.loadUrl(url);
                    }

                }
                return true;
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

//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//                view.loadUrl("file:///android_asset/errorh5/errorh.html");
//            }
        });
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTextView_title.setText(title);
            }

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
                    TestActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"),FILECHOOSER_RESULTCODE);
                } else {
                    selectFileChooser();
                }

            }

        });

        mWebView.addJavascriptInterface(new JsInterface(this, getIntent().getStringExtra("url")), "AppJsInterface");

    }

    private void openAnimation() {
        iv_load.setVisibility(View.VISIBLE);
        animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.refresh_01), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.refresh_02), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.refresh_03), 100);
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
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.back:
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
//                mUploadMessage.onReceiveValue(compressByUri(result));
                Uri uri=compressByUri(result);
                JsInterface.imgPath=uri.getPath();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            } else {
//                mUploadMessage.onReceiveValue(compressByUri(imageUri));
                Uri uri=compressByUri(imageUri);
                JsInterface.imgPath=uri.getPath();
                mUploadMessage.onReceiveValue(imageUri);
                mUploadMessage = null;
            }
        }

//        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFileString));
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
//                results=new Uri[]{compressByUri(imageUri)};
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
                JsInterface.imgPath=compressByUri(Uri.parse(dataString)).getPath();
                results = new Uri[]{Uri.parse(dataString)};

            }
        }
        if (results != null) {
            //压缩图片
            //mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {

            Uri u=compressByUri(imageUri);
            JsInterface.imgPath=u.getPath();
            results=new Uri[]{imageUri};
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

    @Override
    protected void onPause() {
        super.onPause();
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
//        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }
    //根据图片的的uri来压缩
    private Uri compressByUri(Uri imageUri){
        Uri uri=null;
        try {
//        String path=FileUtils.getPath(this,imageUri);
            ContentResolver contentResolver = getContentResolver();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
//            Bitmap smallBitmap = PictureUtil.getSmallBitmap(path);
//            bitmap.recycle();
//            saveScalePhoto(smallBitmap);
//            uri=Uri.fromFile(new File(Constants.getSDPath() + "/IMG_2015.jpg"));
            int size=getBitmapSize(bitmap);
            if(size>1024*200){
                Bitmap newbitmap=zoomBitmap(bitmap,bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                bitmap.recycle();
                saveScalePhoto(newbitmap);
                uri=Uri.fromFile(new File(Constants.getSDPath() + "/IMG_2015.jpg"));
            }else {
                uri=imageUri;
            }
         }catch (Exception e){
            e.printStackTrace();
            return  imageUri;
        }
        return uri;
    }

    /**
     *  
     *      * 存储缩放的图片 
     *      * @param data 图片数据 
     *      
     */
    private void saveScalePhoto(Bitmap bitmap) {
        if (bitmap != null) {
            // 照片全路径  
            String fileName = "";
            // 文件夹路径  
            String pathUrl = Constants.getSDPath() + "/";
            String imageName = "IMG_2015.jpg";
            FileOutputStream fos = null;
            File file = new File(pathUrl);
            file.mkdirs();// 创建文件夹  
            fileName = pathUrl + imageName;
            try {
                fos = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public int getBitmapSize(Bitmap bitmap){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){ //API 19
             return bitmap.getAllocationByteCount();
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
            return bitmap.getByteCount();
             }
        return bitmap.getRowBytes() * bitmap.getHeight(); //earlier version
    }

}
