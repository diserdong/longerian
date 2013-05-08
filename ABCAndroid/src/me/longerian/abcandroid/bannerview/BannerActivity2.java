package me.longerian.abcandroid.bannerview;

import java.util.ArrayList;

import me.longerian.abcandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class BannerActivity2 extends Activity {
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
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// ((ViewPager) arg0).removeView(list.get(arg1));
		}

		@Override
		public Object instantiateItem(View view, int position) {
			try {
				((ViewPager) view).addView(mListViews.get(position % mListViews.size()), 0);
			} catch (Exception e) {
			}
			return mListViews.get(position % mListViews.size());
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
			if (arg0 > 2) {
				arg0 = arg0 % mListViews.size();
			}
			c_id = arg0;
//			for (int i = 0; i < imageViews.length; i++) {
//				imageViews[arg0]
//						.setBackgroundResource(R.drawable.guide_dot_white);
//				if (arg0 != i) {
//					imageViews[i]
//							.setBackgroundResource(R.drawable.guide_dot_black);
//				}
//			}

			Log.d("-------------", "当前是第" + c_id + "页");
		}

	}
}
