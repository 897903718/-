package chanlytech.com.laborsupervision.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.show.T;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.BaseEntity;
import chanlytech.com.laborsupervision.module.FreeBackModule;

public class FreeBackActivity extends BaseActivity<FreeBackModule> implements View.OnClickListener {
    @InjectView(R.id.tv_title)
    TextView mTextView_title;
    @InjectView(R.id.iv_back)
    ImageView mImageView_back;
    @InjectView(R.id.submit)
    Button mButton;
    @InjectView(R.id.content)
    EditText mEditText;

    @Override
    public int setContentView() {
        return R.layout.activity_free_back;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
        BaseApplication.getAppLoctin().addActivity(this);
    }

    @Override
    public FreeBackModule initModule() {
        return new FreeBackModule(this);
    }

    private void initView() {
        mTextView_title.setText("意见反馈");
    }

    private void initLinster() {
        mImageView_back.setOnClickListener(this);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.submit:
                if (mEditText.getText().toString().equals("")) {
                    T.showLong(this, "请填写您的宝贵意见，再反馈给我们");
                } else {
                    getModule().submitFeedback(mEditText.getText().toString());
                }
//
                break;
        }
    }


    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.SUBMIT_FEEBACK:
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    T.showLong(this, "提交成功");
                    finish();
                } else {
                    T.showLong(this, baseEntity.getErrorMsg());
                }
                break;
        }
    }
}
