package chanlytech.com.laborsupervision.fragment;

import android.os.Bundle;
import android.support.v4.widget.EdgeEffectCompat;
import android.view.View;
import android.widget.AdapterView;

import com.umeng.analytics.MobclickAgent;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.adapter.OcupationAdapter;
import chanlytech.com.laborsupervision.base.BaseFragment;
import chanlytech.com.laborsupervision.share.ShareHelper;
import chanlytech.com.laborsupervision.widget.XListView;

/**
 * Created by Lyy on 2015/8/31.
 * 职业体检
 */
public class OccupationTestFragment extends BaseFragment {
    @InjectView(R.id.ocupation_list)
    XListView mXListView;
    public OccupationTestFragment(){}
    public static OccupationTestFragment newInstance() {
        return new OccupationTestFragment();
    }
    @Override
    public int setContentView() {
        return R.layout.fragment_occupation_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
    }

    private void initView(){
        mXListView.setPullLoadEnable(false);
        mXListView.setPullRefreshEnable(false);
        mXListView.setAdapter(new OcupationAdapter(getActivity()));
    }

    private void initLinster(){
        mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ShareHelper shareHelper=new ShareHelper(getActivity());
//                shareHelper.showSharePop("分享测试","http://www.baidu.com","福利来了","https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/super/whfpf%3D425%2C260%2C50/sign=92d795510af3d7ca0ca36c3694228a3b/aa64034f78f0f736a5369e6e0c55b319eac41390.jpg");
            }
        });
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen"); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }

}
