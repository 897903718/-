package chanlytech.com.laborsupervision.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.NetUtils;
import com.arialyy.frame.util.show.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.adapter.LegalProvisionsAdapter;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.BaseEntity;
import chanlytech.com.laborsupervision.entiy.BookEntity;
import chanlytech.com.laborsupervision.entiy.DataEntity;
import chanlytech.com.laborsupervision.entiy.NewsEntity;
import chanlytech.com.laborsupervision.module.LegalProvisionsModule;
import chanlytech.com.laborsupervision.widget.XListView;

/**
 * 法律条文
 */
public class LegalProvisionsActivity extends BaseActivity<LegalProvisionsModule> implements View.OnClickListener, XListView.IXListViewListener {
    @InjectView(R.id.new_list)
    XListView mNewsListView;
    @InjectView(R.id.title)
    TextView mTextView;
    @InjectView(R.id.back)
    ImageView imageView;
    private View headView;
    private List<NewsEntity> LegalEntities = new ArrayList<>();
    private int pagernumber = 0;
    private String bookdata = null;
    public static String keywordid = null, bookname = null, catname = null, catid = null, keyword = null;
    public static int state = 0;//用于判断是关键字搜索（1）还是条件筛选（2）
    public static boolean isEnter = true;//用于判断是否进入了详情页面 true为进入，false为没有进入
    private LegalProvisionsAdapter legalProvisionsAdapter;
    private String id="4";

    @Override
    public int setContentView() {
        return R.layout.activity_legal_provisions;
    }
    @Override
    public LegalProvisionsModule initModule() {
        return new LegalProvisionsModule(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        getModule().getLegal(id, pagernumber, "", "");//第一次加载
        getModule().FtBook();
        initView();
        initLinster();
    }

    private void initView(){
        mTextView.setText("法律条文");
        headView = View.inflate(this, R.layout.head_view, null);
        mNewsListView.addHeaderView(headView);
        legalProvisionsAdapter=new LegalProvisionsAdapter(this,LegalEntities);
        mNewsListView.setAdapter(legalProvisionsAdapter);
        mNewsListView.setXListViewListener(this);
        mNewsListView.setPullLoadEnable(true);
    }

    private void initLinster(){
        imageView.setOnClickListener(this);
    }

    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result){
            case ResultCode.LEGAL:
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    DataEntity dataEntity = JSON.parseObject(baseEntity.getData(), DataEntity.class);
                    if (dataEntity.getNews().size() > 0) {
                        if (pagernumber == 0) {
                            LegalEntities.clear();
                        }
                        mNewsListView.stopLoadMore();
                        mNewsListView.stopRefresh();
                        LegalEntities.addAll(dataEntity.getNews());

                        if (LegalEntities.size() <= 9) {
                            mNewsListView.setPullLoadEnable(false);
                        } else {
                            mNewsListView.setPullLoadEnable(true);
                        }
                        legalProvisionsAdapter.notifyDataSetChanged();
                    } else {
                        mNewsListView.stopLoadMore();
                        mNewsListView.stopRefresh();
                        mNewsListView.setPullLoadEnable(false);
                    }

                } else {
                    mNewsListView.stopLoadMore();
                    mNewsListView.stopRefresh();
                }
                break;
            case ResultCode.EORROR_CODE:
                T.showLong(this, data.toString());
                break;
            case ResultCode.FtBook:
                BaseEntity baseEntity1 = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity1.getStatus() == 1) {
                    bookdata = baseEntity1.getData();
                    List<BookEntity> bookEntities = JSON.parseArray(baseEntity1.getData(), BookEntity.class);
                    setHeadViewData(headView, bookEntities.get(0).getName(), bookEntities.get(0).getZhangjie().get(0).getName());
                }
                break;
        }
    }


    private void setHeadViewData(View headView, String bookname, String zhangname) {
        TextView tv_search = (TextView) headView.findViewById(R.id.tv_search);
        RelativeLayout rl_fa = (RelativeLayout) headView.findViewById(R.id.rl_fa);
        RelativeLayout rl_zhang = (RelativeLayout) headView.findViewById(R.id.rl_zhang);
        TextView tv_fa = (TextView) headView.findViewById(R.id.tv_fa);
        tv_fa.setText(bookname);
        TextView tv_zhang = (TextView) headView.findViewById(R.id.tv_zhang);
        tv_zhang.setText(zhangname);
        tv_search.setOnClickListener(this);
        rl_fa.setOnClickListener(this);
        rl_zhang.setOnClickListener(this);


    }

    private void setSearchData(View headView, String keyword) {
        TextView tv_keyword = (TextView) headView.findViewById(R.id.tv_keyword);
        tv_keyword.setText(keyword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
                Intent mIntent = new Intent(this, SearchActivity.class);
//                state = 1;
                startActivity(mIntent);
                break;
            case R.id.rl_fa:
                if (bookdata != null) {
                    Intent intent = new Intent(this, CodeSelectionActivity.class);
                    intent.putExtra("book", bookdata);
//                    state = 2;
                    startActivity(intent);
                }

                break;
            case R.id.rl_zhang:
                if (bookdata != null) {
                    Intent intent = new Intent(this, CodeSelectionActivity.class);
                    intent.putExtra("book", bookdata);
//                    state = 2;
                    startActivity(intent);
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isEnter){
            if (state == 1) {//关键字筛选
                if (keywordid != null) {
                    pagernumber = 0;
                    setSearchData(headView, keyword);
                    setHeadViewData(headView, "全部", "全部");
                    //这里的id可能要改成变量
                    getModule().getLegal(id, pagernumber, keywordid, "");
                }
            } else if (state == 2) {//条件筛选
                if (bookname != null && catname != null && catid != null) {
                    pagernumber = 0;
                    setSearchData(headView, "请选择关键字");
                    setHeadViewData(headView, bookname, catname);
                    getModule().getLegal(id, pagernumber, "", catid);
                }
            }
            isEnter=true;

        }

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetUtils.isConnected(LegalProvisionsActivity.this)) {
                    pagernumber = 0;
                    mNewsListView.setPullLoadEnable(true);
                    LegalEntities.clear();
                    if(state==0){
                        getModule().getLegal(id, pagernumber, "", "");
                    }else if (state == 1) {
                        getModule().getLegal(id, pagernumber, keywordid, "");
                    } else if (state == 2) {
                        getModule().getLegal(id, pagernumber, "", catid);
                    }

                } else {
                    T.showLong(LegalProvisionsActivity.this, "当前网络不可用");
                    legalProvisionsAdapter.notifyDataSetChanged();
                    mNewsListView.stopLoadMore();
                    mNewsListView.stopRefresh();
                }
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetUtils.isConnected(LegalProvisionsActivity.this)) {
                    pagernumber++;
                    if(state==0){
                        getModule().getLegal(id, pagernumber, "", "");
                    }else if (state == 1) {
                        getModule().getLegal(id, pagernumber, keywordid, "");
                    } else if (state == 2) {
                        getModule().getLegal(id, pagernumber, "", catid);
                    }
                } else {
                    T.showLong(LegalProvisionsActivity.this, "当前网络不可用");
                    mNewsListView.stopLoadMore();
                    mNewsListView.stopRefresh();
                }
            }
        }, 2000);
    }

}
