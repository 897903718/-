package chanlytech.com.laborsupervision.activity;


import android.os.Bundle;
import android.widget.TextView;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.BaseActivity;

/**
 * 认证通知
 */
public class CertificationNoticeActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.tv_issuccess)
    TextView tv_state;

    @Override
    public int setContentView() {
        return R.layout.activity_certification_notice;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
      String objid=getIntent().getStringExtra("objid");
        if(objid!=null){
            if(objid.equals("1")){
                tv_state.setText("您的身份认证已通过认证");
            }else {
                tv_state.setText("您的身份认证未通过认证");
            }
        }
        initView();
    }

    private void initView(){
        title.setText("身份认证通知");
    }
}
