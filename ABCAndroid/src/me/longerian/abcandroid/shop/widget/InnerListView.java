package me.longerian.abcandroid.shop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

public class InnerListView extends ListView implements IScrollController {

	public InnerListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public InnerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public InnerListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canScrollup() {
		return getLastVisiblePosition() < getCount() - 1;
	}

	@Override
	public boolean canScrollDown() {
		return getFirstVisiblePosition() > 0;
	}

	@Override
	public void scrollBy(int dy) {
		smoothScrollBy(dy, 0);
	}

}
