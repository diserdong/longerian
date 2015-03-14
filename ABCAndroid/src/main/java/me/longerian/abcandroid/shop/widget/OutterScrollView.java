package me.longerian.abcandroid.shop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ScrollView;

public class OutterScrollView extends ScrollView implements IScrollController {

	private IScrollController innerScrollListener;
	
	private int mTouchSlop = 10;
	private int maxScrollUp = 250;
	private GestureDetector gestureDetector;
	
	private float touchLastX;
	private float touchLastY;
	private float touchDx;
	private float touchDy;
	
	private volatile boolean scrollHeaderRunning = false;
	
	public OutterScrollView(Context context) {
		super(context);
		init(context);
	}
	public OutterScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public OutterScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		gestureDetector = new GestureDetector(context, new DefaultGestureDetector());
	}
	
	private void resetTouchPositions() {
		touchLastX = 0;
		touchLastY = 0;
		touchDx = 0;
		touchDy = 0;
	}
	
	public void setInnerScrollListener(IScrollController listener) {
		innerScrollListener = listener;
	}
	
	public void setMaxScrollUp(int max) {
		maxScrollUp = max;
		Log.d(VIEW_LOG_TAG, "maxScrollUp = " + maxScrollUp);
		if(maxScrollUp == 0) {
			maxScrollUp = 400;
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		int action = event.getAction();
		Log.d(VIEW_LOG_TAG, "onInterceptTouchEvent " + event.getAction());
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			resetTouchPositions();
			touchLastY = event.getY();
			touchLastX = event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			final float y = event.getY();
			final float x = event.getX();
			touchDy += y - touchLastY;
			touchLastY = y;
			touchDx += x - touchLastX;
			touchLastX = x;
			if(Math.abs(touchDy) > mTouchSlop || Math.abs(touchDx) > mTouchSlop) {
				if(Math.abs(touchDx) < Math.abs(touchDy)) {
					return true;
				} else {
					return false;
				}
			}
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onInterceptTouchEvent(event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		gestureDetector.onTouchEvent(event);
		int action = event.getAction();
		Log.d(VIEW_LOG_TAG, "onTouchEvent " + action);
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			touchLastY = event.getY();
			
			break;
		case MotionEvent.ACTION_MOVE:
			final float y = event.getY();
			final float dy = y - touchLastY;
			Log.d(VIEW_LOG_TAG, "onTouch Move dy = " + dy);
			touchLastY = y;
			if(dy >= 0) {
				if(innerScrollListener != null && innerScrollListener.canScrollDown()) {
					innerScrollListener.scrollBy((int) dy);
				} else {
					if(canScrollDown()) {
						scrollBy((int) dy);
//						smoothScrollHeader(false);
					}
				}
			} else {
				if(canScrollup()) {
					scrollBy((int) dy);
//					smoothScrollHeader(true);
				} else {
					if(innerScrollListener != null && innerScrollListener.canScrollup()) {
						innerScrollListener.scrollBy((int) dy);
					}
				}
			}
			return true;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean canScrollup() {
		int scrollY = getScrollY();
		Log.d(VIEW_LOG_TAG, "canScrollup scrollY " + scrollY);
		return scrollY >= 0 && scrollY < maxScrollUp;
	}
	
	@Override
	public boolean canScrollDown() {
		int scrollY = getScrollY();
		Log.d(VIEW_LOG_TAG, "canScrollDown scrollY " + scrollY);
		return scrollY > 0 && scrollY <= maxScrollUp;
	}
	
	@Override
	public void scrollBy(int dy) {
		int x = getScrollX();
		int y = getScrollY() - dy;
		if(y < 0) {
			y = 0;
		}
		if(y > maxScrollUp) {
			y = maxScrollUp;
		}
		scrollTo(x, y);
	}
	
	class DefaultGestureDetector extends SimpleOnGestureListener {

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			// return super.onScroll(e1, e2, distanceX, distanceY);
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (velocityY < 0) 	{
				if (getScrollY() >= maxScrollUp && null != innerScrollListener && e1 != null && e2 != null) {
//					innerScrollListener.fling(-(int) velocityY);
					innerScrollListener.scrollBy((int) (e1.getY() - e2.getY()));
				}

			} else {
				if (null != innerScrollListener && innerScrollListener.canScrollDown() && e1 != null && e2 != null) {
//					innerScrollListener.fling(-(int) velocityY);
					innerScrollListener.scrollBy((int) (e1.getY() - e2.getY()));
				}
			}
			return false;
		}
	}
	
	private void smoothScrollHeader(boolean srollUp) {
		if(!scrollHeaderRunning) {
			SmoothScrollRunnable scrollRunnable = new SmoothScrollRunnable(srollUp);
			post(scrollRunnable);
			scrollHeaderRunning = true;
		}
	}
	
	
	private class SmoothScrollRunnable implements Runnable {

		private long startTime = -1;//动画开始时间
		private long mDuration = 250;//滑动头部用的时间
		private int mCurrentY = 0;//当前需要移动到的距离
		private Interpolator mInterpolator = new DecelerateInterpolator(); // 滚动动画的插值器
		private boolean scrollUp = true;//向上滑动还是向下滑动
		
		public SmoothScrollRunnable(boolean scrollUp) {
			super();
			this.scrollUp = scrollUp;
		}

		@Override
		public void run() {
			if(startTime == -1) {
				startTime = System.currentTimeMillis();
			}else {
				//动画的具体实现
				long normalizeTime = (1000 * (System.currentTimeMillis() - startTime)) / mDuration;
				normalizeTime = Math.max(Math.min(normalizeTime, 1000), 0);
				final int deltaY = Math.round(maxScrollUp * mInterpolator.getInterpolation(normalizeTime / 1000f));
				if(scrollUp) {
					mCurrentY = deltaY - getScrollY();
					scrollDeltaY(-mCurrentY);					
				}else {
					mCurrentY = deltaY - (maxScrollUp - getScrollY());
					scrollDeltaY(mCurrentY);
				}
			}
			
			//决定是否继续做动画
			if((scrollUp && getScrollY() >= maxScrollUp) || (!scrollUp && getScrollY() <= 0)) {
				removeCallbacks(this);
				scrollHeaderRunning = false;
//				if (innerScrollListener != null) {
//					innerScrollListener.scrollTopFinish(scrollUp);
//				}
			}else {
				post(this);
			}			
			
		}
		
	}
	
	private void scrollDeltaY(int yDistance) {
		int x = getScrollX();
		int y = getScrollY() - yDistance;
		scrollTo(x, y);
	}
	
}
