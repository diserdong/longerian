package me.longerian.abcandroid.tiledview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class TiledLayout extends ViewGroup {

	private int verticalSpace = 10;
	private int horizontalSpace = 10;
	private int paddingLeft;
	private int paddingRight;
	private int paddingTop;
	private int paddingBottom;

	public TiledLayout(Context context) {
		super(context);
		init(context);
	}

	public TiledLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public TiledLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		paddingLeft = getPaddingLeft();
		paddingRight = getPaddingRight();
		paddingTop = getPaddingTop();
		paddingBottom = getPaddingBottom();
		Log.d(VIEW_LOG_TAG, paddingLeft + "/" + paddingRight + "/" + paddingTop + "/" + paddingBottom);
	}

	public int getVerticalSpace() {
		return verticalSpace;
	}

	public void setVerticalSpace(int verticalSpace) {
		this.verticalSpace = verticalSpace;
		requestLayout();
	}

	public int getHorizontalSpace() {
		return horizontalSpace;
	}

	public void setHorizontalSpace(int horizontalSpace) {
		this.horizontalSpace = horizontalSpace;
		requestLayout();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (int index = 0; index < getChildCount(); index++) {
			final View child = getChildAt(index);
			child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int count = getChildCount();
		int xIndex = l + paddingLeft;
		int yIndex = t + paddingTop;
		int lineIndex = 0;
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				final int childHeight = child.getMeasuredHeight();
				final int childWidth = child.getMeasuredWidth();
				if (xIndex + childWidth > r - paddingRight) {
					// new line
					lineIndex = lineIndex + 1;
					xIndex = l + paddingLeft;
					yIndex = t + paddingTop + lineIndex * (childHeight + verticalSpace);
					child.layout(xIndex, yIndex, xIndex + childWidth, yIndex
							+ childHeight);
					xIndex = xIndex + childWidth + horizontalSpace;
				} else {
					child.layout(xIndex, yIndex, xIndex + childWidth, yIndex
							+ childHeight);
					xIndex = xIndex + childWidth + horizontalSpace;
				}
			}
		}
	}

}
