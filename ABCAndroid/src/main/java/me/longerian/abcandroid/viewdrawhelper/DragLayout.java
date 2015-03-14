package me.longerian.abcandroid.viewdrawhelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import me.longerian.abcandroid.R;

/**
 * Created by huifeng.hxl on 2015/2/2.
 */
public class DragLayout extends ViewGroup {

    private ViewDragHelper mViewDragHelper;

    private View mDragView;

    private int childOffset;

    private int initTouchDownX;

    private int initTouchDownY;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, mDragHelperCallback);
        childOffset = 300;
    }

    public void setChildOffset(int childOffset) {
        this.childOffset = childOffset;
    }

    private ViewDragHelper.Callback mDragHelperCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View view, int i) {
//            Log.d(VIEW_LOG_TAG, "capture " + view + " i = " + i);
            return mDragView == view;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.d(VIEW_LOG_TAG, "clampViewPositionHorizontal " + left + "," + dx);
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - getPaddingRight() - mDragView.getWidth();
            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
            return newLeft;
//            return 0;
        }


        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() + childOffset - getPaddingBottom() - mDragView.getHeight();
            int newTop = Math.min(Math.max(top, topBound), bottomBound);
            int limitTop = getHeight() - getPaddingBottom() - mDragView.getHeight();
            Log.d(VIEW_LOG_TAG, "clampViewPositionVertical " + top + "," + dy + "," + newTop + "," + limitTop);
            return Math.max(newTop, limitTop);
//            return 0;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            if (child == mDragView) {
                return getWidth() - getPaddingLeft() - getPaddingRight() - child.getWidth();
            }
            return super.getViewHorizontalDragRange(child);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            if (child == mDragView) {
                return getHeight() + childOffset - getPaddingTop() - getPaddingBottom() - child.getHeight();
            }
            return super.getViewVerticalDragRange(child);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.d(VIEW_LOG_TAG, "xvel " + xvel + "," + " yvel " + yvel);
            //use settleCaptureViewAt() method
//            if (yvel > 0) {
//                mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), getHeight() - releasedChild.getHeight() - getPaddingBottom());
//            } else {
//                mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), getPaddingTop());
//            }
//            invalidate();

            //or use smoothSlideViewTo method
//            int half = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2;
//            int current = (releasedChild.getTop() + releasedChild.getBottom()) / 2;
//            boolean settled;
//            if (current >= half) {
//                settled = mViewDragHelper.smoothSlideViewTo(releasedChild, releasedChild.getLeft(),
//                        getHeight() + childOffset - releasedChild.getHeight() - getPaddingBottom());
//            } else {
//                settled = mViewDragHelper
//                        .smoothSlideViewTo(releasedChild, releasedChild.getLeft(), getPaddingTop());
//            }
//            if (settled) {
//                invalidate();
//            }
            boolean settled;
            if (yvel > 0) {
                //往下拖，直接吸到底部
                settled = mViewDragHelper.smoothSlideViewTo(releasedChild, releasedChild.getLeft(),
                        getHeight() + childOffset - releasedChild.getHeight() - getPaddingBottom());
            } else {
                //往上托，全部露出
                settled = mViewDragHelper.smoothSlideViewTo(releasedChild, releasedChild.getLeft(),
                        getHeight() - releasedChild.getHeight() - getPaddingBottom());
            }
            if (settled) {
                invalidate();
            }
        }
    };

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 1) {
            mDragView = getChildAt(0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        if (count != 1) {
            throw new IllegalStateException("must have only one child view");
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(VIEW_LOG_TAG, "on layout: left " + left + " top " + top + " right " + right + " bottom " + bottom);
        final int count = getChildCount();
        if (count != 1) {
            throw new IllegalStateException("must have only one child view");
        }

        final int parentLeft = getPaddingLeft();
        final int parentTop = getPaddingTop();
        final int parentBottom = bottom - top - getPaddingBottom();
        final int parentRight = right - left - getPaddingRight();

        final View child = getChildAt(0);
        if (child.getVisibility() != GONE) {
            //固定布局在底部，并根据offset进行偏移
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();

            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            int childLeft = parentLeft + lp.leftMargin;
            int childTop = parentBottom - height - lp.bottomMargin + childOffset;

            child.layout(childLeft, childTop, childLeft + width, childTop + height);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_DOWN) {
            //记录初始touch位置，如果初始touch的是空白区域，不进行拖动事件处理
            initTouchDownX = (int) ev.getX();
            initTouchDownY = (int) ev.getY();
        }
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mViewDragHelper.cancel();
            initTouchDownX = 0;
            initTouchDownY = 0;
            return false;
        }
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDragView != null) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (initTouchDownX < mDragView.getLeft() || initTouchDownX > mDragView.getRight()
                    || initTouchDownY < mDragView.getTop() || initTouchDownY > mDragView.getBottom()) {
                //空白区域的事件，不进行拖动处理
                Log.d(VIEW_LOG_TAG, "on touch out side drag view " + " x = " + x + " y = " + y);
                Log.d(VIEW_LOG_TAG, "on touch out side drag view " + " left = " + mDragView.getLeft() + " right = " + mDragView.getRight()
                    + " top = " + mDragView.getTop() + " bottom = " + mDragView.getBottom());
                return super.onTouchEvent(event);
            }
            Log.d(VIEW_LOG_TAG, "on touch " + " drag view top " + mDragView.getTop());
            Log.d(VIEW_LOG_TAG, "on touch " + " drag view min top " + (getHeight() - getPaddingBottom() - mDragView.getHeight()));
            if (mDragView.getTop() >= getHeight() - getPaddingBottom() - mDragView.getHeight()) {
                mViewDragHelper.processTouchEvent(event);
            }
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public static class LayoutParams extends MarginLayoutParams {

        /**
         * {@inheritDoc}
         */
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(int width, int height) {
            super(width, height);
        }


        /**
         * {@inheritDoc}
         */
        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        /**
         * {@inheritDoc}
         */
        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        /**
         * Copy constructor. Clones the width, height, margin values, and
         * gravity of the source.
         *
         * @param source The layout params to copy from.
         */
        public LayoutParams(LayoutParams source) {
            super(source);
        }

    }
}
