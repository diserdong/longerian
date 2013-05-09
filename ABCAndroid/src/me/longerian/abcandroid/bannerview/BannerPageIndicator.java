package me.longerian.abcandroid.bannerview;

import me.longerian.abcandroid.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BannerPageIndicator extends LinearLayout {

	private int pageSize;
	private int currentPage = 0;

	public BannerPageIndicator(Context context) {
		super(context);
		init();
	}

	public BannerPageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		this.setGravity(Gravity.CENTER);
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.pageSize = 0;
		this.currentPage = 0;
	}

	public final void setPageSize(int size) {
		if (size <= 0 || size == pageSize) {
			return;
		}
		removeAllViews();
		for (int i = 0; i < size; i++) {
			ImageView point = new ImageView(getContext());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			if(i == 0) {
				point.setImageResource(R.drawable.list_longpressed_holo);
			} else {
				point.setImageResource(R.drawable.list_pressed_holo_light);
			}
			lp.setMargins(5, 0, 5, 0);
			addView(point, lp);
		}
		this.pageSize = size;
		this.currentPage = 0;
	}
	
	public final void updatePageIndicator(int index) {
		if(currentPage == index || index < 0 || index >= pageSize) {
			return ;
		}
		((ImageView)this.getChildAt(index)).setImageResource(R.drawable.list_longpressed_holo);
	    ((ImageView)this.getChildAt(this.currentPage)).setImageResource(R.drawable.list_pressed_holo_light);
	    this.currentPage = index;
	}

}
