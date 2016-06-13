package chanlytech.com.laborsupervision.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.show.T;
import com.chanlytech.ui.widget.NotScrollListView;
import com.chanlytech.ui.widget.pullrefresh.IDataLoadListener;
import com.chanlytech.ui.widget.pullrefresh.PullToRefreshBase;
import com.chanlytech.ui.widget.pullrefresh.PullToRefreshScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.adapter.CommentAdapter;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.BaseDataEntity;
import chanlytech.com.laborsupervision.entiy.BaseEntity;
import chanlytech.com.laborsupervision.entiy.CommentEntity;
import chanlytech.com.laborsupervision.entiy.MessageEntity;
import chanlytech.com.laborsupervision.module.CaseAnalysisModule;
import chanlytech.com.laborsupervision.share.ShareHelper;

/**
 * 案列分析
 */
public class CaseAnalysisActivity extends BaseActivity<CaseAnalysisModule> implements View.OnClickListener,IDataLoadListener {
    @InjectView(R.id.iv_back)
    ImageView mImageView_back;
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.case_webview)
    WebView mWebView;
    @InjectView(R.id.tv_open)
    TextView tv_open;
    @InjectView(R.id.tv_comment)
    TextView tv_comment;
    @InjectView(R.id.list)
    NotScrollListView notScrollListView;
    @InjectView(R.id.btn_more_comment)
    Button btn_more_comment;
    @InjectView(R.id.btn_comment)
    Button btn_comment;
    @InjectView(R.id.scrollView)
    PullToRefreshScrollView scrollView;
    @InjectView(R.id.iv_share)
    ImageView mImageView_share;
    private PopupWindow mPopupWindow;
    private int pageIndex=1;
    private List<CommentEntity> commentEntities,tempCommentEntities;
    private CommentAdapter commentAdapter;
    private String id;
    private EditText mEditText_comment;
    private CommentEntity commentEntity;
    private boolean flag,state;
    private MessageEntity mMessageEntity;
    @Override
    public int setContentView() {
        return R.layout.activity_case_analysis;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
        BaseApplication.getAppLoctin().addActivity(this);

    }

    @Override
    public CaseAnalysisModule initModule() {
        return new CaseAnalysisModule(this);
    }

    private void initView() {
        id=getIntent().getStringExtra("id");
        commentEntities=new ArrayList<>();
        tempCommentEntities=new ArrayList<>();
        title.setText(getIntent().getStringExtra("title"));
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setUserAgentString("laodongjianguan/" + AppManager.getUser().getUserId() + "/UserType=" + AppManager.getUser().getUserType()+"/"+ mWebView.getSettings().getUserAgentString());
        mWebView.loadUrl(getIntent().getStringExtra("url"));
//        mWebView.setOverScrollMode(WebSettings.OVER_SCROLL_NEVER);
//        mWebView.setVerticalScrollbarOverlay(false);
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        commentAdapter=new CommentAdapter(this,commentEntities);
        notScrollListView.setAdapter(commentAdapter);
        getModule().getCommentList(id, pageIndex, 10);
        getModule().getShareInfo(id, "1");
    }

    private void initLinster() {
        mImageView_back.setOnClickListener(this);
        tv_open.setOnClickListener(this);
        btn_comment.setOnClickListener(this);
        btn_more_comment.setOnClickListener(this);
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                CaseAnalysisActivity.this.onRefresh(refreshView);
            }
        });
        mImageView_share.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_open:
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mWebView.setLayoutParams(params);
                tv_open.setVisibility(View.GONE);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_comment:
                if (AppManager.isLogin()){
                    showComment();
                }else {
                    Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_more_comment://更多评论
                pageIndex++;
                getModule().getCommentList(id,pageIndex,10);
                break;
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




    /**
     * 弹出评论的popwindow
     * */
    private void showComment(){
        View view=View.inflate(this,R.layout.comment_window,null);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        TextView tv_canle= (TextView) view.findViewById(R.id.tv_canly);
        TextView tv_send= (TextView) view.findViewById(R.id.tv_send);
        FrameLayout frameLayout= (FrameLayout) view.findViewById(R.id.framelayout);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
         mEditText_comment= (EditText) view.findViewById(R.id.et_comment);
        tv_canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditText_comment.getText().length()==0){
                    T.showLong(CaseAnalysisActivity.this,"评论内容不能为空");
                }else {
//                    flag=false;
//                    tempCommentEntities.clear();
//                    tempCommentEntities.addAll(commentEntities);
                    //提交评论
                    getModule().commentSub(id, mEditText_comment.getText().toString());
                    commentEntity=new CommentEntity();
                    commentEntity.setContent(mEditText_comment.getText().toString());
                    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd日-时");
                    Date data=new Date(System.currentTimeMillis());
                    String time = dateFormat.format(data);
                    commentEntity.setTime(time);
                    commentEntity.setHeadimg(AppManager.getUser().getAvatar());
                    mPopupWindow.dismiss();
//                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
                }
            }
        });
    }

    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result){
            case ResultCode.GET_COMMENT_LIST:
                scrollView.onRefreshComplete();
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus()==1){
                    if(baseEntity.getData()!=null){
                        BaseDataEntity baseDataEntity = JSON.parseObject(baseEntity.getData(), BaseDataEntity.class);
                        tv_comment.setText("评论" + "(" + baseDataEntity.getCount() + ")");
                        if(flag==true){
                            commentEntities.clear();
                            flag=false;
                        }
                        if(pageIndex==1){
                            commentEntities.clear();
                        }
                        if(baseDataEntity.getCommentlist().size()>0){
                            commentEntities.addAll(baseDataEntity.getCommentlist());
                            commentAdapter.notifyDataSetChanged();
                            state=false;
                            if(flag==true){
                                notScrollListView.setSelection(0);
                                flag=false;
                            }
                        }else {
                            btn_more_comment.setVisibility(View.GONE);
                            state=true;
                        }

                    }else {
                        btn_more_comment.setVisibility(View.GONE);
                    }

                }else {
                    ErrorToast.showError(this,baseEntity.getErrorCode());
                }
                break;
            case ResultCode.EORROR_CODE:
                scrollView.onRefreshComplete();
                T.showLong(this,data.toString());
                break;
            case ResultCode.COMMENT_SUB:
                BaseEntity baseEntity1 = JSON.parseObject(data.toString(), BaseEntity.class);
                if(baseEntity1.getStatus()==1){
                    BaseDataEntity baseDataEntity = JSON.parseObject(baseEntity1.getData(), BaseDataEntity.class);
                    T.showLong(this, "评论成功,审核通过后将会展示出来");
//                    flag=true;
//                    if(state==true){
//                        getModule().getCommentList(id,1,commentEntities.size()+1);
//                    }else {
//                        getModule().getCommentList(id,1,commentEntities.size());
//                    }




//                    c ommentEntity.setId(baseDataEntity.getComid());
//                    tempCommentEntities.add(commentEntity);
//                    tempCommentEntities.addAll(commentEntities);
//                    commentEntities.clear();
//                    commentEntities.addAll(tempCommentEntities);
//                    tempCommentEntities.clear();
//                    commentAdapter.notifyDataSetChanged();
                }else {
                    ErrorToast.showError(this, baseEntity1.getErrorCode());
                }
                break;
            case ResultCode.SHARE_CODE:
                BaseEntity baseEntity2 = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity2.getStatus() == 1) {
                    mImageView_share.setEnabled(true);
                    mMessageEntity = JSON.parseObject(baseEntity2.getData(), MessageEntity.class);
                } else {
                    mImageView_share.setEnabled(false);
                    ErrorToast.showError(this, baseEntity2.getErrorCode());
                }
                break;




        }
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        pageIndex=1;
        getModule().getCommentList(id,pageIndex,10);
    }

    @Override
    public void onLoadMore(int i) {

    }
}
