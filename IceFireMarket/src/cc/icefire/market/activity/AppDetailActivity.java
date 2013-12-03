package cc.icefire.market.activity;

import android.os.Bundle;
import cc.icefire.market.R;
import cc.icefire.market.model.BasicAppItem;
import cc.icefire.market.util.ActivityUtil;
import cc.icefire.market.view.AppDetailView;
import cc.icefire.market.view.TitleBar;

public class AppDetailActivity extends BaseActivity {

	private BasicAppItem item;
	private TitleBar titleBar;
	private AppDetailView content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_detail);
		parseAppItem();
		initTitleBar();
		initContentView();
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

	private void parseAppItem() {
		item = getIntent().getParcelableExtra(ActivityUtil.EXTRA_APP);
	}

	private void initTitleBar() {
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.setTitle(R.string.app_detail);
	}
	
	private void initContentView() {
		content = (AppDetailView) findViewById(R.id.content);
		content.bindBasicAppItem(item);
	}
	
	private void registerListener() {
		content.onResume();
	}
	
	private void unregisterListener() {
		content.onPause();
	}
	
}
