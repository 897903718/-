package chanlytech.com.laborsupervision.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.show.L;
import com.arialyy.frame.util.show.T;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.RightsEntity;
import chanlytech.com.laborsupervision.module.RightsMoudle;
import chanlytech.com.laborsupervision.module.RightsProssModule;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.Spannable;

/**
 * 维权详情
 */
public class RightsDetailsActivity extends BaseActivity<RightsProssModule> implements View.OnClickListener {
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.tv_number)
    TextView tv_number;
    @InjectView(R.id.tv_time)
    TextView tv_time;
    @InjectView(R.id.tv_company_name)
    TextView tv_company_name;
    @InjectView(R.id.tv_comment_address)
    TextView tv_comment_address;
    @InjectView(R.id.tv_comment_phone)
    TextView tv_comment_phone;
    @InjectView(R.id.tv_rights_phone)
    TextView tv_rights_phone;
    @InjectView(R.id.tv_task_people)
    TextView tv_task_people;
    @InjectView(R.id.tv_appoint_people)
    TextView tv_appoint_people;
    @InjectView(R.id.tv_appoint_time)
    TextView tv_appoint_time;
    @InjectView(R.id.tv_question)
    TextView tv_question;
    @InjectView(R.id.btn_shouli)
    Button mButton;

    @Override
    public int setContentView() {
        return R.layout.activity_rights_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
        getModule().taskDetail(getIntent().getStringExtra("id"));
    }

    @Override
    public RightsProssModule initModule() {
        return new RightsProssModule(this);
    }

    private void initView(){
        title.setText("维权详情");

    }


    private void initLinster(){
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.btn_shouli:
                if(!Constants.isFastClick()){
                    getModule().proTask(getIntent().getStringExtra("id"));
                }
                break;
        }
    }


    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result){
            case ResultCode.TASK_DETAIL:
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if(baseEntity.getStatus()==1){
                    RightsEntity rightsEntity = JSON.parseObject(baseEntity.getData(), RightsEntity.class);
                    tv_number.setText(Spannable.matcherSearchTitle("维权编号:"+rightsEntity.getCode(),rightsEntity.getCode(),0xFF333333));
                    tv_time.setText(Spannable.matcherSearchTitle("提交时间:"+rightsEntity.getAddtime(),rightsEntity.getAddtime(),0xFF333333));
                    tv_company_name.setText(Spannable.matcherSearchTitle("单位名称:"+rightsEntity.getEnterpriseName(),rightsEntity.getEnterpriseName(),0xFF333333));
                    tv_comment_address.setText(Spannable.matcherSearchTitle("单位地址:"+rightsEntity.getAddress(),rightsEntity.getAddress(),0xFF333333));
                    tv_comment_phone.setText(Spannable.matcherSearchTitle("单位电话:"+rightsEntity.getPhone(),rightsEntity.getPhone(),0xFF333333));
                    tv_rights_phone.setText(rightsEntity.getUserphone());
                    tv_task_people.setText(rightsEntity.getZhi());
                    tv_appoint_people.setText(Spannable.matcherSearchTitle("任务指派人:"+rightsEntity.getAdmin(),rightsEntity.getAdmin(),0xFF333333));
                    tv_appoint_time.setText(Spannable.matcherSearchTitle("指派时间:"+rightsEntity.getTime(), rightsEntity.getTime(),0xFF333333));
                    tv_question.setText(Spannable.matcherSearchTitle("反映问题:"+rightsEntity.getProblemstr(), rightsEntity.getProblemstr(),0xFF333333));
                    mButton.setText(rightsEntity.getButtext());
                    if(rightsEntity.getIshandle()==0){
                        mButton.setVisibility(View.GONE);
                    }else {
                        mButton.setVisibility(View.VISIBLE);
                    }
                }else {
                    ErrorToast.showError(this,baseEntity.getErrorCode());
                }
                break;
            case ResultCode.EORROR_CODE:
                T.showLong(this,data.toString());
                break;
            case ResultCode.TASK_PRO:
                BaseEntity baseEntity1 = JSON.parseObject(data.toString(), BaseEntity.class);
                if(baseEntity1.getStatus()==1){
                    T.showLong(this,"处理成功");
                    finish();
                }else {
                    ErrorToast.showError(this,baseEntity1.getErrorCode());
                }
                L.d("维权状态提交",data.toString());
                break;
        }
    }
}
