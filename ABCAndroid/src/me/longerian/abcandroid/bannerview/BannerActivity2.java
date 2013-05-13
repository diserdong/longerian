package me.longerian.abcandroid.bannerview;

import java.util.ArrayList;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class BannerActivity2 extends Activity {
	BannerPageIndicator indicator;
	RateBar rateBar;
	StorageIndicator progress;
	
	BannerView2 viewPager;
	ArrayList<View> mListViews;
	private static int c_id = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner_2);
		mListViews = new ArrayList<View>();
		ImageView iv1 = new ImageView(getApplicationContext());
		iv1.setImageResource(R.drawable.system_file_manager);
		ImageView iv2 = new ImageView(getApplicationContext());
		iv2.setImageResource(R.drawable.system_installer);
		ImageView iv3 = new ImageView(getApplicationContext());
		iv3.setImageResource(R.drawable.tencent_q);

		mListViews.add(iv1);
		mListViews.add(iv2);
		mListViews.add(iv3);
		viewPager = (BannerView2) findViewById(R.id.banner);
		viewPager.setAdapter(new BannerPagerAdapter(new MyAdapter()));
		viewPager.setOnPageChangeListener(new MyListener());

		viewPager.startAutoScroll();
		
		indicator = (BannerPageIndicator) findViewById(R.id.indicator);
		indicator.setPageSize(3);
		
		rateBar = (RateBar) findViewById(R.id.rate);
		rateBar.setMaxRate(5);
		rateBar.setRate(5);
		
		progress = (StorageIndicator) findViewById(R.id.progress_external_storage);
		progress.setProgress(95);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		viewPager.stopAutoScroll();
	}

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View viewPager, int position, Object content) {
			Log.d("BannerActivity", "destroyItem " + position);
//			((ViewPager) viewPager).removeView((View) content);
			//cost too many memory
		}

		@Override
		public Object instantiateItem(View viewPager, int position) {
			Log.d("BannerActivity", "instantiateItem " + position);
			View content = mListViews.get(position % mListViews.size());
			if(content.getParent() != null) {
				((ViewPager) viewPager).removeView((View) content);
			}
			((ViewPager) viewPager).addView(content, 0);
			return content;
		}
	}

	class MyListener implements OnPageChangeListener {

		// 当滑动状态改变时调用
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// arg0=arg0%list.size();

		}

		// 当当前页面被滑动时调用
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		// 当新的页面被选中时调用
		@Override
		public void onPageSelected(int arg0) {
			Log.d("-------------", "arg0 " + arg0);
			if (arg0 > 2) {
				arg0 = arg0 % mListViews.size();
			}
			c_id = arg0;
			// for (int i = 0; i < imageViews.length; i++) {
			// imageViews[arg0]
			// .setBackgroundResource(R.drawable.guide_dot_white);
			// if (arg0 != i) {
			// imageViews[i]
			// .setBackgroundResource(R.drawable.guide_dot_black);
			// }
			// }
			indicator.updatePageIndicator(c_id);
//			rateBar.setRate(c_id);
			Log.d("-------------", "当前是第" + c_id + "页");
		}

	}
}
