package me.longerian.abcandroid.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.longerian.abcandroid.R;
import me.longerian.abcandroid.shop.widget.InnerListView;
import me.longerian.abcandroid.shop.widget.InnerScrollView;
import me.longerian.abcandroid.shop.widget.OutterScrollView;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ShopActivity2 extends Activity {

	private String[] txts = new String[] {"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
			"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu"};
	
	private ViewPager viewPager;
	private OutterScrollView outterScrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop_2);
		outterScrollView = (OutterScrollView) findViewById(R.id.outter);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(new ShopPageAdapter());
	}

	private class ShopPageAdapter extends PagerAdapter {

		private List<View> pages = new ArrayList<View>();
		
		public ShopPageAdapter() {
//			InnerListView page1 = new InnerListView(getApplicationContext());
//			page1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//			page1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.item_company, R.id.text, txts));
//			page1.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> arg0, View arg1,
//						int arg2, long arg3) {
//					Log.d("Longer", "page1 clicked item " + arg0.getItemAtPosition(arg2) + " at " + arg3);
//				}
//			});
//			InnerListView page2 = new InnerListView(getApplicationContext());
//			page2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//			page2.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.item_company, R.id.text, txts));
//			page2.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> arg0, View arg1,
//						int arg2, long arg3) {
//					Log.d("Longer", "page2 clicked item " + arg0.getItemAtPosition(arg2) + " at " + arg3);
//				}
//			});
//			pages.add(page1);
//			pages.add(page2);
//			outterScrollView.setInnerScrollListener(page1);
			
			InnerScrollView page1 = (InnerScrollView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.page_shop_detail, null);
			TextView text1 = (TextView) page1.findViewById(R.id.text);
			text1.setText(Arrays.toString(txts));
			
			InnerScrollView page2 = (InnerScrollView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.page_shop_detail, null);
			TextView text2 = (TextView) page2.findViewById(R.id.text);
			text2.setText(Arrays.toString(txts));
			pages.add(page1);
			pages.add(page2);
			
			outterScrollView.setInnerScrollListener(page1);
		}
		
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(pages.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(pages.get(position));  
			return pages.get(position);
		}
		
	}
	
	
}
