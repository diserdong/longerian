package me.longerian.abcandroid.bannerview;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class BannerView2 extends ViewPager {

	private static final int NEXT = 1;
	private Handler handler;
	private Timer timer;
	
	public BannerView2(Context context) {
		super(context);
		init();
	}

	public BannerView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
	}

	@Override
	public void setAdapter(PagerAdapter adapter) {
		super.setAdapter(adapter);
		// offset first element so that we can scroll to the left
		setCurrentItem(0);
	}
	
	public void startAutoScroll() {
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if(msg.what == NEXT) {
					setCurrentItem(getCurrentItem() + 1, true);
				}
			}
			
		};
		if(timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if(handler != null) {
					handler.sendEmptyMessage(NEXT);
				}
			}
			
		}, 5000, 5000);
	}
	
	public void stopAutoScroll() {
		if(timer != null) {
			timer.cancel();
		}
	}

	@Override
	public void setCurrentItem(int item) {
		// offset the current item to ensure there is space to scroll
		item = getOffsetAmount() + (item % getAdapter().getCount());
		super.setCurrentItem(item);

	}

	private int getOffsetAmount() {
		if (getAdapter() instanceof BannerPagerAdapter) {
			BannerPagerAdapter infAdapter = (BannerPagerAdapter) getAdapter();
			// allow for 100 back cycles from the beginning
			// should be enough to create an illusion of infinity
			// warning: scrolling to very high values (1,000,000+) results in
			// strange drawing behaviour
			return infAdapter.getRealCount() * 100;
		} else {
			return 0;
		}
	}

}
