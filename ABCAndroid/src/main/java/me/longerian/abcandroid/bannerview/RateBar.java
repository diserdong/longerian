package me.longerian.abcandroid.bannerview;

import me.longerian.abcandroid.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class RateBar extends LinearLayout {

	private int maxRate;
	private int currentRate;
	
	public RateBar(Context context) {
		super(context);
		init();
	}
	
	public RateBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		this.setGravity(Gravity.CENTER);
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.maxRate = 0;
		this.currentRate = 0;
	}
	
	public final void setMaxRate(int size) {
		if (size <= 0 || size == maxRate) {
			return;
		}
		removeAllViews();
		for (int i = 0; i < size; i++) {
			ImageView point = new ImageView(getContext());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			point.setImageResource(R.drawable.list_pressed_holo_light);
			lp.setMargins(5, 0, 5, 0);
			addView(point, lp);
		}
		this.maxRate = size;
		this.currentRate = 0;
	}
	
	public final void setRate(int rate) {
		if(currentRate == rate || rate < 0 || rate > maxRate) {
			return ;
		}
		for(int i = 0, count = getChildCount(); i < count; i++) {
			if(i < rate) {
				((ImageView)this.getChildAt(i)).setImageResource(R.drawable.list_longpressed_holo);
			} else {
				((ImageView)this.getChildAt(i)).setImageResource(R.drawable.list_pressed_holo_light);
			}
		}
	    this.currentRate = rate;
	}

}
