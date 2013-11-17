package cc.icefire.market.activity;

import android.os.Bundle;
import cc.icefire.market.R;
import cc.icefire.market.view.TitleBar;

public class SearchActivity extends BaseActivity {

	private TitleBar titleBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);
		initTitleBar();
	}
	
	protected void initTitleBar() {
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.hideSearchBtn();
		titleBar.setTitle(R.string.title_search);
	}

}
