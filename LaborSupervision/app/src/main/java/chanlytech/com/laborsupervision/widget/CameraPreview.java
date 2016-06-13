package chanlytech.com.laborsupervision.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import chanlytech.com.laborsupervision.util.Utils;


public class CameraPreview  extends SurfaceView implements SurfaceHolder.Callback, Camera.AutoFocusCallback {

	private Camera mCamera;
	private SurfaceHolder mHolder;
	private FocusView mFocusView;//聚焦视图
	@SuppressWarnings("deprecation")
	public CameraPreview(Context context) {
		super(context);
		mCamera=getCameraInstance();
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		setOnTouchListener(onTouchListener);
	}

	//获取Camera的实例
	public Camera getCameraInstance() {
		if (mCamera!=null) {
			return mCamera;
		}
		Camera c = null;
		try {
			c = Camera.open();
		} catch (Exception e) {
		}
		return c;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera = getCameraInstance();
			//设置camera的预览界面
			mCamera.setPreviewDisplay(holder);
			//开始预览
			mCamera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {
		if (holder.getSurface()==null) {
			return;
		}
		try {
			//停止预览
			mCamera.stopPreview();
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		releaseCamera();
	}

	public Parameters getCameraParams(){
		if (mCamera!=null) {
			//获取相机的参数
			mCamera.setDisplayOrientation(90);//设置相机旋转90
			return mCamera.getParameters();
		}
		return null;
	}

	//释放camera对象
	public void releaseCamera(){
		if (mCamera != null){
			mCamera.release();        // release the camera for other applications
			mCamera = null;
		}
	}

	public void setZoom(int zoom){
		if (mCamera==null) {
			return;
		}
		Parameters params = getCameraParams();
		params.setZoom(zoom);//设置焦距
		mCamera.setParameters(params);
	}


	/**
	 * 点击显示焦点区域
	 */
	OnTouchListener onTouchListener = new OnTouchListener() {
		@SuppressLint("NewApi") @SuppressWarnings("deprecation")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				int width = mFocusView.getWidth();
				int height = mFocusView.getHeight();
				mFocusView.setX(event.getX() - (width / 2));
				mFocusView.setY(event.getY() - (height / 2));
				mFocusView.beginFocus();
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				focusOnTouch(event);
			}
			return true;
		}
	};


	/**
	 * 设置焦点和测光区域
	 *
	 * @param event
	 */
	@SuppressLint("NewApi") @SuppressWarnings("deprecation")
	public void focusOnTouch(MotionEvent event) {

		int[] location = new int[2];
		RelativeLayout relativeLayout = (RelativeLayout)getParent();
		relativeLayout.getLocationOnScreen(location);
		Rect focusRect = Utils.calculateTapArea(mFocusView.getWidth(),
				mFocusView.getHeight(), 1f, event.getRawX(), event.getRawY(),
				location[0], location[0] + relativeLayout.getWidth(), location[1],
				location[1] + relativeLayout.getHeight());
		Rect meteringRect = Utils.calculateTapArea(mFocusView.getWidth(),
				mFocusView.getHeight(), 1.5f, event.getRawX(), event.getRawY(),
				location[0], location[0] + relativeLayout.getWidth(), location[1],
				location[1] + relativeLayout.getHeight());

		Parameters parameters = mCamera.getParameters();
		parameters.setFocusMode(Parameters.FOCUS_MODE_AUTO);

		if (parameters.getMaxNumFocusAreas() > 0) {
			List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
			focusAreas.add(new Camera.Area(focusRect, 1000));

			parameters.setFocusAreas(focusAreas);
		}

		if (parameters.getMaxNumMeteringAreas() > 0) {
			List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
			meteringAreas.add(new Camera.Area(meteringRect, 1000));

			parameters.setMeteringAreas(meteringAreas);
		}

		try {
			mCamera.setParameters(parameters);
		} catch (Exception e) {
		}
		mCamera.autoFocus(this);
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {

	}


	/**
	 * 设置聚焦的图片
	 * @param focusView
	 */
	public void setFocusView(FocusView focusView) {
		this.mFocusView = focusView;
	}

	/**
	 * 设置自动聚焦，并且聚焦的圈圈显示在屏幕中间位置
	 */
	@SuppressLint("NewApi") public void setFocus() {
		if(!mFocusView.isFocusing()) {
			try {
				mCamera.autoFocus(this);
				mFocusView.setX((Utils.getWidthInPx(getContext())-mFocusView.getWidth()) / 2);
				mFocusView.setY((Utils.getHeightInPx(getContext())-mFocusView.getHeight()) / 2);
				mFocusView.beginFocus();
			} catch (Exception e) {
			}
		}
	}
}
