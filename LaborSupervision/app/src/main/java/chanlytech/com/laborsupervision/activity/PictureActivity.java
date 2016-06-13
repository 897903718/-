package chanlytech.com.laborsupervision.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.PictureUtil;

public class PictureActivity extends AppCompatActivity implements View.OnClickListener {
    private Uri uri;
    private Bitmap nBitmap;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Intent intent = getIntent();
        type=intent.getIntExtra("type",1);
        uri = intent.getData();
        Bitmap smallbitmap= PictureUtil.getSmallBitmap(uri.getPath());
        nBitmap=rotaingImageView(90,smallbitmap);
        ImageView btn_save = (ImageView) findViewById(R.id.btn_save);
        ImageView btn_del = (ImageView) findViewById(R.id.btn_del);
        ImageView pic = (ImageView) findViewById(R.id.iv_pic);
//        pic.setImageURI(uri);
        pic.setImageBitmap(nBitmap);
        btn_save.setOnClickListener(this);
        btn_del.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_del) {
            File file = new File(uri.getPath());
            file.delete();
            finish();
        }else if(v.getId()==R.id.btn_save){
            String path=saveScalePhoto(nBitmap);
            Intent mIntent=null;
            if(type==1){
                //律师帮助
                 mIntent=new Intent(this,LawyersToJoin.class);
                mIntent.putExtra("path",path);
                startActivity(mIntent);
                BaseApplication.getAppLoctin().killActivity();
                finish();
            }else if(type==2){
                //劳务派遣
                mIntent=new Intent(this,IWantToJoinActivity.class);
                mIntent.putExtra("path",path);
                startActivity(mIntent);
                BaseApplication.getAppLoctin().killActivity();
                finish();
            }else if(type==3){
                //用户认证
                mIntent=new Intent(this,IdentityAuthenActivity.class);
                mIntent.putExtra("path",path);
                startActivity(mIntent);
                BaseApplication.getAppLoctin().killActivity();
                finish();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        File file = new File(uri.getPath());
        file.delete();
        finish();
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
}
