package chanlytech.com.laborsupervision.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.KeyBoardUtils;
import com.arialyy.frame.util.show.L;
import com.arialyy.frame.util.show.T;
import com.chanlytech.ui.widget.NotScrollListView;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.adapter.QuestionAdapter;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.BaseDataEntity;
import chanlytech.com.laborsupervision.entiy.DataEntity;
import chanlytech.com.laborsupervision.entiy.NewsEntity;
import chanlytech.com.laborsupervision.entiy.QuestionEntity;
import chanlytech.com.laborsupervision.module.RightsMoudle;
import chanlytech.com.laborsupervision.util.Constants;

/**
 * 维权帮助
 * */
public class RightsActivity extends BaseActivity<RightsMoudle> implements View.OnClickListener {
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.back)
    ImageView mImageView;
    @InjectView(R.id.que_list)
    NotScrollListView que_list;
    @InjectView(R.id.tishi)
    TextView tv_tishi;
    @InjectView(R.id.et_company_name)
    EditText mEditText_name;
    @InjectView(R.id.et_company_address)
    EditText mEditText_address;
    @InjectView(R.id.et_company_phone)
    EditText mEditText_phone;
    @InjectView(R.id.btn_update)
    Button mButton_update;
    @InjectView(R.id.scrollView)
    ScrollView mScrollView;
    private SpannableString msp = null;
    private QuestionAdapter mQuestionAdapter;
    private List<QuestionEntity> mQuestionEntities = new ArrayList<>();
    private ImageView tempImag;
    private QuestionEntity temquestionEntity;
    private String questid = null;
    private List<NewsEntity> newsEntities = new ArrayList<>();
    private PopupWindow mPopupWindow;
    private ProgressDialog mProgressDialog;


    @Override
    public int setContentView() {
        return R.layout.activity_rights;
    }

    @Override
    public RightsMoudle initModule() {
        return new RightsMoudle(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
        getModule().WeiQuan();
    }

    private void initView() {
        //自动隐藏软件盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        title.setText("维权帮助");
        msp = new SpannableString("温馨提示：若您想反映的问题不在上述选项中，请返回本平台首页查看劳动保障监察、劳动仲裁咨询电话寻求帮助。");
        int firsStar = msp.toString().indexOf("劳");
        int firstEnd = msp.toString().indexOf("察");
        int dierEnd = msp.toString().indexOf("咨");
        //劳动保障监察
        msp.setSpan(new Clickable(1), firsStar, firstEnd + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置点击
        msp.setSpan(new ForegroundColorSpan(0xFF1396F0), firsStar, firstEnd + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //设置背景色为青色
        msp.setSpan(new UnderlineSpan(), firsStar, firstEnd + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置下划线
        //劳动仲裁
        msp.setSpan(new Clickable(2), firstEnd + 2, dierEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置点击
        msp.setSpan(new ForegroundColorSpan(0xFF1396F0), firstEnd + 2, dierEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //设置背景色为青色
        msp.setSpan(new UnderlineSpan(), firstEnd + 2, dierEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置下划线
        tv_tishi.setText(msp);
        tv_tishi.setMovementMethod(LinkMovementMethod.getInstance());
    }
    private void initLinster() {
        mButton_update.setOnClickListener(this);
        que_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QuestionEntity questionEntity = mQuestionEntities.get(position);
                ImageView imageView = (ImageView) view.findViewById(R.id.check);
                if (tempImag != null) {
                    tempImag.setBackgroundResource(R.mipmap.ic_check);
//                    tempImag.setVisibility(View.GONE);
                }
                imageView.setBackgroundResource(R.mipmap.ic_no_check);
                tempImag = imageView;
                if (temquestionEntity != null) {
                    temquestionEntity.setIsCheck(1);
                }
                questionEntity.setIsCheck(0);
                temquestionEntity = questionEntity;
                mQuestionAdapter.notifyDataSetChanged();
                questid = questionEntity.getId();
            }
        });
        mEditText_name.setOnClickListener(this);
        mEditText_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = mEditText_name.getText().toString();
                String str = StringFilter(editable.toString());
                if (!editable.equals(str)) {
                    mEditText_name.setText(str);
                    mEditText_name.setSelection(str.length()); //光标置后
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditText_address.setOnClickListener(this);
        mEditText_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = mEditText_address.getText().toString();
                String str = StringFilter(editable.toString());

                if (!editable.equals(str)) {
                    mEditText_address.setText(str);
                    mEditText_address.setSelection(str.length()); //光标置后
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditText_phone.setOnClickListener(this);
        mEditText_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = mEditText_phone.getText().toString();
                String str = StringFilter(editable.toString());

                if (!editable.equals(str)) {
                    mEditText_phone.setText(str);
                    mEditText_phone.setSelection(str.length()); //光标置后
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update://判断是否登录
                if(!Constants.isFastClick()){
                    if(AppManager.isLogin()){

                        if (questid == null) {
                            T.showLong(this, "请选择您的问题");
                            return;
                        }
                        if (mEditText_name.getText().length() == 0) {
                            T.showLong(this, "请输入单位全称");
                            return;
                        }
                        if (mEditText_address.getText().length() == 0) {
                            T.showLong(this, "请输入单位实际办公地址");
                            return;
                        }
                        if (mEditText_phone.getText().length() == 0) {
                            T.showLong(this, "请输入单位电话");
                            return;
                        }
                        KeyBoardUtils.closeKeybord(mEditText_phone, this);
                        mProgressDialog=new ProgressDialog(this);
                        mProgressDialog.setMessage("信息提交中....");
                        mProgressDialog.show();
                        getModule().tijiao(questid,mEditText_name.getText().toString(),mEditText_address.getText().toString(),mEditText_phone.getText().toString());
                    }else {
                        Intent mIntent=new Intent(this, LoginActivity.class);
                        startActivity(mIntent);
                    }
                }
                break;
            case R.id.et_company_name:
                mScrollView.scrollTo(0,50);
                break;
            case R.id.et_company_phone:
                mScrollView.scrollTo(0,100);
                break;
            case R.id.et_company_address:
                mScrollView.scrollTo(0,150);
                break;
            case R.id.back:
                finish();
                break;
        }
    }


    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.WEIQUAN:
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    BaseDataEntity baseData = JSON.parseObject(baseEntity.getData(), BaseDataEntity.class);
                    mQuestionEntities = baseData.getOption();
                    newsEntities = baseData.getLinkobj();
                    mQuestionAdapter=new QuestionAdapter(this, mQuestionEntities);
                    que_list.setAdapter(mQuestionAdapter);
                }
                L.d("维权问题", data.toString());
                break;
            case ResultCode.TIJIAO:
                if(mProgressDialog!=null){
                    if(mProgressDialog.isShowing()){
                        mProgressDialog.dismiss();
                    }
                }
                BaseEntity dataEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if(dataEntity.getStatus()==1){
                    questid=null;
                    mEditText_address.setText("");
                    mEditText_name.setText("");
                    mEditText_phone.setText("");
                    for (int i=0;i<mQuestionEntities.size();i++){
                        if(mQuestionEntities.get(i)!=null){
                            mQuestionEntities.get(i).setIsCheck(1);
                        }
                    }
                    mQuestionAdapter.notifyDataSetChanged();
                    DataEntity dataEntity1 = JSON.parseObject(dataEntity.getData(), DataEntity.class);
                    successWind(dataEntity1.getInfo());
                }else {
                    ErrorToast.showError(this, dataEntity.getErrorCode());
                }
                break;
            case ResultCode.EORROR_CODE:
                if(mProgressDialog!=null){
                    if(mProgressDialog.isShowing()){
                        mProgressDialog.dismiss();
                    }
                }
                T.showLong(this,data.toString());
                break;
        }
    }

    /**
     * 提交成功弹窗
     * */
    private void successWind(String string){
        View view = View.inflate(this, R.layout.success_wind_view, null);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        Button mbtn = (Button) view.findViewById(R.id.btn_ok);
        TextView textView= (TextView) view.findViewById(R.id.tv_content);
        textView.setText(string);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }


    class Clickable extends ClickableSpan {
        private int type;

        public Clickable(int type) {
            super();
            this.type = type;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.text_gray_color));
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View v) {

            if (type == 1) {
                if (newsEntities.get(0) != null) {
                    Intent mIntent = new Intent(RightsActivity.this, WebActivity.class);
                    mIntent.putExtra("title", "成都市劳动监察机构地址及举报投诉电话");
                    mIntent.putExtra("url", newsEntities.get(0).getUrl());
                    mIntent.putExtra("objid", newsEntities.get(0).getId());
                    mIntent.putExtra("type", 1);
                    startActivity(mIntent);
                } else {
                    T.showLong(RightsActivity.this, "抱歉没有跳转链接");
                }

            } else {
                if (newsEntities.get(1) != null) {
                    Intent mIntent = new Intent(RightsActivity.this, WebActivity.class);
                    mIntent.putExtra("title", "成都市劳动人事争议仲裁委机构电话");
                    mIntent.putExtra("url", newsEntities.get(1).getUrl());
                    mIntent.putExtra("objid", newsEntities.get(1).getId());
                    mIntent.putExtra("type", 1);
                    startActivity(mIntent);
                } else {
                    T.showLong(RightsActivity.this, "抱歉没有跳转链接");
                }

            }
        }
    }
    //过滤特殊字符
    private static String StringFilter(String str)throws PatternSyntaxException {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？～]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
