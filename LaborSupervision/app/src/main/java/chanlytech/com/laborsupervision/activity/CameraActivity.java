package chanlytech.com.laborsupervision.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.widget.CameraPreview;
import chanlytech.com.laborsupervision.widget.FocusView;

/***
 * 自定义相机
 * */
public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    private CameraPreview cameraPreview;
    private SeekBar sb1;
    private int flashMode = -1;  //-1 auto  0 on  1off
    private RelativeLayout fl_preview;
    private FocusView mFocusView;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getAppLoctin().addOtherActivity(this);
        setContentView(R.layout.activity_camera);
        type=getIntent().getIntExtra("type",1);
        if (!checkCameraHardware()) {
            Toast.makeText(this, "没有检测到相机", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mFocusView= (FocusView) findViewById(R.id.view_focus);

        fl_preview = (RelativeLayout) findViewById(R.id.fl_preview);
        ImageView btn_capture = (ImageView) findViewById(R.id.iv_capture);
        ImageView iv_flash = (ImageView) findViewById(R.id.iv_flash);
        ImageView iv_return = (ImageView) findViewById(R.id.iv_return);
        iv_return.setOnClickListener(this);
        iv_flash.setOnClickListener(this);
        sb1 = (SeekBar) findViewById(R.id.sb1);
        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cameraPreview.setZoom(progress);
            }
        });
        btn_capture.setOnClickListener(this);
    }

    public boolean checkCameraHardware() {
        return getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }
    @Override
    protected void onResume() {
        sb1.setProgress(0);
        cameraPreview = new CameraPreview(this);
        cameraPreview.setFocusView(mFocusView);
        fl_preview.addView(cameraPreview);
        Camera.Parameters params = cameraPreview.getCameraParams();
        if(params==null){
            finish();
            Toast.makeText(this, "打开相机失败", Toast.LENGTH_SHORT).show();
        }else{
            int maxZoom = params.getMaxZoom();
            sb1.setMax(maxZoom);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        cameraPreview.releaseCamera();
        fl_preview.removeAllViews();
        cameraPreview=null;
        super.onPause();
    }
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {// 没有sd卡
                return;
            }

            File dir = new File(Environment.getExternalStorageDirectory() + "//laodongshebao//user_icon");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File pictureFile = new File(dir, System.currentTimeMillis() + ".jpg");
            try {
                pictureFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                //拍完预览
                Intent intent=new Intent(CameraActivity.this,PictureActivity.class);
                intent.putExtra("type",type);
                intent.setData(Uri.fromFile(pictureFile));
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };
    @Override
    public void onClick(View v) {
        Camera.Parameters params = cameraPreview.getCameraParams();
        Camera camera = cameraPreview.getCameraInstance();
        switch (v.getId()) {
            case R.id.iv_capture:
                // 照相
                camera.autoFocus(null);
                camera.takePicture(null, null, mPicture);
                break;
            case R.id.iv_flash://auto  on off 切换
                if (flashMode==-1) {//auto
                    ((ImageView)v).setImageResource(R.drawable.flash_on);
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                    flashMode=0;
                }else if(flashMode==0){//on
                    ((ImageView)v).setImageResource(R.drawable.flash_off);
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    flashMode=1;
                }else{
                    ((ImageView)v).setImageResource(R.drawable.flash_auto);
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                    flashMode=-1;
                }
                break;
            case R.id.iv_return:
                finish();
                break;
        }
        camera.setParameters(params);
    }
}
