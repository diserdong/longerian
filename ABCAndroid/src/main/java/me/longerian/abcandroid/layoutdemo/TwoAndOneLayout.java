package me.longerian.abcandroid.layoutdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huifeng.hxl on 2015/3/16.
 */
public class TwoAndOneLayout extends ViewGroup {

    public TwoAndOneLayout(Context context) {
        super(context);
    }

    public TwoAndOneLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TwoAndOneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount > 3) {
            throw new IllegalStateException("can not contain more than three children view");
        }
        int parentWith = getMeasuredWidth();
        if (childCount == 1) {
            View child = getChildAt(0);
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            measureChild(child,
                    MeasureSpec.makeMeasureSpec(parentWith - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                    child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
        } else if (childCount == 2) {
            final View child1 = getChildAt(0);
            final LayoutParams lp1 = (LayoutParams) child1.getLayoutParams();
            final View child2 = getChildAt(1);
            final LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
            measureChild(child1,
                    MeasureSpec.makeMeasureSpec(parentWith - lp1.leftMargin - lp1.rightMargin, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            measureChild(child2,
                    MeasureSpec.makeMeasureSpec(parentWith - lp2.leftMargin - lp2.rightMargin, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                    child1.getMeasuredHeight() + lp1.topMargin + lp1.bottomMargin +
                            child2.getMeasuredHeight() + lp2.topMargin + lp2.bottomMargin);
        } else if (childCount == 3) {
            final View child1 = getChildAt(0);
            final LayoutParams lp1 = (LayoutParams) child1.getLayoutParams();
            final View child2 = getChildAt(1);
            final LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
            final View child3 = getChildAt(2);
            final LayoutParams lp3 = (LayoutParams) child3.getLayoutParams();

            int width = (parentWith - lp1.leftMargin - lp1.rightMargin - lp3.leftMargin - lp3.rightMargin) / 2;

            measureChild(child1,
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            measureChild(child2,
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            measureChild(child3,
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

            setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                    Math.max((child1.getMeasuredHeight() + lp1.topMargin + lp1.bottomMargin +
                            child2.getMeasuredHeight() + lp2.topMargin + lp2.bottomMargin),
                            child3.getMeasuredHeight() + lp3.topMargin + lp3.bottomMargin));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int parentLeft = getPaddingLeft();
        final int parentTop = getPaddingTop();
        int childCount = getChildCount();
        if (childCount == 1) {
            View child = getChildAt(0);
            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int childLeft = parentLeft + lp.leftMargin;
            int childTop = parentTop + lp.topMargin;
            child.layout(childLeft, childTop, childLeft + width, childTop + height);
        } else if (childCount == 2) {
            View child1 = getChildAt(0);
            final int width1 = child1.getMeasuredWidth();
            final int height1 = child1.getMeasuredHeight();
            final LayoutParams lp1 = (LayoutParams) child1.getLayoutParams();
            int childLeft1 = parentLeft + lp1.leftMargin;
            int childTop1 = parentTop + lp1.topMargin;
            child1.layout(childLeft1, childTop1, childLeft1 + width1, childTop1 + height1);

            View child2 = getChildAt(1);
            final int width2 = child2.getMeasuredWidth();
            final int height2 = child2.getMeasuredHeight();
            final LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
            int childLeft2 = parentLeft + lp2.leftMargin;
            int childTop2 = parentTop + lp1.topMargin + height1 + lp1.bottomMargin + lp2.topMargin;
            child2.layout(childLeft2, childTop2, childLeft2 + width2, childTop2 + height2);
        } else if (childCount == 3) {
            View child1 = getChildAt(0);
            final int width1 = child1.getMeasuredWidth();
            final int height1 = child1.getMeasuredHeight();
            final LayoutParams lp1 = (LayoutParams) child1.getLayoutParams();
            int childLeft1 = parentLeft + lp1.leftMargin;
            int childTop1 = parentTop + lp1.topMargin;
            child1.layout(childLeft1, childTop1, childLeft1 + width1, childTop1 + height1);

            View child2 = getChildAt(1);
            final int width2 = child2.getMeasuredWidth();
            final int height2 = child2.getMeasuredHeight();
            final LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
            int childLeft2 = parentLeft + lp2.leftMargin;
            int childTop2 = parentTop + lp1.topMargin + height1 + lp1.bottomMargin + lp2.topMargin;
            child2.layout(childLeft2, childTop2, childLeft2 + width2, childTop2 + height2);

            View child3 = getChildAt(2);
            final int width3 = child3.getMeasuredWidth();
            final int height3 = child3.getMeasuredHeight();
            final LayoutParams lp3 = (LayoutParams) child3.getLayoutParams();
            int childLeft3 = parentLeft + lp1.leftMargin + width1 + lp1.rightMargin + lp3.leftMargin;
            int childTop3 = parentTop + lp3.topMargin;
            child3.layout(childLeft3, childTop3, childLeft3 + width3, childTop3 + height3);

        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
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
