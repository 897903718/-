package chanlytech.com.laborsupervision.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.arialyy.frame.util.show.T;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.adapter.ReservationAdapter;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;

public class MyReservationActivity extends BaseActivity implements View.OnClickListener {
/**
 * 我的预约
 * */
    @InjectView(R.id.iv_back)
    ImageView iv_back;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.exlistview)
    ExpandableListView expandableListView;
    private ReservationAdapter adapter;
    private int[] group_checked = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    @Override
    public int setContentView() {
        return R.layout.activity_my_reservation;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initLinster();
        initView();
        BaseApplication.getAppLoctin().addActivity(this);
    }

    private void initView(){
        tv_title.setText("我的预约");
        // 设置默认图标为不显示状态
        expandableListView.setGroupIndicator(null);
//        int width = getWindowManager().getDefaultDisplay().getWidth();
//        expandableListView.setIndicatorBounds(width-40, width-10);
        adapter=new ReservationAdapter(this);
        expandableListView.setAdapter(adapter);
        // 设置一级item点击的监听器
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
//                group_checked[groupPosition] = group_checked[groupPosition] + 1;
                // 刷新界面
//                ((ReservationAdapter) adapter).notifyDataSetChanged();
                T.showLong(MyReservationActivity.this,"第"+groupPosition+"展开");
                return false;
            }
        });


    }

    private void initLinster(){
        iv_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }





}
