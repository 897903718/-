package chanlytech.com.laborsupervision.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.KeyBoardUtils;
import com.arialyy.frame.util.NetUtils;
import com.arialyy.frame.util.show.L;
import com.arialyy.frame.util.show.T;
import com.chanlytech.ui.widget.NotScrollListView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.activity.LoginActivity;
import chanlytech.com.laborsupervision.activity.WebActivity;
import chanlytech.com.laborsupervision.adapter.QuestionAdapter;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.base.BaseFragment;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.config.ServerConfig;
import chanlytech.com.laborsupervision.entiy.BaseDataEntity;
import chanlytech.com.laborsupervision.entiy.DataEntity;
import chanlytech.com.laborsupervision.entiy.NewsEntity;
import chanlytech.com.laborsupervision.entiy.QuestionEntity;
import chanlytech.com.laborsupervision.entiy.UserEntity;
import chanlytech.com.laborsupervision.module.RightsMoudle;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.web.js.JsInterface;

/**
 * Created by Lyy on 2016/1/27.
 */
public class RightsFragment extends BaseFragment {
    //    @InjectView(R.id.que_list)
//    NotScrollListView que_list;
//    @InjectView(R.id.tishi)
//    TextView tv_tishi;
//    @InjectView(R.id.et_company_name)
//    EditText mEditText_name;
//    @InjectView(R.id.et_company_address)
//    EditText mEditText_address;
//    @InjectView(R.id.et_company_phone)
//    EditText mEditText_phone;
//    @InjectView(R.id.btn_update)
//    Button mButton_update;
//    @InjectView(R.id.scrollView)
//    ScrollView mScrollView;
//    private SpannableString msp = null;
//    private QuestionAdapter mQuestionAdapter;
//    private List<QuestionEntity> mQuestionEntities = new ArrayList<>();
//    private ImageView tempImag;
//    private QuestionEntity temquestionEntity;
//    private String questid = null;
//    private List<NewsEntity> newsEntities = new ArrayList<>();
//    private PopupWindow mPopupWindow;
//    private ProgressDialog mProgressDialog;
    @InjectView(R.id.task_web)
    WebView mWebView;
    private static final String APP_CACAHE_DIRNAME = "/webcache";

    public RightsFragment() {

    }

    public static RightsFragment newInstance() {
        return new RightsFragment();
    }




    @Override
    public void onResume() {
        super.onResume();
        initView();
//
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_rights;
    }

//    @Override
//    public RightsMoudle initModule() {
//        return new RightsMoudle(getActivity());
//    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }
    @SuppressLint("JavascriptInterface")
    private void initView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//        if (NetUtils.isConnected(getActivity())) {
//            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
//        } else {
//            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        }
//        // 开启 DOM storage API 功能
//        mWebView.getSettings().setDomStorageEnabled(true);
//        //开启 database storage API 功能
//        mWebView.getSettings().setDatabaseEnabled(true);
//        String cacheDirPath = getActivity().getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//        //String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
//        //设置数据库缓存路径
//        mWebView.getSettings().setDatabasePath(cacheDirPath);
//        //设置  Application Caches 缓存目录
//        mWebView.getSettings().setAppCachePath(cacheDirPath);
//        //开启 Application Caches 功能
//        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setUserAgentString("laodongjianguan/" + AppManager.getUser().getUserId() + "/UserType=" + AppManager.getUser().getUserType()+"/"+ mWebView.getSettings().getUserAgentString());
        mWebView.loadUrl(ServerConfig.BASE_URL + "task/task");
        mWebView.addJavascriptInterface(new JsInterface(getActivity(),ServerConfig.BASE_URL + "task/task"), "AppJsInterface");
        mWebView.setWebViewClient(new WebViewClient(){
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
        });

    }


}
