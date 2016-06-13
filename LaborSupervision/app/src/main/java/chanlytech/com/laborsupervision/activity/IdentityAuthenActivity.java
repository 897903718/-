package chanlytech.com.laborsupervision.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.AndroidUtils;
import com.arialyy.frame.util.KeyBoardUtils;
import com.arialyy.frame.util.show.T;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.module.IdentityAuthenModule;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.FileUtils;
import chanlytech.com.laborsupervision.util.PictureUtil;
import chanlytech.com.laborsupervision.util.PublicTool;
import chanlytech.com.laborsupervision.util.ValidateUtils;

/**
 * 身份认证
 */
public class IdentityAuthenActivity extends BaseActivity<IdentityAuthenModule> implements View.OnClickListener {
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.et_name)
    EditText et_name;
    @InjectView(R.id.et_code)
    EditText et_code;
    @InjectView(R.id.et_company_name)
    EditText et_company_name;
    @InjectView(R.id.btn_submit)
    Button mBtn_submit;
    @InjectView(R.id.ll_renli)
    LinearLayout mLayout_renli;
    @InjectView(R.id.ll_no_renli)
    LinearLayout mLayout_no_renli;
    @InjectView(R.id.image1)
    ImageView iv_img1;
    @InjectView(R.id.image2)
    ImageView iv_img2;
    @InjectView(R.id.img)
    ImageView imageView;
    private PopupWindow mPopupWindow;
    private static final int SCALE =3;//照片缩小比例
    private Uri imageUri;
    private File mFile;
    private String type=null;// 1 人力资源管理 2非人力资源管理
    private int width;
    @Override
    public int setContentView() {
        return R.layout.activity_identity_authen;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
    }

    @Override
    public IdentityAuthenModule initModule() {
        return new IdentityAuthenModule(this);
    }

    private void initView() {
        title.setText("身份认证");
        width=PublicTool.getDeviceWidth();
    }

    private void initLinster() {
        mBtn_submit.setOnClickListener(this);
        imageView.setOnClickListener(this);
        mLayout_renli.setOnClickListener(this);
        mLayout_no_renli.setOnClickListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_submit://提交
                if (et_name.getText().length() == 0) {
                    T.showLong(this, "请输入您的姓名");
                    return;
                }

                if(et_code.getText().length()<6||et_code.getText().length()>6){
                    T.showLong(this, "请输入身份证后六位");
                    return;
                }

                if (et_company_name.getText().length() == 0) {
                    T.showLong(this, "请输入您公司的名称");
                    return;
                }
                if(type==null){
                    T.showLong(this, "请选择岗位类型");
                    return;
                }
                if(mFile==null){
                    T.showLong(this,"请选择营业执照照片");
                }
                getModule().userUpgradeReq(et_name.getText().toString().trim(), et_code.getText().toString().trim()
                        , et_company_name.getText().toString().trim(),type,
                        mFile);
                showLoadDialog("正在提交资料请耐心等待");
                break;
            case R.id.img:
                KeyBoardUtils.closeKeybord(et_company_name, this);//关闭软键盘
                showPop();
                break;
            case R.id.ll_no_renli://非人力资源
                type="2";
                iv_img2.setBackgroundResource(R.mipmap.ic_check);
                iv_img1.setBackgroundResource(R.mipmap.ic_no_check);
                KeyBoardUtils.closeKeybord(et_company_name, this);//关闭软键盘
                break;
            case R.id.ll_renli://人力
                type="1";
                iv_img1.setBackgroundResource(R.mipmap.ic_check);
                iv_img2.setBackgroundResource(R.mipmap.ic_no_check);
                KeyBoardUtils.closeKeybord(et_company_name, this);//关闭软键盘
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
                    T.showLong(IdentityAuthenActivity.this, "没有内存卡");
                    return;
                }
                if (AndroidUtils.getDeviceModel().equals("GT-I9300I")) {
                    //启动自定义相机
                    Intent mIntent=new Intent(IdentityAuthenActivity.this,CameraActivity.class);
                    mIntent.putExtra("type",3);
                    startActivity(mIntent);
                } else {
                    //系统自带相机
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                    //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    openCameraIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivityForResult(openCameraIntent, 1);
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
                    T.showLong(IdentityAuthenActivity.this, "没有内存卡");
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(getIntent().getStringExtra("path")!=null){
//            Bitmap mBitmap = PictureUtil.getSmallBitmap(getIntent().getStringExtra("path"));
//            int h = getHeight(getIntent().getStringExtra("path"));
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width - 40, h);
//            imageView.setLayoutParams(params);
//            imageView.setImageBitmap(mBitmap);
//            mFile = new File(getIntent().getStringExtra("path"));
//        }
//    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getStringExtra("path")!=null){
            Bitmap mBitmap = PictureUtil.getSmallBitmap(intent.getStringExtra("path"));
            int h = getHeight(intent.getStringExtra("path"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width - 40, h);
            imageView.setLayoutParams(params);
            imageView.setImageBitmap(mBitmap);
            mFile = new File(intent.getStringExtra("path"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 0) {//相册
//            ContentResolver resolver = getContentResolver();
//            Uri uri = data.getData();
//            if (uri != null) {//图片路径
//                try {
//
//                    String path = FileUtils.getPath(this, uri);
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
////                    File file=new File(path);
//                    Bitmap newBitmap = zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
//                    bitmap.recycle();
//                    saveScalePhoto(newBitmap);
//                    imageView.setImageBitmap(newBitmap);
//                    mFile=new File(Constants.getSDPath()+"/"+"IMG_2015.jpg");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

            if(data.getData()!=null){
                String path = FileUtils.getPath(IdentityAuthenActivity.this, data.getData());
                Bitmap bitmap=PictureUtil.getSmallBitmap(path);
                saveScalePhoto(bitmap);
                int h=getHeight(Constants.getSDPath()+"/"+"IMG_2015.jpg");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width - 20, h);
                imageView.setLayoutParams(params);
                imageView.setImageBitmap(bitmap);
                mFile=new File(Constants.getSDPath()+"/"+"IMG_2015.jpg");
            }

        }
        if (requestCode == 1) {//相机
//            Bitmap normbitmap = null;
//            ContentResolver contentResolver = getContentResolver();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
//                Bitmap newBitmap = zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
//                //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
//                bitmap.recycle();
//                String path = FileUtils.getPath(this, imageUri);
//                if (path != null) {
//
//                    int degree = readPictureDegree(path);
//                    if (newBitmap != null) {
//                        switch (degree) {
//                            case 0:
//                                saveScalePhoto(newBitmap);
//                                break;
//                            case 90:
//                                normbitmap = rotaingImageView(90, newBitmap);
//                                saveScalePhoto(normbitmap);
//                                break;
//                            case 180:
//                                normbitmap = rotaingImageView(180, newBitmap);
//                                saveScalePhoto(normbitmap);
//                                break;
//                            case 270:
//                                normbitmap = rotaingImageView(270, newBitmap);
//                                saveScalePhoto(normbitmap);
//                                break;
//                            case 360:
//                                saveScalePhoto(newBitmap);
//                                break;
//                        }
//                    }
//
//                    mFile=new File(Constants.getSDPath()+"/"+"IMG_2015.jpg");
//                    imageView.setImageBitmap(newBitmap);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            if(imageUri!=null){
                Bitmap mBitmap = PictureUtil.getSmallBitmap(imageUri.getPath());
                saveScalePhoto(mBitmap);
                int h = getHeight(Constants.getSDPath() + "/" + "IMG_2015.jpg");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width - 40, h);
                imageView.setLayoutParams(params);
                imageView.setImageBitmap(mBitmap);
                mFile = new File(Constants.getSDPath()+"/"+"IMG_2015.jpg");
            }else {
                T.showLong(this,"获取拍照结果失败，请退出后重试");
            }

        }
    }


    // 根据路径获得图片并压缩，返回bitmap用于显示
//    public static Bitmap getSmallBitmap(String filePath) {
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(filePath, options);
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, 480, 800);
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeFile(filePath, options);
//    }

    //计算图片的缩放值
//    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//            final int heightRatio = Math.round((float) height / (float) reqHeight);
//            final int widthRatio = Math.round((float) width / (float) reqWidth);
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//        }
//        return inSampleSize;
//    }
    private   int getHeight(String filePath){
        Bitmap bitmap=BitmapFactory.decodeFile(filePath);
        int width= PublicTool.getDeviceWidth();
        double size= Double.valueOf(bitmap.getWidth())/Double.valueOf(bitmap.getHeight());
        double height=Double.valueOf(width - 40)/size;
        int h;
        h=(int)height;
        bitmap.recycle();
        return h;
    }
    /**
     *  
     *      * 存储缩放的图片 
     *      * @param data 图片数据 
     *      
     */
    private void saveScalePhoto(Bitmap bitmap) {
        if (bitmap != null) {
            // 照片全路径  
            String fileName = "";
            // 文件夹路径  
            String pathUrl = Constants.getSDPath() + "/";
            String imageName = "IMG_2015.jpg";
            FileOutputStream fos = null;
            File file = new File(pathUrl);
            file.mkdirs();// 创建文件夹  
            fileName = pathUrl + imageName;
            try {
                fos = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Resize the bitmap
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
//        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    /**
     * 根据路径来判断图片的方向
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
    * 旋转图片
    * @param angle
    * @param bitmap
    * @return Bitmap
    */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作  
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片  
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }


    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.UPDATE_USER_REQ:
                dismissLoadDialog();
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    T.showLong(this, "提交资料成功，请等待后台审核");
                    finish();
                } else {
                    ErrorToast.showError(this, baseEntity.getErrorCode());
                }
                break;
        }
    }
}
