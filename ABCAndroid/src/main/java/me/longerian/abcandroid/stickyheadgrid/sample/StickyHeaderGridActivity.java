package me.longerian.abcandroid.stickyheadgrid.sample;

import java.util.ArrayList;
import java.util.List;

import me.longerian.abcandroid.R;
import me.longerian.abcandroid.bannerview.BannerPageIndicator;
import me.longerian.abcandroid.bannerview.BannerPagerAdapter;
import me.longerian.abcandroid.bannerview.BannerView2;
import me.longerian.abcandroid.stickyheadgrid.lib.StickyGridHeadersGridView;
import me.longerian.abcandroid.stickyheadgrid.lib.StickyGridHeadersGridView.OnHeaderClickListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class StickyHeaderGridActivity extends Activity {

	
	ScrollView container;
	FrameLayout bannerContainer;
	BannerView2 viewPager;
	BannerPageIndicator indicator;
	ArrayList<View> mListViews;
	private static int c_id = 0;
	
	private StickyGridHeadersGridView gridView;
	private StickyGridHeaderAppsArrayAdapter gridAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sticky_header_grid);
		
		container = (ScrollView) findViewById(R.id.container);
		bannerContainer = (FrameLayout) findViewById(R.id.banner_container);
		indicator = (BannerPageIndicator) findViewById(R.id.indicator);
		indicator.setPageSize(3);
		
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
		
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> appPackages = getPackageManager().queryIntentActivities(mainIntent, 0);
		List<Apks> apks = new ArrayList<Apks>();
		int i = 0;
		for(ResolveInfo ri : appPackages) {
			Apks apk = new Apks();
			apk.setType((i++) % 4);
			apk.setName(ri.activityInfo.loadLabel(getPackageManager()).toString());
			apks.add(apk);
		}
		
		gridView = (StickyGridHeadersGridView) findViewById(R.id.app_list);
		gridAdapter = new StickyGridHeaderAppsArrayAdapter(getApplicationContext(), apks);
		gridView.setAreHeadersSticky(false);
		gridView.setAdapter(gridAdapter);
		gridView.setOnHeaderClickListener(new OnHeaderClickListener() {
			
			@Override
			public void onHeaderClick(AdapterView<?> parent, View view, long id) {
				Log.d("StickyHeaderGridActivity", "click header " + id);
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				Log.d("StickyHeaderGridActivity", "click item " + id);
			}
		});
		
		WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
	    Display display = manager.getDefaultDisplay();
	    DisplayMetrics dm = new DisplayMetrics();
	    display.getMetrics(dm);
	    
	    bannerContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (dm.heightPixels * 0.3f)));
		int headerHeight = (int) (gridAdapter.getHeaderCount() * 34f * dm.scaledDensity + 0.5f);
		int bodyHeight = (int) (gridAdapter.getRow() * 148.3f * dm.scaledDensity + 0.5f);
		Log.d("StickyHeaderGridActivity", headerHeight + "/" + bodyHeight);
		gridView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, headerHeight + bodyHeight));
		viewPager.startAutoScroll();
		container.smoothScrollTo(0, 10);
		
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
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
			if (arg0 > 2) {
				arg0 = arg0 % mListViews.size();
			}
			c_id = arg0;
			indicator.updatePageIndicator(c_id);
			Log.d("StickyHeaderGridActivity", "select page " + c_id);
		}

	}
	
}
