package me.longerian.abcandroid.listsidebar;

import me.longerian.abcandroid.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
public class SideBar extends View {
	
	public SideBar(Context context) {
		super(context);
	}
	
	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		return false;
	}
	
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(0xFFD6ECFA);
		Bitmap upArrow = BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_up);
		Bitmap downArrow = BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_down);
		int barWidth = getWidth();
		int barHeight = getHeight();
		int arrowWidth = upArrow.getWidth();
		int arrowHeight = upArrow.getWidth();
		canvas.drawBitmap(upArrow, (barWidth - arrowWidth) / 2, 10, null);
		canvas.drawBitmap(downArrow, (barWidth - arrowWidth) / 2, barHeight - 10 - arrowHeight, null);
		super.onDraw(canvas);
		
	}
	
}
