package chanlytech.com.laborsupervision.activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.AndroidUtils;
import com.arialyy.frame.util.KeyBoardUtils;
import com.arialyy.frame.util.show.T;

import java.io.File;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.module.JoinModule;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.FileUtils;
import chanlytech.com.laborsupervision.util.PictureUtil;
import chanlytech.com.laborsupervision.util.PublicTool;
import chanlytech.com.laborsupervision.util.ValidateUtils;

/**
 * 我要加入
 */
public class IWantToJoinActivity extends BaseActivity<JoinModule> implements View.OnClickListener {
    @InjectView(R.id.title)
    TextView tv_title;
    @InjectView(R.id.et_name)
    EditText name;
    @InjectView(R.id.et_number)
    EditText number;
    @InjectView(R.id.et_company_number)
    EditText company_number;
    @InjectView(R.id.et_xingzhenghao)
    EditText xingzhenghao;
    @InjectView(R.id.et_xukejiguan)
    EditText xukejiguan;
    @InjectView(R.id.et_address)
    EditText address;
    @InjectView(R.id.et_phone)
    EditText phone;
    @InjectView(R.id.et_jianjie)
    EditText jianjie;
    @InjectView(R.id.img)
    ImageView mImageView;
    @InjectView(R.id.img1)
    ImageView mImageView1;
    @InjectView(R.id.default_img)
    ImageView default_img;
    @InjectView(R.id.btn_submit)
    Button mButton_submit;
    private int type;
    private File file1, file2;
    private PopupWindow mPopupWindow;
    private Uri imageUri;
    private int index;
    private int width;
    protected static final int REQCAMERA = 11;


    @Override
    public int setContentView() {
        return R.layout.activity_iwant_to_join;
    }

    @Override
    public JoinModule initModule() {
        return new JoinModule(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        BaseApplication.getAppLoctin().addActivity(this);
        tv_title.setText(getIntent().getStringExtra("title"));
        type = getIntent().getIntExtra("type", 1);
        switch (type) {
            case 2://劳务派遣
                default_img.setBackgroundResource(R.mipmap.lwpq1);
                break;
            case 3://培训机构
                default_img.setBackgroundResource(R.mipmap.pxjg);
                break;
            case 4://人力资源服务
                default_img.setBackgroundResource(R.mipmap.rlzy);
                break;
        }
        initLinster();
        if (savedInstanceState == null) {
            creatWinds("服务参与者承诺：本人／机构保证其在加入本app人力资源服务超市，成为人力资源服务者时向app提交的相关资料的真实性，不做虚假宣传，并承担由此产生的一切法律责任。");
        }
        width = PublicTool.getDeviceWidth();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void initLinster() {
        mImageView.setOnClickListener(this);
        mImageView1.setOnClickListener(this);
        mButton_submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img:
                if (mPopupWindow == null) {
                    index = 1;
                    KeyBoardUtils.closeKeybord(phone, this);
                    showPop();
                } else if (!mPopupWindow.isShowing()) {
                    index = 1;
                    KeyBoardUtils.closeKeybord(phone, this);
                    showPop();
                }
                break;
            case R.id.img1:
                if (mPopupWindow == null) {
                    index = 2;
                    KeyBoardUtils.closeKeybord(phone, this);
                    showPop();
                } else if (!mPopupWindow.isShowing()) {
                    index = 2;
                    KeyBoardUtils.closeKeybord(phone, this);
                    showPop();
                }
                break;
            case R.id.btn_submit://提交
                if (!Constants.isFastClick()) {
                    if (name.getText().length() == 0) {
                        T.showLong(this, "请输入机构名称");
                        return;
                    }
                    if (!ValidateUtils.isPhoneNumber(phone.getText().toString().trim())) {
                        T.showLong(this, "请输入正确的电话号码");
                        return;
                    }
                    if (file1 == null) {
                        T.showLong(this, "请上传营业执照");
                        return;
                    }
                    if (file2 == null) {
                        T.showLong(this, "请上传许可证");
                        return;
                    }
                    KeyBoardUtils.closeKeybord(phone, this);
                    getModule().JoinUs(type + "", name.getText().toString(),
                            number.getText().toString(), company_number.getText().toString(),
                            xingzhenghao.getText().toString(), xukejiguan.getText().toString().toString(),
                            address.getText().toString(), phone.getText().toString(),
                            jianjie.getText().toString(), file1, file2);
                    showLoadDialog("信息提交中....");
                    mButton_submit.setOnClickListener(null);

                }

                break;
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(getIntent().getStringExtra("path")!=null){
//            Bitmap bitmap1 = PictureUtil.getSmallBitmap(getIntent().getStringExtra("path"));
//            int h1 = getModule().getHeight(getIntent().getStringExtra("path"));
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width - 40, h1);
//            if (index == 1) {
//                file1 = new File(getIntent().getStringExtra("path"));
//                mImageView.setLayoutParams(params);
//                mImageView.setImageBitmap(bitmap1);
//            } else if (index == 2) {
//                file2 = new File(getIntent().getStringExtra("path"));
//                mImageView1.setLayoutParams(params);
//                mImageView1.setImageBitmap(bitmap1);
//            }
//        }
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getStringExtra("path")!=null){
            Bitmap bitmap1 = PictureUtil.getSmallBitmap(intent.getStringExtra("path"));
            int h1 = getModule().getHeight(intent.getStringExtra("path"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width - 40, h1);
            if (index == 1) {
                file1 = new File(intent.getStringExtra("path"));
                mImageView.setLayoutParams(params);
                mImageView.setImageBitmap(bitmap1);
            } else if (index == 2) {
                file2 = new File(intent.getStringExtra("path"));
                mImageView1.setLayoutParams(params);
                mImageView1.setImageBitmap(bitmap1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//        if (requestCode == 0) {//相册
//            Uri uri = data.getData();
//            if (uri != null) {//图片路径
//                String path = FileUtils.getPath(this, uri);
//                Bitmap mBitmap = PictureUtil.getSmallBitmap(path);
//                String filepath = getModule().saveScalePhoto(mBitmap);
//                int h=getModule().getHeight(filepath);
//                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(width-40, h);
//                if (index == 1) {
//                    file1 = new File(filepath);
//                    mImageView.setLayoutParams(params);
//                    mImageView.setImageBitmap(mBitmap);
//
//                } else if (index == 2) {
//                    file2 = new File(filepath);
//                    mImageView1.setLayoutParams(params);
//                    mImageView1.setImageBitmap(mBitmap);
//
//                }
//            }
//
//        }
//        if (requestCode == 1) {//相机
//            Bitmap normbitmap = null;
//            String filePath=null;
//            Bitmap mBitmap = PictureUtil.getSmallBitmap(imageUri.getPath());
//            filePath = getModule().saveScalePhoto(mBitmap);
//            String path = FileUtils.getPath(this, imageUri);
//            if (path != null) {
//                int degree = getModule().readPictureDegree(path);
//                if (mBitmap != null) {
//                    switch (degree) {
//                        case 0:
//                            filePath=getModule().saveScalePhoto(mBitmap);
//                            break;
//                        case 90:
//                            normbitmap = getModule().rotaingImageView(90, mBitmap);
//                            filePath=getModule().saveScalePhoto(normbitmap);
//                            break;
//                        case 180:
//                            normbitmap = getModule().rotaingImageView(180, mBitmap);
//                            filePath=getModule().saveScalePhoto(normbitmap);
//                            break;
//                        case 270:
//                            normbitmap = getModule().rotaingImageView(270, mBitmap);
//                            filePath=getModule().saveScalePhoto(normbitmap);
//                            break;
//                        case 360:
//                            filePath=getModule().saveScalePhoto(mBitmap);
//                            break;
//                    }
//                }
//                int h=getModule().getHeight(filePath);
//                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(width-40, h);
//                if (index == 1) {
//                    file1 = new File(filePath);
//                    mImageView.setLayoutParams(params);
//                    mImageView.setImageBitmap(mBitmap);
//                } else if (index == 2) {
//                    file2 = new File(filePath);
//                    mImageView1.setLayoutParams(params);
//                    mImageView1.setImageBitmap(mBitmap);
//                }
//            }
//
//        }
        switch (requestCode) {
            case REQCAMERA://拍照
//                String path1 = data.getStringExtra(UseCameraActivity.IMAGE_PATH);
//                Toast.makeText(IWantToJoinActivity.this,path1,Toast.LENGTH_LONG).show();
                if(imageUri!=null){
                    Bitmap bitmap1 = PictureUtil.getSmallBitmap(imageUri.getPath());
                    String filepath = getModule().saveScalePhoto(bitmap1);
                    int h1 = getModule().getHeight(filepath);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width - 40, h1);
                    if (index == 1) {
                        file1 = new File(filepath);
                        mImageView.setLayoutParams(params);
                        mImageView.setImageBitmap(bitmap1);
                    } else if (index == 2) {
                        file2 = new File(filepath);
                        mImageView1.setLayoutParams(params);
                        mImageView1.setImageBitmap(bitmap1);
                    }
                }else {
                    T.showLong(this,"获取拍照结果失败，请退出后重试");
                }

                break;
            case 0://图库
                if (data.getData()!= null) {
                    Uri data2 = data.getData();
                    String path = FileUtils.getPath(IWantToJoinActivity.this, data2);
                    Bitmap bitmap = PictureUtil.getSmallBitmap(path);
                    String filePath = getModule().saveScalePhoto(bitmap);
                    int h = getModule().getHeight(filePath);
                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(width - 40, h);
                    if (index == 1) {
                        file1 = new File(filePath);
                        mImageView.setLayoutParams(params1);
                        mImageView.setImageBitmap(bitmap);
                    } else if (index == 2) {
                        file2 = new File(filePath);
                        mImageView1.setLayoutParams(params1);
                        mImageView1.setImageBitmap(bitmap);
                    }
                }
                break;
        }
    }

    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.JOIN_LAWYER:
                dismissLoadDialog();
                mButton_submit.setOnClickListener(this);
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    T.showLong(this, "提交资料成功");
                    finish();
                } else {
                    ErrorToast.showError(this, baseEntity.getErrorCode());
                }
                break;
            case ResultCode.EORROR_CODE:
                mButton_submit.setOnClickListener(this);
                dismissLoadDialog();
                T.showLong(this, data.toString());
                break;
        }
    }

    private void showPop() {
        View view = View.inflate(this, R.layout.update_view, null);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        FrameLayout mFrameLayout = (FrameLayout) view.findViewById(R.id.framelayout);
        mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        Button mButton = (Button) view.findViewById(R.id.btn_camera);
//        if(AndroidUtils.getDeviceModel().equals("GT-I9300I")){
//            mButton.setVisibility(View.GONE);
//        }else {
//            mButton.setVisibility(View.VISIBLE);
//        }
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
                if (!Constants.isExistSD()) {
                    T.showLong(IWantToJoinActivity.this, "没有内存卡");
                    return;
                }
                if(AndroidUtils.getDeviceModel().equals("GT-I9300I")){
                    //启动自定义相机
                    Intent mIntent=new Intent(IWantToJoinActivity.this,CameraActivity.class);
                    mIntent.putExtra("type",2);
                    startActivity(mIntent);
                }else {
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                    //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(openCameraIntent, REQCAMERA);
                }

                mPopupWindow.dismiss();
            }
        });

        Button mButton_photo = (Button) view.findViewById(R.id.btn_photos);
        mButton_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //图库选择
                if (!Constants.isExistSD()) {
                    T.showLong(IWantToJoinActivity.this, "没有内存卡");
                    return;
                }
                Intent intentPick = new Intent(Intent.ACTION_PICK);
                intentPick.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentPick, 0);
                mPopupWindow.dismiss();
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

    /**
     * 免责声明弹窗
     */
    public void creatWinds(String conten) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("服务参与者承诺")
                .setMessage(conten)
                .setNegativeButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        //点击返回键不消失
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}
