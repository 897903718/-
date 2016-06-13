package chanlytech.com.laborsupervision.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.NetUtils;
import com.arialyy.frame.util.show.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.activity.CodeSelectionActivity;
import chanlytech.com.laborsupervision.activity.SearchActivity;
import chanlytech.com.laborsupervision.adapter.LegalProvisionsAdapter;
import chanlytech.com.laborsupervision.base.BaseFragment;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.BaseEntity;
import chanlytech.com.laborsupervision.entiy.BookEntity;
import chanlytech.com.laborsupervision.entiy.ColumnEntity;
import chanlytech.com.laborsupervision.entiy.DataEntity;
import chanlytech.com.laborsupervision.entiy.NewsEntity;
import chanlytech.com.laborsupervision.module.InformationModule;
import chanlytech.com.laborsupervision.module.LegalProvisionsModule;
import chanlytech.com.laborsupervision.widget.XListView;

/**
 * Created by Lyy on 2015/12/7.
 * 法律条文
 */
public class LegalProvisionsFragment extends BaseFragment<LegalProvisionsModule> implements View.OnClickListener, XListView.IXListViewListener {
    @InjectView(R.id.new_list)
    XListView mNewsListView;
    private View headView;
    private List<NewsEntity> LegalEntities = new ArrayList<>();
    private int pagernumber = 0;
    private ColumnEntity columnEntity;
    private String  bookdata = null;
    public static String keywordid = null, bookname = null, catname = null, catid = null, keyword = null;
    public static int state = 0;//用于判断是关键字搜索（1）还是条件筛选（2）
    public static boolean isEnter=true ;//用于判断是否进入了详情页面 true为进入，false为没有进入
    private LegalProvisionsAdapter legalProvisionsAdapter;
    @Override
    public int setContentView() {
        return R.layout.fragment_legal;
    }

    public static LegalProvisionsFragment newInstance(ColumnEntity ColumnEntity){
        LegalProvisionsFragment fragment=new LegalProvisionsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ColumnEntity", ColumnEntity);
        fragment.setArguments(bundle);
        return fragment;
    }

    public LegalProvisionsFragment(){

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        columnEntity = getArguments().getParcelable("ColumnEntity");
        getModule().getLegal(columnEntity.getId(), pagernumber, "", "");//第一次加载
        getModule().FtBook();
        initView();
    }

    @Override
    public LegalProvisionsModule initModule() {
        return new LegalProvisionsModule(getActivity());
    }

    private void initView(){
        headView = View.inflate(getActivity(), R.layout.head_view, null);
        mNewsListView.addHeaderView(headView);
        legalProvisionsAdapter=new LegalProvisionsAdapter(getActivity(),LegalEntities);
        mNewsListView.setAdapter(legalProvisionsAdapter);
        mNewsListView.setXListViewListener(this);
        mNewsListView.setPullLoadEnable(true);
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
                T.showLong(getActivity(), data.toString());
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
                Intent mIntent = new Intent(getActivity(), SearchActivity.class);
//                state = 1;
                startActivity(mIntent);
                break;
            case R.id.rl_fa:
                if (bookdata != null) {
                    Intent intent = new Intent(getActivity(), CodeSelectionActivity.class);
                    intent.putExtra("book", bookdata);
//                    state = 2;
                    startActivity(intent);
                }

                break;
            case R.id.rl_zhang:
                if (bookdata != null) {
                    Intent intent = new Intent(getActivity(), CodeSelectionActivity.class);
                    intent.putExtra("book", bookdata);
//                    state = 2;
                    startActivity(intent);
                }
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
                    getModule().getLegal("4", pagernumber, keywordid, "");
                }
            } else if (state == 2) {//条件筛选
                if (bookname != null && catname != null && catid != null) {
                    pagernumber = 0;
                    setSearchData(headView, "请选择关键字");
                    setHeadViewData(headView, bookname, catname);
                    getModule().getLegal("4", pagernumber, "", catid);
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
                if (NetUtils.isConnected(getActivity())) {
                    pagernumber = 0;
                    mNewsListView.setPullLoadEnable(true);
                    LegalEntities.clear();
                    if(state==0){
                        getModule().getLegal("4", pagernumber, "", "");
                    }else if (state == 1) {
                        getModule().getLegal("4", pagernumber, keywordid, "");
                    } else if (state == 2) {
                        getModule().getLegal("4", pagernumber, "", catid);
                    }

                } else {
                    T.showLong(getActivity(), "当前网络不可用");
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
                if (NetUtils.isConnected(getActivity())) {
                    pagernumber++;
                    if(state==0){
                        getModule().getLegal("4", pagernumber, "", "");
                    }else if (state == 1) {
                        getModule().getLegal("4", pagernumber, keywordid, "");
                    } else if (state == 2) {
                        getModule().getLegal("4", pagernumber, "", catid);
                    }
                } else {
                    T.showLong(getActivity(), "当前网络不可用");
                    mNewsListView.stopLoadMore();
                    mNewsListView.stopRefresh();
                }
            }
        }, 2000);
    }
}
