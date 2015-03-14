package me.longerian.abcandroid.listgrid;

import java.util.ArrayList;
import java.util.List;

import me.longerian.abcandroid.R;
import me.longerian.abcandroid.bannerview.BannerPagerAdapter;
import me.longerian.abcandroid.bannerview.BannerView2;
import me.longerian.abcandroid.listgrid.GridAdapter.OnGridItemClickListener;
import me.longerian.abcandroid.stickyheadgrid.sample.Apks;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListGridActivity extends Activity {

	BannerView2 viewPager;
	ArrayList<View> mListViews;
	private static int c_id = 0;

	ListView apps;
	RawAdapter rawAdapter;
	GridAdapter<RawAdapter> gridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_grid);

		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> appPackages = getPackageManager()
				.queryIntentActivities(mainIntent, 0);
		List<Apks> apks = new ArrayList<Apks>();
		int i = 0;
		for (ResolveInfo ri : appPackages) {
			Apks apk = new Apks();
			if((i++) % 8 == 0) {
				Apks section = new Apks();
				section.setType(0);
				section.setName("category");
				apks.add(section);
			}
			apk.setType(1);
			apk.setName(ri.activityInfo.loadLabel(getPackageManager())
					.toString());
			apks.add(apk);
		}

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
		viewPager = new BannerView2(getApplicationContext());
		viewPager.setAdapter(new BannerPagerAdapter(new MyAdapter()));
		viewPager.setOnPageChangeListener(new MyListener());
		viewPager.setBackgroundColor(Color.WHITE);
		viewPager.setLayoutParams(new AbsListView.LayoutParams(
				AbsListView.LayoutParams.MATCH_PARENT, 100));

		apps = (ListView) findViewById(R.id.app_list);
		rawAdapter = new RawAdapter(apks);
		gridAdapter = new GridAdapter<RawAdapter>(getApplicationContext(),
				rawAdapter);
		gridAdapter.setNumColumns(4);
		gridAdapter.setOnItemClickListener(new OnGridItemClickListener() {

			@Override
			public void onItemClick(int pos, int realPos) {
				// 这里的Listener需要自己扩充，可以callback你需要的内容。
				Log.d("ListGridActivity", pos + "/" + realPos);
			}
		});

		apps.addHeaderView(viewPager);
		apps.setAdapter(gridAdapter);
		viewPager.startAutoScroll();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		viewPager.stopAutoScroll();
	}
	
	private class RawHeaderAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	private class RawAdapter extends BaseAdapter {

		private List<Apks> items;

		public RawAdapter(List<Apks> items) {
			this.items = items;
		}

//		@Override
//		public int getItemViewType(int position) {
//			return (int) items.get(position).getType();
//		}
//
//		@Override
//		public int getViewTypeCount() {
//			return 2;
//		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
//			if(getItemViewType(position) == 0) {
//				HeaderViewHolder holder;
//		        if (convertView == null || convertView.getTag() instanceof ViewHolder) {
//		            convertView = LayoutInflater.from(getApplicationContext())
//							.inflate(R.layout.item_grid_header, parent, false);
//		            holder = new HeaderViewHolder();
//		            holder.textView = (TextView) convertView.findViewById(R.id.header);
//		            convertView.setTag(holder);
//		        } else {
//		            holder = (HeaderViewHolder) convertView.getTag();
//		        }
//
//		        Apks item = (Apks) getItem(position);
//		        // set header text as first char in string
//		        holder.textView.setText(item.getName());
//		        return convertView;
//		        
//			} else {
				ViewHolder holder;
				if (convertView == null) {
					convertView = LayoutInflater.from(getApplicationContext())
							.inflate(R.layout.item_grid_app, parent, false);
					holder = new ViewHolder();
					holder.textView = (TextView) convertView
							.findViewById(R.id.app_name);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				Apks item = (Apks) getItem(position);
				holder.textView.setText(item.getName());
				return convertView;
//			}
			
		}

	}

	protected class HeaderViewHolder {
        public TextView textView;
    }
	
	protected class ViewHolder {
		public TextView textView;
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
			Log.d("ListGridActivity", "destroyItem " + position);
			// ((ViewPager) viewPager).removeView((View) content);
			// cost too many memory
		}

		@Override
		public Object instantiateItem(View viewPager, int position) {
			Log.d("ListGridActivity", "instantiateItem " + position);
			View content = mListViews.get(position % mListViews.size());
			if (content.getParent() != null) {
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
			Log.d("ListGridActivity", "select page " + c_id);
		}

	}

}
