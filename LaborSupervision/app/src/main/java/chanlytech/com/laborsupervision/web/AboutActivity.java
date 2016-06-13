package chanlytech.com.laborsupervision.web;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.config.ServerConfig;
import chanlytech.com.laborsupervision.web.js.JsInterface;

/**
 * 关于我们
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.iv_back)
    ImageView imageView;
    @InjectView(R.id.tv_title)
    TextView textView;
    @InjectView(R.id.web)
    WebView webView;
    private String url = ServerConfig.BASE_URL + "Version/Version/versionPush?versionCode=";
    private PackageInfo info;

    @Override
    public int setContentView() {
        return R.layout.activity_about;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        BaseApplication.getAppLoctin().addActivity(this);
        textView.setText("关于我们");
        imageView.setOnClickListener(this);
        WebSettings webViewSettings = webView.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString("laodongjianguan/" + AppManager.getUser().getUserId() + "/UserType=" + AppManager.getUser().getUserType() + "/" + webView.getSettings().getUserAgentString());

        PackageManager pm = getPackageManager();
        try {
            info = pm.getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        webView.loadUrl(url + info.versionCode);
        webView.setWebViewClient(new WebViewClient() {

                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         super.onPageFinished(view, url);
                                     }

                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                         view.loadUrl(url);
                                         return true;
                                     }

                                     @Override
                                     public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                                         super.onReceivedError(view, errorCode, description, failingUrl);
                                         view.loadUrl("file:///android_asset/errorh5/errorh.html");
                                     }
                                 }

        );

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        webView.addJavascriptInterface(new JsInterface(this,url), "AppJsInterface");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
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
