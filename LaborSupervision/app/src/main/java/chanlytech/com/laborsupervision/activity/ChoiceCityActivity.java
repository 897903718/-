package chanlytech.com.laborsupervision.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.show.T;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.adapter.CityAdapter;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.BaseEntity;
import chanlytech.com.laborsupervision.entiy.CityEntity;
import chanlytech.com.laborsupervision.entiy.UserEntity;
import chanlytech.com.laborsupervision.module.ChoiceCityModule;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.SharedPreferData;

public class ChoiceCityActivity extends BaseActivity<ChoiceCityModule> implements View.OnClickListener {
    @InjectView(R.id.iv_back)
    ImageView mImageView_back;
    @InjectView(R.id.tv_title)
    TextView mTextView_title;
    @InjectView(R.id.city_list)
    ListView mListView;
    private CityAdapter mCityAdapter;
    private List<CityEntity> cityEntities = new ArrayList<CityEntity>();
    private UserEntity mUserEntity;
    private String flag;

    @Override
    public int setContentView() {
        return R.layout.activity_choice_city;
    }

    @Override
    public ChoiceCityModule initModule() {
        return new ChoiceCityModule(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        showLoadDialog("加载中...");
        getModule().getCityList();
        initView();
        flag = getIntent().getStringExtra("flag");
        BaseApplication.getAppLoctin().addActivity(this);
        mUserEntity = getIntent().getParcelableExtra("user");

    }

    private void initView() {
        mImageView_back.setOnClickListener(this);
        mTextView_title.setText("选择区县");
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (cityEntities != null && cityEntities.size() > 0) {
                    if (flag.equals("0")) {
                        SharedPreferData.writeStringdata(ChoiceCityActivity.this, "city", cityEntities.get(position).getName());
//                        Intent mIntent = new Intent(ChoiceCityActivity.this, MainActivity.class);
//                        startActivity(mIntent);
                        Constants.state_address=true;
                        UserEntity userEntity = AppManager.getUser();
                        userEntity.setCityId(cityEntities.get(position).getCityId());
                        userEntity.setAddress(cityEntities.get(position).getName());
                        AppManager.saveUser(userEntity);
                        finish();
                    } else if (flag.equals("1")) {
                        Intent mIntent = new Intent(ChoiceCityActivity.this, UserInfoActivity.class);
                        Constants.state_address=true;
                        Constants.flag_state=true;
//                        mUserEntity.setCityId(cityEntities.get(position).getCityId());
                        mIntent.putExtra("city", cityEntities.get(position));
                        mIntent.putExtra("userEntity", mUserEntity);
                        mIntent.putExtra("flag", flag);
                        startActivity(mIntent);
//                        AppManager.saveUser(mUserEntity);
                        finish();
                    }

                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_back:
                if (flag.equals("0")) {
//                    SharedPreferData.writeStringdata(ChoiceCityActivity.this, "city", cityEntities.get(position).getName());
//                    Intent mIntent = new Intent(ChoiceCityActivity.this, MainActivity.class);
//                    AppManager.getUser().setCityId(cityEntities.get(position).getCityId());
//                    startActivity(mIntent);
                    finish();
                } else if (flag.equals("1")) {
                    Intent mIntent = new Intent(ChoiceCityActivity.this, UserInfoActivity.class);
//                    mIntent.putExtra("city", cityEntities.get(position));
                    mIntent.putExtra("userEntity", mUserEntity);
                    mIntent.putExtra("flag", flag);
                    startActivity(mIntent);
                    finish();
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (flag.equals("0")) {
//                    SharedPreferData.writeStringdata(ChoiceCityActivity.this, "city", cityEntities.get(position).getName());
//            Intent mIntent = new Intent(ChoiceCityActivity.this, MainActivity.class);
//            startActivity(mIntent);
            finish();
        } else if (flag.equals("1")) {
            Intent mIntent = new Intent(ChoiceCityActivity.this, UserInfoActivity.class);
//                    mIntent.putExtra("city", cityEntities.get(position));
            mIntent.putExtra("userEntity", mUserEntity);
            mIntent.putExtra("flag", flag);
            startActivity(mIntent);
            finish();
        }
    }

    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.GET_CITY_LIST:
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    cityEntities = JSON.parseArray(baseEntity.getData(), CityEntity.class);
                    mCityAdapter = new CityAdapter(this, cityEntities);
                    mListView.setAdapter(mCityAdapter);
                    dismissLoadDialog();
                } else {
                    T.showLong(this, baseEntity.getErrorMsg());
                    dismissLoadDialog();
                }
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
