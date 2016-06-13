package chanlytech.com.laborsupervision.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
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
 * 律师加入
 */
public class LawyersToJoin extends BaseActivity<JoinModule> implements View.OnClickListener {
    private PopupWindow mPopupWindow;
    private Uri imageUri;
    protected static final int REQCAMERA = 11;
    private File file;
    private int width;
    @InjectView(R.id.title)
    TextView tv_title;
    @InjectView(R.id.et_name)
    EditText name;
    @InjectView(R.id.et_number)
    EditText number;
    @InjectView(R.id.et_company)
    EditText company;
    @InjectView(R.id.et_phone)
    EditText phone;
    @InjectView(R.id.et_jianjie)
    EditText jianjie;
    @InjectView(R.id.img)
    ImageView imageView;
    @InjectView(R.id.btn_sumbit)
    Button btn_sumbit;

    @Override
    public int setContentView() {
        return R.layout.activity_lawyers_to_join;
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
        initLinster();
        if (savedInstanceState == null) {
            creatWinds("服务参与者承诺：本人／机构保证其在加入本app人力资源服务超市，成为人力资源服务者时向app提交的相关资料的真实性，不做虚假宣传，并承担由此产生的一切法律责任。");

        }
        width = PublicTool.getDeviceWidth();
    }

    private void initLinster() {
        imageView.setOnClickListener(this);
        btn_sumbit.setOnClickListener(this);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img://选择图片
                if (mPopupWindow == null) {
                    KeyBoardUtils.closeKeybord(phone, this);
                    showPop();
                } else if (!mPopupWindow.isShowing()) {
                    KeyBoardUtils.closeKeybord(phone, this);
                    showPop();
                }
                break;
            case R.id.btn_sumbit://提交信息
                if (!Constants.isFastClick()) {
                    if (name.getText().length() == 0) {
                        T.showLong(this, "请输入您的姓名");
                        return;
                    }
                    if (!ValidateUtils.isPhoneNumber(phone.getText().toString().trim())) {
                        T.showLong(this, "请输入正确的手机号码");
                        return;
                    }
                    if (file == null) {
                        T.showLong(this, "请上传证件照");
                        return;
                    }
                    KeyBoardUtils.closeKeybord(phone, this);
                    getModule().LawyersToJoin(getIntent().getIntExtra("type", 1) + "", name.getText().toString(), number.getText().toString(), company.getText().toString(), phone.getText().toString(), jianjie.getText().toString(), file);
                    showLoadDialog("正在提交资料请耐心等待");
                    btn_sumbit.setOnClickListener(null);
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
                btn_sumbit.setOnClickListener(this);
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    T.showLong(this, "提交资料成功");
                    finish();
                } else {
                    ErrorToast.showError(this, baseEntity.getErrorCode());
                }
                break;
            case ResultCode.EORROR_CODE:
                btn_sumbit.setOnClickListener(this);
                dismissLoadDialog();
                T.showLong(this, data.toString());
                break;
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(getIntent().getStringExtra("path")!=null){
//            Bitmap mBitmap = PictureUtil.getSmallBitmap(getIntent().getStringExtra("path"));
//            int h = getModule().getHeight(getIntent().getStringExtra("path"));
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width - 40, h);
//            imageView.setLayoutParams(params);
//            imageView.setImageBitmap(mBitmap);
//            file = new File(getIntent().getStringExtra("path"));
//        }
//    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getStringExtra("path") != null) {
            Bitmap mBitmap = PictureUtil.getSmallBitmap(intent.getStringExtra("path"));
            int h = getModule().getHeight(intent.getStringExtra("path"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width - 40, h);
            imageView.setLayoutParams(params);
            imageView.setImageBitmap(mBitmap);
            file = new File(intent.getStringExtra("path"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
//        if (requestCode == 0) {//相册
//            Uri uri = data.getData();
//            if (uri != null) {//图片路径
//
//                String path = FileUtils.getPath(this, uri);
//                Bitmap mBitmap = PictureUtil.getSmallBitmap(path);
//                String filePath = getModule().saveScalePhoto(mBitmap);//保存图片
//                int h=getModule().getHeight(filePath);
//                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(width-40, h);
//                imageView.setLayoutParams(params);
//                imageView.setImageBitmap(mBitmap);
//                file = new File(filePath);
//            }
//
//        }
//        if (requestCode == 1) {//相机
//            Bitmap normbitmap = null;
//            Bitmap mBitmap = PictureUtil.getSmallBitmap(imageUri.getPath());
//            String filePath = getModule().saveScalePhoto(mBitmap);
//            String path = FileUtils.getPath(this, imageUri);
//            if (path != null) {
//                int degree = getModule().readPictureDegree(path);
//                if (mBitmap != null) {
//                    switch (degree) {
//                        case 0:
//                            getModule().saveScalePhoto(mBitmap);
//                            break;
//                        case 90:
//                            normbitmap = getModule().rotaingImageView(90, mBitmap);
//                            getModule().saveScalePhoto(normbitmap);
//                            break;
//                        case 180:
//                            normbitmap = getModule().rotaingImageView(180, mBitmap);
//                            getModule().saveScalePhoto(normbitmap);
//                            break;
//                        case 270:
//                            normbitmap = getModule().rotaingImageView(270, mBitmap);
//                            getModule().saveScalePhoto(normbitmap);
//                            break;
//                        case 360:
//                            getModule().saveScalePhoto(mBitmap);
//                            break;
//                    }
//                }
//                file = new File(filePath);
//                int h=getModule().getHeight(filePath);
//                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(width-40, h);
//                imageView.setLayoutParams(params);
//                imageView.setImageBitmap(mBitmap);
//            }
//        }

        switch (requestCode) {
            case REQCAMERA://拍照
//                Toast.makeText(this,"拍照回调路径"+imageUri.getPath(),Toast.LENGTH_LONG).show();
                if (imageUri != null) {
                    Bitmap mBitmap = PictureUtil.getSmallBitmap(imageUri.getPath());
                    String filepath = getModule().saveScalePhoto(mBitmap);
                    int h = getModule().getHeight(filepath);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width - 40, h);
                    imageView.setLayoutParams(params);
                    imageView.setImageBitmap(mBitmap);
                    file = new File(filepath);
                } else {
                    T.showLong(this, "获取拍照结果失败，请退出后重试");
                }

                break;
            case 0://图库
                if (data.getData() != null) {
                    Uri data2 = data.getData();
                    String path = FileUtils.getPath(LawyersToJoin.this, data2);
                    Bitmap bitmap = PictureUtil.getSmallBitmap(path);
                    String filePath = getModule().saveScalePhoto(bitmap);
                    int h1 = getModule().getHeight(filePath);
                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(width - 40, h1);
                    imageView.setLayoutParams(params1);
                    imageView.setImageBitmap(bitmap);
                    file = new File(path);
                }

                break;
        }
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
                    T.showLong(LawyersToJoin.this, "没有内存卡");
                    return;
                }

                if (AndroidUtils.getDeviceModel().equals("GT-I9300I")) {
                    //启动自定义相机
                    Intent mIntent = new Intent(LawyersToJoin.this, CameraActivity.class);
                    mIntent.putExtra("type", 1);
                    startActivity(mIntent);
                } else {
                    //系统自带相机
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                    //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    openCameraIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivityForResult(openCameraIntent, REQCAMERA);
                }

//                Intent intent = new Intent(LawyersToJoin.this, UseCameraActivity.class);
//                startActivityForResult(intent, REQCAMERA);
                mPopupWindow.dismiss();
            }
        });

        Button mButton_photo = (Button) view.findViewById(R.id.btn_photos);
        mButton_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //图库选择
                if (!Constants.isExistSD()) {
                    T.showLong(LawyersToJoin.this, "没有内存卡");
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

}
