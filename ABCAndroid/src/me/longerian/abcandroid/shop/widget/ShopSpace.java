package me.longerian.abcandroid.shop.widget;

import java.util.Arrays;

import me.longerian.abcandroid.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class ShopSpace extends RelativeLayout {

	private String[] txts = new String[] {"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu"};
	
	private OnPinChangeListener listener;
	
	private ImageView banner;
	private ImageView pin;
	private ListView list;
	
	private int mTouchSlop = 10;
	
	private Scroller scroller;
	private int scrollCurrentX;
	private int scrollCurrentY;
	private int scrollDx;
	private int scrollDy;
	
	private float touchLastX;
	private float touchLastY;
	private float touchDx;
	private float touchDy;
	
	private GestureDetector gestureDetector;
	
	public ShopSpace(Context context) {
		super(context);
		init(context);
	}
	public ShopSpace(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public ShopSpace(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		scroller = new Scroller(context);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		gestureDetector = new GestureDetector(context, new DefaultGestureDetector());
		resetScrollPositions();
		resetTouchPositions();
	}
	
	private void resetScrollPositions() {
		scrollDx = 0;
		scrollDy = 0;
		scrollCurrentX = 0;
		scrollCurrentY = 0;
	}
	
	private void resetTouchPositions() {
		touchLastX = 0;
		touchLastY = 0;
		touchDx = 0;
		touchDy = 0;
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		banner = (ImageView) findViewById(R.id.banner);
		pin = (ImageView) findViewById(R.id.pin);
		list = (ListView) findViewById(R.id.list);
		if(list != null) {
			list.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, txts));
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Log.d(VIEW_LOG_TAG, "clicked item " + arg0.getItemAtPosition(arg2) + " at " + arg3);
				}
			});
		}
	}
	
	@Override
	public void computeScroll() {
		super.computeScroll();
		if(scroller.computeScrollOffset()) {
			// Get current x and y positions
		     int currX = scroller.getCurrX();
		     int currY = scroller.getCurrY();
		     Log.d(VIEW_LOG_TAG, "in computeScroll currX = " + currX + " currY = " + currY);
		     if(currY >= 0) {
		    	 scrollTo(currX, currY);
		    	 postInvalidate();
		     }
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		Log.d(VIEW_LOG_TAG, "in onScrollChanged l = " + l + " t = " + t + " oldl = " + oldl + " oldt = " + oldt);
		scrollCurrentX = l;
		scrollCurrentY = t;
		scrollDx += l - oldl;
		scrollDy += t - oldt;
		int[] location = new int[2];
		pin.getLocationInWindow(location);
		Log.d(VIEW_LOG_TAG, "pin.WindowLocation = " + Arrays.toString(location));
		pin.getLocationOnScreen(location);
		Log.d(VIEW_LOG_TAG, "pin.ScreenLocation = " + Arrays.toString(location));
		Log.d(VIEW_LOG_TAG, "pin.height = " + Arrays.toString(location));
		Log.d(VIEW_LOG_TAG, "pin.top = " + pin.getTop());
		Log.d(VIEW_LOG_TAG, "pin.scrollY = " + pin.getScrollY());
		Log.d(VIEW_LOG_TAG, "this.top = " + getTop());
		Log.d(VIEW_LOG_TAG, "this.scrollY = " + getScrollY());
		if(listener != null) {
			if(location[1] <= 0) {
				listener.onPin();
			} else {
				listener.onUnpin();
			}
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		int action = event.getAction();
		Log.d(VIEW_LOG_TAG, "onInterceptTouchEvent " + event.getAction());
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			resetTouchPositions();
			if (!scroller.isFinished()) {
                scroller.abortAnimation();
            }
			touchLastY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float y = event.getY();
			touchDy += y - touchLastY;
			touchLastY = y;
			if(Math.abs(touchDy) > mTouchSlop) {
				return true;
			}
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onInterceptTouchEvent(event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		Log.d(VIEW_LOG_TAG, "onTouchEvent " + action);
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			touchLastY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float y = event.getY();
			final float dy = y - touchLastY;
			touchLastY = y;
			int[] location = new int[2];
			pin.getLocationInWindow(location);
			Log.d(VIEW_LOG_TAG, "dy = " + dy + " pin.windowLocation = " + location[1]);
			if(location[1] >= 0) {
				scrollBy(0, -(int) dy);
			} else {
				if(dy <= 0) {
					list.scrollBy(0, -(int) dy);
				} else {
					scrollBy(0, -(int) dy);
				}
			}
			return true;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onTouchEvent(event);
	}
	
	public void setOnPinChangeListener(OnPinChangeListener l) {
		this.listener = l;
	}
	
	public void scrollUp() {
	     scroller.forceFinished(true);
	     // Start scrolling by providing a starting point and
	     // the distance to travel
	     scroller.startScroll(scrollCurrentX, scrollCurrentY, 0, 100);
	     // Invalidate to request a redraw
	     invalidate();
	 }
	 
	public void scrollDown() {
		scroller.forceFinished(true);
		// Start scrolling by providing a starting point and
		// the distance to travel
		scroller.startScroll(scrollCurrentX, scrollCurrentY, 0, -100);
		// Invalidate to request a redraw
		invalidate();
	}
	
	public interface OnPinChangeListener {
		
		public void onPin();
		public void onUnpin();
		
	}
	
	class DefaultGestureDetector extends SimpleOnGestureListener
	{

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
		{
			// return super.onScroll(e1, e2, distanceX, distanceY);
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
		{
//			if (velocityY < 0)
//			{
//				if (getScrollY() >= maxScrollUp && null != innerScrollListener)
//				{
//					innerScrollListener.fling(-(int) velocityY);
//					innerScrollListener.scrollDeltaY(0);
//				}
//
//			}
//			else
//			{
//				if (null != innerScrollListener && innerScrollListener.canScrollDown())
//				{
//					innerScrollListener.fling(-(int) velocityY);
//					innerScrollListener.scrollDeltaY(0);
//				}
//			}
			list.scrollTo(0, 0);
			return false;
		}
	}
	
}
