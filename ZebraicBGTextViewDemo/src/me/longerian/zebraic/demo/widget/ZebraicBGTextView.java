package me.longerian.zebraic.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class ZebraicBGTextView extends TextView {
	
	private static final int COLOR_NORMAL = 0XFFE5E2D2;
	private static final int COLOR_HIGHLIGHT = 0XFF76C631;
	private Rect mRect;
    private Paint mPaint;

	public ZebraicBGTextView(Context context) {
		super(context);
		init();
	}
	
	public ZebraicBGTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public ZebraicBGTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		 mRect = new Rect();
         mPaint = new Paint();
         mPaint.setStyle(Paint.Style.FILL);
	}

	@Override
    protected void onDraw(Canvas canvas) {
        int count = getLineCount();
        Rect r = mRect;
        Paint paint = mPaint;
        for (int i = 0; i < count; i++) {
        	if(i % 2 == 0) {
        		paint.setColor(COLOR_NORMAL);
        	} else {
        		paint.setColor(COLOR_HIGHLIGHT);
        	}
            int baseline = getLineBounds(i, r);
            canvas.drawRect(r, paint);
//            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
        }

        super.onDraw(canvas);
    }
	
}
