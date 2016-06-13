package chanlytech.com.laborsupervision.module;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.preference.DialogPreference;

import com.arialyy.frame.util.AndroidUtils;
import com.arialyy.frame.util.show.L;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.config.ServerConfig;
import chanlytech.com.laborsupervision.http.Encryption;
import chanlytech.com.laborsupervision.http.LoadDatahandler;
import chanlytech.com.laborsupervision.http.MyAsyncHttpResponseHandler;
import chanlytech.com.laborsupervision.http.MyHttpClient;
import chanlytech.com.laborsupervision.http.ServerUtil;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.PublicTool;

/**
 * Created by Lyy on 2016/4/19.
 */
public class JoinModule extends BaseModule {
    private Context mContext;

    public JoinModule(Context context) {
        super(context);
        this.mContext = context;
    }

    /**
     * 加入我们----律师
     */
    public void LawyersToJoin(String type, String name, String code1, String agency, String phone, String brief, final File file) {
        RequestParams params = new RequestParams();
        String randomCode = Encryption.getRandomCode();
        //添加通用参数
        params.put("timestamp", randomCode);
        params.put("sign", Encryption.getICityCode(randomCode));
        params.put("userId", AppManager.getUser().getUserId());
        params.put("version", AndroidUtils.getVersionName(mContext) + "");
        params.put("versionCode", AndroidUtils.getVersionCode(mContext) + "");
        params.put("device", "0");
        params.put("cityId", AppManager.getUser().getCityId());
        params.put("type", type);//类型--律师
        params.put("name", name);//名字
        params.put("code1", code1);//职业证号
        params.put("agency", agency);//所属律所
        params.put("phone", phone);//电话
        params.put("brief", brief);//简介
        try {
            params.put("img1", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyHttpClient.getInstance(mContext).post(ServerConfig.BASE_URL + ServerConfig.LAWYERS_TO_JOIN, params, new MyAsyncHttpResponseHandler(new LoadDatahandler() {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                L.i("律师加入", data);
                L.i("图片文件大小", file.length() + "");
                callback(ResultCode.JOIN_LAWYER, data);
            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
                L.i("律师加入", message);
            }
        }));
    }

    /**
     * 加入我们----律师
     */
    public void JoinUs(String type, String name, String code1, String code2, String code3, String agency, String address, String phone, String brief, final File file, File file1) {
        RequestParams params = new RequestParams();
        String randomCode = Encryption.getRandomCode();
        //添加通用参数
        params.put("timestamp", randomCode);
        params.put("sign", Encryption.getICityCode(randomCode));
        params.put("userId", AppManager.getUser().getUserId());
        params.put("version", AndroidUtils.getVersionName(mContext) + "");
        params.put("versionCode", AndroidUtils.getVersionCode(mContext) + "");
        params.put("device", "0");
        params.put("cityId", AppManager.getUser().getCityId());
        params.put("type", type);//类型--律师
        params.put("name", name);//名字
        params.put("code1", code1);//统一社会信用代码
        params.put("code2", code2);//企业注册号
        params.put("code3", code3);//行政许可证号
        params.put("agency", agency);//所属律所
        params.put("address", address);//地址
        params.put("phone", phone);//电话
        params.put("brief", brief);//简介
        try {
            params.put("img1", file);
            params.put("img2", file1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyHttpClient.getInstance(mContext).post(ServerConfig.BASE_URL + ServerConfig.LAWYERS_TO_JOIN, params, new MyAsyncHttpResponseHandler(new LoadDatahandler() {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                L.i("其他加入", data);
                L.i("图片文件大小", file.length() + "");
                callback(ResultCode.JOIN_LAWYER, data);
            }

            @Override
            public void onFailure(String error, String message) {
                super.onFailure(error, message);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
                L.i("其他加入失败", message);
            }
        }));
    }

    /**
     * 保存图片
     */
    public static String saveScalePhoto(Bitmap bitmap) {
        String fileName = ""; // 照片完整路径  
        if (bitmap != null) {
            // 文件夹路径  
            String pathUrl = Constants.getSDPath() + "/";
            String imageName = "IMG_" + System.currentTimeMillis() + ".jpg";
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
        return fileName;
    }

/**
 * 根据图片路径获取图片的宽高比在屏幕中动态显示图片
 * */
    public static int getHeight(String filePath){
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
     * 质量压缩
     *
     * @param image 原始的bitmap
     *              return 压缩后的bitmap
     */
    public Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 图片按比例大小压缩方法（根据路径获取图片并压缩）
     *
     * @param srcPath 图片的路径
     *                return 压缩后的bitmap
     */
    public Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 图片按比例大小压缩方法（根据Bitmap图片压缩）
     *
     * @param Bitmap bitmap(原始的bitmap)
     *               return bitmap(返回压缩后的bitmap)
     */
    public Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
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

    /**
     *     * 旋转图片
     *     * @param angle
     *     * @param bitmap
     *     * @return Bitmap
     *     
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作  
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片  
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }
}
