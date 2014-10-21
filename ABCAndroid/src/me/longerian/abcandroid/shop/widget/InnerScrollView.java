package me.longerian.abcandroid.shop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class InnerScrollView extends LinearLayout implements IScrollController {

	private int top;
	private int screenHeight;
	
	public InnerScrollView(Context context) {
		super(context);
		init(context);
	}
	public InnerScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public InnerScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		top = getTop();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		screenHeight = wm.getDefaultDisplay().getHeight();
	}

	@Override
	public boolean canScrollup() {
		int scrollY = getScrollY();
		int height = getHeight();
		if(top == 0) {
			int top = getTop();
		}
		int lastChildHeight = screenHeight - top;
		Log.d(VIEW_LOG_TAG, "InnerScrollView scrollY = " + scrollY + " height = " + (height - lastChildHeight));
		return scrollY < height - lastChildHeight;
	}

	@Override
	public boolean canScrollDown() {
		int scrollY = getScrollY();
		return scrollY > 0;
	}

	@Override
	public void scrollBy(int dy) {
		int x = getScrollX();
		int y = getScrollY() - dy;
		Log.d(VIEW_LOG_TAG, "InnerScrollView x = " + x + " y = " + y + " dy = " + dy);
		if(y < 0) {
			y = 0;
		}
		scrollTo(x, y);
	}

}
