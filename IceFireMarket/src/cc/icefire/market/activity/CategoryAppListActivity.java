package cc.icefire.market.activity;

import android.content.Intent;
import android.os.Bundle;
import cc.icefire.market.R;
import cc.icefire.market.model.AppListType;
import cc.icefire.market.model.AppOrGame;
import cc.icefire.market.model.Category;
import cc.icefire.market.util.ActivityUtil;
import cc.icefire.market.view.AppListView;
import cc.icefire.market.view.TitleBar;

public class CategoryAppListActivity extends BaseActivity {

	private Category item;
	private TitleBar titleBar;
	private AppListView children;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_app_list);
		parseCategoryItem();
		initTitleBar(item.getName());
		initListUi();
		requestChildren(AppOrGame.fromInt(item.getAppOrGame()), item.getId());
	}
	
	private void parseCategoryItem() {
		Intent launchIntent = getIntent();
		item = launchIntent.getParcelableExtra(ActivityUtil.EXTRA_CATEGORY);
	}
	
	private void initTitleBar(String title) {
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.hideSearchBtn();
		titleBar.setTitle(title);
	}
	
	private void initListUi() {
		children = (AppListView) findViewById(R.id.children);
	}

	private void requestChildren(AppOrGame appOrGame, int categoryId) {
		children.requestCategoryApp(AppListType.CATEGORY, appOrGame, categoryId);
	}
	
}
