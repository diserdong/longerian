package me.longerian.abcandroid.layoutdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huifeng.hxl on 2015/3/17.
 */
public class ColumnLayout extends ViewGroup {

    /**
     * 当子元素个数小于mColumnCount时，是否自动均分容器宽度
     */
    private boolean mIsAutoExpand;
    private int mColumnCount = 4;

    public ColumnLayout(Context context) {
        super(context);
    }

    public ColumnLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isAutoExpand() {
        return mIsAutoExpand;
    }

    public void setAutoExpand(boolean isAutoExpand) {
        mIsAutoExpand = isAutoExpand;
        requestLayout();
    }

    public int getColumnCount() {
        return mColumnCount;
    }

    public void setColumnCount(int columnCount) {
        this.mColumnCount = columnCount;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int line = childCount % mColumnCount == 0 ? childCount / mColumnCount
                : childCount / mColumnCount + 1;
        final int parentWidth = getMeasuredWidth();
        int totalHeight = 0;
        int totalLineMargin = 0;
        if (line == 1) {
            //只有一行，平均分
            for (int i = 0; i < childCount; i++) {
                LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
                totalLineMargin += (lp.leftMargin + lp.rightMargin);
            }
            int width = (parentWidth - totalLineMargin) / childCount;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                measureChild(getChildAt(i),
                        MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int totalChildHeight = lp.topMargin + lp.bottomMargin + child.getMeasuredHeight();
                if (totalChildHeight > totalHeight) {
                    totalHeight = totalChildHeight;
                }
            }
        } else {
            int childIndex = 0;
            for (int l = 0; l < line; l++) {
                int inlineHeight = 0;
                totalLineMargin = 0;
                for (int inlineIndex = l * mColumnCount; inlineIndex < mColumnCount * (l + 1) && inlineIndex < childCount; inlineIndex++) {
                    LayoutParams lp = (LayoutParams) getChildAt(inlineIndex).getLayoutParams();
                    totalLineMargin += (lp.leftMargin + lp.rightMargin);
                }
                for (int i = 0; i < mColumnCount; i++) {
                    childIndex = l * mColumnCount + i;
                    if (childIndex < childCount) {
                        int width = (parentWidth - totalLineMargin) / mColumnCount;
                        View child = getChildAt(childIndex);
                        LayoutParams lp = (LayoutParams) child.getLayoutParams();
                        measureChild(child,
                                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                        int totalInlineChildHeight = lp.topMargin + lp.bottomMargin + child.getMeasuredHeight();
                        if (totalInlineChildHeight > inlineHeight) {
                            inlineHeight = totalInlineChildHeight;
                        }
                    }
                }
                totalHeight += inlineHeight;
            }
        }
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int parentLeft = getPaddingLeft();
        final int parentTop = getPaddingTop();
        int childCount = getChildCount();
        int line = childCount % mColumnCount == 0 ? childCount / mColumnCount
                : childCount / mColumnCount + 1;
        if (line == 1) {
            int lastLeft = parentLeft;
            int lastTop = parentTop;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                lastLeft += lp.leftMargin;
                child.layout(lastLeft, lastTop + lp.topMargin, lastLeft + width, lastTop + lp.topMargin + height);
                lastLeft += (width + lp.rightMargin);
            }
        } else {
            int childIndex = 0;
            int lastLeft = parentLeft;
            int lastTop = parentTop;
            for (int ln = 0; ln < line; ln++) {
                int inlineHeight = 0;
                for (int i = 0; i < mColumnCount && childIndex < childCount; i++) {
                    childIndex = ln * mColumnCount + i;
                    if (childIndex < childCount) {
                        View child = getChildAt(childIndex);
                        final int width = child.getMeasuredWidth();
                        final int height = child.getMeasuredHeight();
                        final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                        lastLeft += lp.leftMargin;
                        child.layout(lastLeft, lastTop + lp.topMargin, lastLeft + width, lastTop + lp.topMargin + height);
                        lastLeft += (width + lp.rightMargin);
                        int totalInlineChildHeight = lp.topMargin + lp.bottomMargin + height;
                        if (totalInlineChildHeight > inlineHeight) {
                            inlineHeight = totalInlineChildHeight;
                        }
                    }
                }
                lastTop += inlineHeight;
                lastLeft = parentLeft;
            }
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
