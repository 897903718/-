package chanlytech.com.laborsupervision.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 *
 * Created by Lyy on 2015/9/6.
 */
public class MyView extends View {
    private float x1=0.0f,y1=100.f,x3=220.0f,y3=100.0f;
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3.0f);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        final Path path=new Path();
        path.moveTo(0.0f, 1000.0f);//起始位置
//        final float x2 = (200.0f + 100.f) / 2;
//        final float y2 = (200.0f + 100.f) / 10;
        path.quadTo(x3, y3, -200.f, -800.0f);
        canvas.drawPath(path, paint);
    }
}
