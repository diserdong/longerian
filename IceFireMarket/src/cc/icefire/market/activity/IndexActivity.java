package cc.icefire.market.activity;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cc.icefire.market.R;
import cc.icefire.market.model.AppListType;
import cc.icefire.market.model.AppOrGame;
import cc.icefire.market.util.ILog;
import cc.icefire.market.view.AppListView;
import cc.icefire.market.view.SmoothGallery;
import cc.icefire.market.view.TabPageView;
import cc.icefire.market.view.TabPageView.OnPageSelectedListener;
import cc.icefire.market.view.TitleBar;

public class IndexActivity extends BaseActivity {

	private TitleBar titleBar;
	private TabPageView tabPageView;
	private AppListView selectedAppListView;
	private AppListView recommendedAppListView;
	private ImageAdapter adapter;
	private SmoothGallery gallery;
	RelativeLayout galleryLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);
		initTitleBar();
		initTabPageView();
		requestApp(0);
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerListener();
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initTitleBar() {
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.setTitle(R.string.title_index);
	}

	private void initTabPageView() {
		tabPageView = (TabPageView) findViewById(R.id.page_view);
		selectedAppListView = new AppListView(this);
		tabPageView.addPage(getString(R.string.app_list_selected), selectedAppListView);
		recommendedAppListView = new AppListView(this);
		tabPageView.addPage(getString(R.string.app_list_recommended), recommendedAppListView);
		tabPageView.setOnPageSelectedListener(onPageSelected);
		galleryLayout = (RelativeLayout) this.findViewById(R.id.gallery_layout);
		galleryLayout.setVisibility(View.VISIBLE);
		gallery = (SmoothGallery) this.findViewById(R.id.myGallery);
		adapter = new ImageAdapter(IndexActivity.this);
		gallery.setAdapter(adapter);
	}

	private OnPageSelectedListener onPageSelected = new OnPageSelectedListener() {

		@Override
		public void onPageSelected(int position) {
			ILog.d("Index", "select " + position);
			requestApp(position);
		}
	};

	private void requestApp(int position) {
		if (position == 0) {
			selectedAppListView.requestCommonApp(AppListType.SELECTED, AppOrGame.ANY);
		} else if (position == 1) {
			recommendedAppListView.requestCommonApp(AppListType.RECOMMENDEDED, AppOrGame.ANY);
		}
	}

	private void registerListener() {
		selectedAppListView.onResume();
		recommendedAppListView.onResume();
	}

	private void unregisterListener() {
		selectedAppListView.onPause();
		recommendedAppListView.onPause();
	}

	private Integer[] mps = { R.drawable.jk01, R.drawable.jk02, R.drawable.jk03, R.drawable.jk04, R.drawable.jk05 };

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {

			return mps.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView image = new ImageView(mContext);
			image.setImageResource(mps[position]);
			image.setAdjustViewBounds(true);
			image.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			return image;
		}
	}

}
