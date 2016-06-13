package chanlytech.com.laborsupervision.activity;

import java.io.File;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import chanlytech.com.laborsupervision.util.Constants;

/**
 * ��Ƭ���ɵ�Ŀ¼�� sd����/a/image/camera/.. .jpg
 * 
 * @author baozi
 * 
 */
public class UseCameraActivity extends Activity {
	private String mImageFilePath;
	public static final String IMAGEFILEPATH = "ImageFilePath";
	public final static String IMAGE_PATH = "image_path";
	static Activity mContext;
	public final static int GET_IMAGE_REQ = 5000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//判断 activity被销毁后 有没有数据被保存下来
		if (savedInstanceState != null) {

			mImageFilePath = savedInstanceState.getString(IMAGEFILEPATH);

			Log.i("savedInstanceState", mImageFilePath);

			File mFile = new File(IMAGEFILEPATH);
			if (mFile.exists()) {
				Intent rsl = new Intent();
				rsl.putExtra(IMAGE_PATH, mImageFilePath);
				setResult(Activity.RESULT_OK, rsl);
				Log.i("savedInstanceState", "图片拍摄成功");
				finish();
			} else {
				Log.i("savedInstanceState", "图片拍摄失败");
			}
		}

		mContext = this;
		if (savedInstanceState == null) {
			initialUI();
		}

	}

	public void initialUI() {
		long ts = System.currentTimeMillis();
		mImageFilePath = getCameraPath() +"IMG_"+ ts + ".jpg";
		File out = new File(mImageFilePath);
		showCamera(out);

	}

	private void showCamera(File out) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); // set
		startActivityForResult(intent, GET_IMAGE_REQ);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (GET_IMAGE_REQ == requestCode && resultCode == Activity.RESULT_OK) {
			Intent rsl = new Intent();
			rsl.putExtra(IMAGE_PATH, mImageFilePath);
			mContext.setResult(Activity.RESULT_OK, rsl);
			mContext.finish();
		} else {
			mContext.finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("ImageFilePath", mImageFilePath + "");

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

	}

	public static String getCameraPath() {
		return Constants.getSDPath()+ "/";
	}



}
