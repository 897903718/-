package chanlytech.com.laborsupervision.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.show.L;
import com.arialyy.frame.util.show.T;
import com.umeng.analytics.MobclickAgent;

import android.view.ViewGroup.LayoutParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.BaseEntity;
import chanlytech.com.laborsupervision.entiy.CityEntity;
import chanlytech.com.laborsupervision.entiy.UserEntity;
import chanlytech.com.laborsupervision.module.UserInfoModule;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.Imageload;
import chanlytech.com.laborsupervision.util.ValidateUtils;

//修改个人资料界面
public class UserInfoActivity extends BaseActivity<UserInfoModule> implements View.OnClickListener {
    @InjectView(R.id.rl_chose_icon)
    RelativeLayout mRelativeLayout_icon;
    @InjectView(R.id.iv_user_icon)
    ImageView iv_img;
    @InjectView(R.id.rl_chose_user_type)
    RelativeLayout mRelativeLayout_chose_type;
    @InjectView(R.id.tv_user_type)
    TextView mTextView_user_type;
    @InjectView(R.id.et_user_nickname)
    EditText mEditText_nickname;
    @InjectView(R.id.et_user_email)
    EditText mEditText_email;
    @InjectView(R.id.rl_chose_address)
    RelativeLayout mRelativeLayout_chose_address;
    @InjectView(R.id.tv_save)
    TextView mTextView_save;
    @InjectView(R.id.iv_back)
    ImageView mImageView_back;
    @InjectView(R.id.tv_user_address)
    TextView mTextView_tv_address;
    @InjectView(R.id.rl_change_pwd)
    RelativeLayout mLayout_chang;
    @InjectView(R.id.rl_update_user)
    RelativeLayout mRelativeLayout;
    private PopupWindow mPopupWindow, mPopupWindow_type;
    private Intent mIntent;
    private final static int REQUEST_CODE_1 = 1111; //拍照
    private final static int REQUEST_CODE_2 = 2222;
    private final static int REQUEST_CODE_3 = 3333;
    private final static int CROP_X = 150;
    private final static int CROP_Y = 150;//56
    private static Uri mUri;
    private static String mImageName = "laodongshebao" + System.currentTimeMillis() + ".png"; // 文件名;
    private String sdPath = Constants.getSDPath();
    private Button mButton_peopel, mButton_company, mButton_organization, mButton_business;
    private int index = 1;
    private String city_id, city_name;
    private UserEntity mUserEntity;
    private int type=1;
    private String flag;
    private boolean state;//判断邮箱是否修改
    private  CityEntity cityEntity=new CityEntity();
    //图片存储路径/storage/sdcard0/edianfa/user_icon/edianfa1437445148391.png
    @Override

    public int setContentView() {
        return R.layout.activity_user_info;
    }

    @Override
    public UserInfoModule initModule() {
        return new UserInfoModule(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initLinster();
        initView();
        BaseApplication.getAppLoctin().addActivity(this);


    }

    private void initView() {
        mUserEntity = new UserEntity();
        mUserEntity = (UserEntity) getIntent().getParcelableExtra("userEntity");
        if(mUserEntity.getUserType().length()>0){
            type=Integer.parseInt(mUserEntity.getUserType());
        }else {
            type=1;
        }
        if(mUserEntity.getUserType().equals("1")){
            mTextView_user_type.setText("普通用户");
//            mRelativeLayout.setVisibility(View.VISIBLE);
        }else if(mUserEntity.getUserType().equals("2")){
            mTextView_user_type.setText("企业用户");
//            mRelativeLayout.setVisibility(View.GONE);
        }else if(mUserEntity.getUserType().equals("3")){
            mTextView_user_type.setText("内部人员");
        }
        flag=getIntent().getStringExtra("flag");
        mEditText_nickname.setText(mUserEntity.getNickName());
        if(mUserEntity.getMail().equals("")||mUserEntity.getMail()==null){
            mEditText_email.setHint("点击修改邮箱");
        }else {
            mEditText_email.setText(mUserEntity.getMail());
        }
        if (mUserEntity.getAddress().equals("")||mUserEntity.getAddress()==null){
            mTextView_tv_address.setText("高新区");
        }else {
            mTextView_tv_address.setText(mUserEntity.getAddress());
        }
        Imageload.loadImageRound(this, 100, mUserEntity.getAvatar(), iv_img);

    }


    private void initLinster() {
        mImageView_back.setOnClickListener(this);
        mRelativeLayout_icon.setOnClickListener(this);
        mRelativeLayout_chose_type.setOnClickListener(this);
        mTextView_save.setOnClickListener(this);
        mLayout_chang.setOnClickListener(this);
        mRelativeLayout_chose_address.setOnClickListener(this);
        mRelativeLayout.setOnClickListener(this);
        mEditText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                state = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                state = true;
            }
        });
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
//                if (flag.equals("0")){
//                    Intent mIntent=new Intent(this,MainActivity.class);
//                    startActivity(mIntent);
//                    finish();
//                }else if (flag.equals("1")){
//                    finish();
//                }
                break;
            case R.id.rl_chose_icon:
                if(mPopupWindow==null){
                    showChoseIconPopWindow();
                }else if(!mPopupWindow.isShowing()){
                    showChoseIconPopWindow();
                }

                break;
            case R.id.rl_chose_user_type:
                if(mUserEntity.getUserType().equals("1")){
                    if(mPopupWindow_type==null){
                        showChoseUserType();
                    }else if(!mPopupWindow_type.isShowing()) {
                        showChoseUserType();
                    }

                }

                //showChoseUserType();
                break;
            case R.id.tv_save://保存用户信息
                if(state){
                    if(!ValidateUtils.isMail(mEditText_email.getText().toString().trim())){
                        T.showLong(this,"邮箱格式不正确");
                        return;
                    }
                }

                showLoadDialog("正在提交用户信息");
                getModule().modifyUserInfo(mEditText_nickname.getText().toString().trim(), type, mEditText_email.getText().toString().trim(), city_name, city_id);
                break;
            case R.id.rl_change_pwd://修改密码
                mIntent = new Intent(this, ChangePwdActivity.class);
                startActivity(mIntent);
                break;
            case R.id.rl_chose_address:
                mIntent = new Intent(this, ChoiceCityActivity.class);
                mIntent.putExtra("user",mUserEntity);
                mIntent.putExtra("flag",flag);
                startActivity(mIntent);
                finish();
                break;
            case R.id.rl_update_user:
                Intent mIntent=new Intent(this,IdentityAuthenActivity.class);
                startActivity(mIntent);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (getIntent().getParcelableExtra("city") != null) {
             cityEntity = getIntent().getParcelableExtra("city");
            mTextView_tv_address.setText(cityEntity.getName());
            city_id = cityEntity.getCityId();
            city_name = cityEntity.getName();
        } else {
            city_id = mUserEntity.getCityId();
            city_name = mUserEntity.getAddress();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onResume(this);
    }
    //弹出popWind
    private void showChoseIconPopWindow() {
        View view = View.inflate(this, R.layout.chose_user_icon, null);
        mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//        if(!mPopupWindow.isShowing()){
//
//        }
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        FrameLayout mFrameLayout = (FrameLayout) view.findViewById(R.id.framelayout);
        mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();

            }
        });
        Button mButton = (Button) view.findViewById(R.id.btn_camera);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
                if (!Constants.isExistSD()){
                    T.showLong(UserInfoActivity.this,"没有内存卡");
                    return;
                }
                mPopupWindow.dismiss();
                File sdcardTempFile = new File(sdPath, mImageName);
                mUri = Uri.fromFile(sdcardTempFile);
                Intent intentImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentImage.addCategory(Intent.CATEGORY_DEFAULT);
                intentImage.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                startActivityForResult(intentImage, REQUEST_CODE_1);

            }
        });

        Button mButton_photo = (Button) view.findViewById(R.id.btn_photos);
        mButton_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //图库选择
                if (!Constants.isExistSD()) {
                    T.showLong(UserInfoActivity.this, "没有内存卡");
                    return;
                }
                mPopupWindow.dismiss();
                Intent intentPick = new Intent(Intent.ACTION_PICK);
                intentPick.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentPick, REQUEST_CODE_2);

            }
        });

        Button mButton_canle = (Button) view.findViewById(R.id.btn_canle);
        mButton_canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                getStartPhotoZoom(requestCode, data);
                break;
        }
    }

    //裁剪图片
    private void startPhotoZoom(Uri uri) {
        Intent intentPic = new Intent("com.android.camera.action.CROP");
        intentPic.setDataAndType(uri, "image/*");
        intentPic.putExtra("crop", "true");
        intentPic.putExtra("aspectX", 1);
        intentPic.putExtra("aspectY", 1);
        intentPic.putExtra("outputX", CROP_X);
        intentPic.putExtra("outputY", CROP_Y);
        intentPic.putExtra("return-data", true);
        startActivityForResult(intentPic, REQUEST_CODE_3);
    }

    private void getStartPhotoZoom(int requestCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_1:
                startPhotoZoom(mUri);
                break;
            case REQUEST_CODE_2:
                startPhotoZoom(data.getData());
                break;
            case REQUEST_CODE_3:
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
    }

    //获取图片路径
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Constants.IMAGE_PATH = write(photo);
            Bitmap bitmap = BitmapFactory.decodeFile(Uri.parse(Constants.IMAGE_PATH).toString());
            Bitmap createCircleImage = getRoundBitmap(bitmap, 100);
            iv_img.setImageBitmap(createCircleImage);
            File file = new File(Constants.IMAGE_PATH);
            getModule().updateAvatar(file);
            L.i("选择的图片路径", Constants.IMAGE_PATH);

        }
    }

    //存入图片
    private String write(Bitmap bm) {
        File file = new File(Constants.getSDPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Constants.getSDPath(), mImageName);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            out.write(Bitmap2Bytes(bm));
            out.flush();
            out.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);//png类型
        return baos.toByteArray();
    }


    /**
     * 获取圆角矩形图片方法
     *
     * @param bitmap
     * @param roundPx,圆角的角度
     * @return Bitmap
     * @author wanshaoxun
     */
    private Bitmap getRoundBitmap(Bitmap bitmap, int roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        //		int x = bitmap.getWidth();
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    //账户类型弹出窗
    private void showChoseUserType() {
        View mView = View.inflate(this, R.layout.chose_user_type, null);
        FrameLayout mFrameLayout = (FrameLayout) mView.findViewById(R.id.framelayout);
        mButton_peopel = (Button) mView.findViewById(R.id.btn_people);
        mButton_company = (Button) mView.findViewById(R.id.btn_company);
        mButton_organization = (Button) mView.findViewById(R.id.btn_organization);
        mButton_business = (Button) mView.findViewById(R.id.btn_business);
        btn_check(type);
        mPopupWindow_type = new PopupWindow(mView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        mPopupWindow_type.setOutsideTouchable(true);
        mPopupWindow_type.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow_type.showAtLocation(mView, Gravity.CENTER, 0, 0);
        mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow_type.dismiss();
            }
        });
        mButton_peopel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn_check(0);
                    mTextView_user_type.setText("普通用户");
                    type=1;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mPopupWindow_type.dismiss();
                }

                return false;
            }
        });
        mButton_company.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mPopupWindow_type.dismiss();
                    getModule().dialogPop();
//                    btn_check(1);
//                    mTextView_user_type.setText("企业用户");
//                    type=2;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mPopupWindow_type.dismiss();
                }
                return false;
            }
        });
        mButton_organization.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn_check(2);
                    mTextView_user_type.setText("机构");
                    type=3;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mPopupWindow_type.dismiss();
                }
                return false;
            }
        });
        mButton_business.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn_check(3);
                    type=4;
                    mTextView_user_type.setText("个体工商户");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mPopupWindow_type.dismiss();
                }
                return false;
            }
        });

    }

    //初始化按钮
    private void btn_check(int i) {
        switch (i) {
            case 1:
                mButton_peopel.setTextColor(getResources().getColor(R.color.black));
                mButton_company.setTextColor(getResources().getColor(R.color.line_gray));
                mButton_organization.setTextColor(getResources().getColor(R.color.line_gray));
                mButton_business.setTextColor(getResources().getColor(R.color.line_gray));
                index = 1;
                break;
            case 2:
                mButton_peopel.setTextColor(getResources().getColor(R.color.line_gray));
                mButton_company.setTextColor(getResources().getColor(R.color.black));
                mButton_organization.setTextColor(getResources().getColor(R.color.line_gray));
                mButton_business.setTextColor(getResources().getColor(R.color.line_gray));
                index = 2;
                break;
            case 3:
                mButton_peopel.setTextColor(getResources().getColor(R.color.line_gray));
                mButton_company.setTextColor(getResources().getColor(R.color.line_gray));
                mButton_organization.setTextColor(getResources().getColor(R.color.black));
                mButton_business.setTextColor(getResources().getColor(R.color.line_gray));
                index = 3;
                break;
            case 4:
                mButton_peopel.setTextColor(getResources().getColor(R.color.line_gray));
                mButton_company.setTextColor(getResources().getColor(R.color.line_gray));
                mButton_organization.setTextColor(getResources().getColor(R.color.line_gray));
                mButton_business.setTextColor(getResources().getColor(R.color.black));
                index = 4;
                break;
        }
    }


    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.UPDATE_USER_ICON:
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    UserEntity userEntity = JSON.parseObject(baseEntity.getData(), UserEntity.class);
                    AppManager.saveUser(userEntity);
                    T.showLong(this, "上传头像成功");
                } else {
                    T.showLong(this, baseEntity.getErrorMsg());
                }
                break;
            case ResultCode.MODIFY_USER_INFO:
                BaseEntity baseEntity1 = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity1.getStatus() == 1) {
                    UserEntity userEntity = JSON.parseObject(baseEntity1.getData(), UserEntity.class);
                    if( Constants.flag_state){
                        userEntity.setCityId(cityEntity.getCityId());
                        Constants.flag_state=false;
                    }
                    AppManager.saveUser(userEntity);
                    T.showLong(this, "修改成功");

                    dismissLoadDialog();
                    if(flag.equals("0")){
                        Intent mIntent=new Intent(this,MainActivity.class);
                        startActivity(mIntent);
                        finish();
                    }else if(flag.equals("1")){
//                        Intent mIntent=new Intent(this,MainActivity.class);
//                        startActivity(mIntent);
                        finish();
                    }
                } else {
                    dismissLoadDialog();
                    T.showLong(this, baseEntity1.getErrorMsg());
                }
                break;
        }
    }


//    @Override
//    public void onBackPressed() {
//        if (flag.equals("0")){
//            Intent mIntent=new Intent(this,MainActivity.class);
//            startActivity(mIntent);
//            finish();
//        }else if (flag.equals("1")){
//            finish();
//        }
//    }
}
