package cc.icefire.market.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import cc.icefire.market.R;
import cc.icefire.market.model.AppOrGame;
import cc.icefire.market.util.ILog;
import cc.icefire.market.view.CategoryListView;
import cc.icefire.market.view.TabPageView;
import cc.icefire.market.view.TabPageView.OnPageSelectedListener;
import cc.icefire.market.view.TitleBar;

public class CategoryActivity extends BaseActivity {

	private TitleBar titleBar;
	private TabPageView tabPageView;
	private CategoryListView appCategoryListView;
	private CategoryListView gameCategoryListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_list);
		initTitleBar();
		initTabPageView();
	}
	
	private void initTitleBar() {
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.setTitle(R.string.title_category);
	}
	
	private void initTabPageView() {
		tabPageView = (TabPageView) findViewById(R.id.page_view);
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		appCategoryListView = (CategoryListView) inflater.inflate(R.layout.category_list, null);
		tabPageView.addPage(getString(R.string.title_app), appCategoryListView);
		gameCategoryListView = (CategoryListView) inflater.inflate(R.layout.category_list, null);
		tabPageView.addPage(getString(R.string.title_game), gameCategoryListView);
		tabPageView.setOnPageSelectedListener(onPageSelected);
	}
	
	private OnPageSelectedListener onPageSelected = new OnPageSelectedListener() {
		
		@Override
		public void onPageSelected(int position) {
			ILog.d("Category", "select " + position);
			if(position == 0) {
				appCategoryListView.startRequest(AppOrGame.APP);
			} else if(position == 1) {
				gameCategoryListView.startRequest(AppOrGame.GAME);
			}
		}
	};
	
}
