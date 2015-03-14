package me.longerian.abcandroid.pullview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class PullableView extends ViewGroup {
	
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    
    private int mTouchSlop;
    private int mMaximumVelocity;
    private final int mDefaultScreen = 0;
    private int mCurrentScreen;
	private boolean mFirstLayout = true;
	
	private float mLastMotionY;
    private int mTouchState = TOUCH_STATE_REST;
    
    private static final int SNAP_VELOCITY = 600;
    private final static int TOUCH_STATE_REST = 0;
    private final static int TOUCH_STATE_SCROLLING = 1;

	public PullableView(Context context) {
		super(context);
		initScrollScreen();
	}
	
	public PullableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initScrollScreen();
	}
	
	public PullableView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initScrollScreen();
	}

	private void initScrollScreen() {
        Context context = getContext();
        mScroller = new Scroller(context);

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mCurrentScreen = mDefaultScreen;
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
           return ;
        }

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {
            return ;
        }

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }

        if (mFirstLayout) {
            setHorizontalScrollBarEnabled(false);
            scrollTo(0, mCurrentScreen * height);
            setHorizontalScrollBarEnabled(true);
            mFirstLayout = false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childTop = 0;

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
            	final int childHeight = child.getMeasuredHeight();
            	child.layout(0, childTop, child.getMeasuredWidth(), childTop + child.getMeasuredHeight());
                childTop += childHeight;
            }
        }
    }
    
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
        	scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
            return true;
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
        
        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                final float y = ev.getY();
                final int yDiff = (int) Math.abs(y - mLastMotionY);
                final int touchSlop = mTouchSlop;
                boolean yMoved = yDiff > touchSlop;
                if(yMoved) {
                	mTouchState = TOUCH_STATE_SCROLLING;
                	mLastMotionY = y;
                }
                break;
            }

            case MotionEvent.ACTION_DOWN: {
            	final float y = ev.getY();
            	mLastMotionY = y;
                mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mTouchState = TOUCH_STATE_REST;
                
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }

                break;
        }
        
        return mTouchState != TOUCH_STATE_REST;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }
            mLastMotionY = ev.getY();
            break;
        case MotionEvent.ACTION_MOVE:
        	final float y = ev.getY();
        	final float deltaY = mLastMotionY - y;
        	mLastMotionY = y;
        	scrollBy(0, (int) deltaY);
            break;
        case MotionEvent.ACTION_UP:
            final VelocityTracker velocityTracker = mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
            final int velocityY = (int) velocityTracker.getYVelocity();
            if(velocityY > SNAP_VELOCITY && mCurrentScreen > 0) {
            	snapToScreen(mCurrentScreen);
            } else if(velocityY < -SNAP_VELOCITY && mCurrentScreen < getChildCount() - 1) {
            	snapToScreen(mCurrentScreen);
            } else {
            	snapToScreen(mCurrentScreen);
            }
            if (mVelocityTracker != null) {
                mVelocityTracker.recycle();
                mVelocityTracker = null;
            }
            mTouchState = TOUCH_STATE_REST;
            break;
        case MotionEvent.ACTION_CANCEL:
            mTouchState = TOUCH_STATE_REST;
            break;
        }

        return true;
    }
    
	public void snapToDestination() {
		final int screenHeight = getHeight();
		final int destScreen = (getScrollY() + screenHeight / 2) / screenHeight;
		snapToScreen(destScreen);
	}
	
    private void snapToScreen(int whichScreen) {
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        if (getScrollY() != (whichScreen * getHeight())) {
        	final int delta = whichScreen * getHeight() - getScrollY();
        	mScroller.startScroll(0, getScrollY(), 0, delta, Math.abs(delta) * 2);
        	mCurrentScreen = whichScreen;
        	invalidate();
        }
    }
    
    public void addScreen(View view) {
    	addView(view,new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
    }
    
    public void addScreen(View view, int index) {
    	addView(view, index, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
    }
    
//	public int getCurrentScreen() {
//		return mCurrentScreen;
//	}
//
//	public void setToScreen(int whichScreen) {
//		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
//		mCurrentScreen = whichScreen;
//		scrollTo(whichScreen * getWidth(), 0);
//		
//	}
	
}
