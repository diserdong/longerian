package me.longerian.androidhacks;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import me.longerian.abcandroid.R;

/**
 * Created by huifeng.hxl on 2014/10/21.
 */
public class CascadeLayout extends ViewGroup {

    private int mHorizontalSpacing;
    private int mVerticalSpacing;

    public CascadeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CascadeLayout);
        try {
            mHorizontalSpacing = a.getDimensionPixelOffset(R.styleable.CascadeLayout_horizontal_spacing,
                    getResources().getDimensionPixelOffset(R.dimen.cascade_horizontal_spacing));
            mVerticalSpacing = a.getDimensionPixelOffset(R.styleable.CascadeLayout_vertical_spacing,
                    getResources().getDimensionPixelOffset(R.dimen.cascade_vertical_spacing));
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = getPaddingTop();
        int verticalSpacing = mVerticalSpacing;

        final int count = getChildCount();
        for( int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();

            if(lp.verticalSpacing >= 0) {
                verticalSpacing = lp.verticalSpacing;
            }

            width = getPaddingLeft() + mHorizontalSpacing * i;
            lp.x = width;
            lp.y = height;
            width += child.getMeasuredWidth();
            height += verticalSpacing;
        }

        width += getPaddingRight();
        height += getChildAt(getChildCount() - 1).getMeasuredHeight() + getPaddingBottom();

        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for(int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y + child.getMeasuredHeight());
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {

        int verticalSpacing;

        int x;
        int y;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CascadeLayout_LayoutParams);
            try {
                verticalSpacing = a.getDimensionPixelOffset(R.styleable.CascadeLayout_LayoutParams_layout_vertical_spacing, -1);
            } finally {
                a.recycle();
            }
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

}
