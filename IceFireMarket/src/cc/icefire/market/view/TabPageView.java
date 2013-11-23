package cc.icefire.market.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import cc.icefire.market.R;
import cc.icefire.market.util.ILog;

import com.viewpagerindicator.TabPageIndicator;

public class TabPageView extends LinearLayout {

	private TabPageIndicator indicator;
	private ViewPager pager;
	private PagerAdapter adapter;
	
	private List<String> titles;
	private List<View> pages;
	
	private OnPageSelectedListener pageSelectedListener;
	
	public TabPageView(Context context) {
		super(context);
		init(context);
	}
	
	public TabPageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public TabPageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		titles = new ArrayList<String>();
		pages = new ArrayList<View>();
		
		setOrientation(LinearLayout.VERTICAL);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.widget_tab_page_view, this);
		
		adapter = new CommonAdapter();
		pager = (ViewPager)findViewById(R.id.pager);
		pager.setAdapter(adapter);
		
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        
        indicator.setOnPageChangeListener(onPageChange);
	}
	
	public void addPage(String title, View page) {
		if(titles != null && pages != null && adapter != null) {
			titles.add(title);
			pages.add(page);
			indicator.notifyDataSetChanged();
			adapter.notifyDataSetChanged();
		}
	}
	
	public void setOnPageSelectedListener(OnPageSelectedListener pageSelectedListener) {
		this.pageSelectedListener = pageSelectedListener;
	}
	
	public void setPage(int position) {
		if(pager != null && position >= 0 && position < pager.getChildCount()) {
			pager.setCurrentItem(position);
		}
	}
	
	public void setPage(int position, boolean smoothScroll) {
		if(pager != null && position >= 0 && position < pager.getChildCount()) {
			pager.setCurrentItem(position, smoothScroll);
		}
	}

	private class CommonAdapter extends PagerAdapter {

		@Override
		public CharSequence getPageTitle(int position) {
			String title = (String) (titles != null ? titles.get(position) : super.getPageTitle(position));
			return title;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public int getCount() {
			return titles != null ? titles.size() : 0;
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
	
	private OnPageChangeListener onPageChange = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int position) {
			if(pageSelectedListener != null) {
				pageSelectedListener.onPageSelected(position);
			}
		}
		
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			
		}
		
		@Override
		public void onPageScrollStateChanged(int state) {
			
		}
	}; 

	public interface OnPageSelectedListener {
		
		public void onPageSelected(int position);
		
	}
	
}
