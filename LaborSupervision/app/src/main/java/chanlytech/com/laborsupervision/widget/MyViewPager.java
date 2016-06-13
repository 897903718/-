package chanlytech.com.laborsupervision.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {
	private boolean state=false;
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyViewPager(Context context) {
		super(context);
	}
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if(state){
			return super.onTouchEvent(arg0);
		}
		return false;
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if(state){
			return super.onInterceptTouchEvent(arg0);
		}
		return false;
	}
	public void setPagerEnabled(boolean state){
		this.state=state;
	}
}
