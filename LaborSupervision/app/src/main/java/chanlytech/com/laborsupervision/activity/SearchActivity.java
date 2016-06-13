package chanlytech.com.laborsupervision.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.KeyBoardUtils;
import com.arialyy.frame.util.show.T;

import java.io.BufferedReader;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.KeyWordEntity;
import chanlytech.com.laborsupervision.fragment.InformationFragment;
import chanlytech.com.laborsupervision.fragment.LegalProvisionsFragment;
import chanlytech.com.laborsupervision.module.SearchModule;
import chanlytech.com.laborsupervision.widget.EasyAutoLineView;
import chanlytech.com.laborsupervision.widget.XCFlowLayout;

public class SearchActivity extends BaseActivity<SearchModule> implements View.OnClickListener {
    @InjectView(R.id.back)
    ImageView iv_back;
    @InjectView(R.id.lineView)
    XCFlowLayout easyAutoLineView;
    @InjectView(R.id.btn_put)
    Button mButton_put;
    @InjectView(R.id.et_put)
    EditText mEditText;
    private String mNames[] = {
            "合同签订时间","合同签订","劳动合同类型","合同生效","工作时间计算","试用期","试用期工资","试用期违约","劳动合同变更",
            "调整岗位","合同补充","合同条款删减",
            "协商解除合同","辞职","开除","劳动合同终止","带薪年休假","经济补偿",
            "工伤补偿","赔偿金","赔偿金计算","违约金","双倍工资","工资","最低工资标准","加班工资","法定节假日",
            "加班时间","社会保险","劳动争议"
    };
    @Override
    public int setContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
    }

    @Override
    public SearchModule initModule() {
        return new SearchModule(this);
    }

    private void initView() {
        //自动隐藏软件盘
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getModule().keywordList();

    }

    private void initLinster() {
        iv_back.setOnClickListener(this);
        mButton_put.setOnClickListener(this);
    }

    private void initData(List<KeyWordEntity>keyWordEntities){
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 15;
        lp.rightMargin = 15;
        lp.topMargin = 15;
        lp.bottomMargin =15;
        for (int i = 0; i <keyWordEntities.size(); i++) {
            final TextView view = new TextView(this);
           final KeyWordEntity keyWordEntity = keyWordEntities.get(i);
            view.setText(keyWordEntity.getKeyword());
            view.setTextColor(Color.BLACK);
            easyAutoLineView.addView(view, lp);
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    LegalProvisionsActivity.keywordid=keyWordEntity.getId();
                    LegalProvisionsActivity.keyword=view.getText().toString();
                    LegalProvisionsActivity.isEnter=false;
                    LegalProvisionsActivity.state=1;
                    finish();
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_put:
                KeyBoardUtils.closeKeybord(mEditText, this);//关闭软键盘
                if(mEditText.getText().length()>0){
                    getModule().keywordGather(mEditText.getText().toString());
                }else {
                    T.showLong(this,"您还没有填写关键词呢！");
                }

                break;
        }
    }

    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result){
            case ResultCode.EORROR_CODE:
                T.showLong(this,data.toString());
                break;
            case ResultCode.keywordGather://提交返回
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if(baseEntity.getStatus()==1){
                    T.center(this,"您的需求已成功提交"+"\n"+"我们会改善关键字引导提示");
                }else {
                    ErrorToast.showError(this,baseEntity.getErrorCode());
                }
                break;
            case ResultCode.keywordList://关键字列表
                BaseEntity baseEntity1 = JSON.parseObject(data.toString(), BaseEntity.class);
                if(baseEntity1.getStatus()==1){
                    List<KeyWordEntity> keyWordEntities = JSON.parseArray(baseEntity1.getData(), KeyWordEntity.class);
                    initData(keyWordEntities);
                }else {
                    ErrorToast.showError(this,baseEntity1.getErrorCode());
                }
                break;
        }
    }
}
