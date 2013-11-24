package cc.icefire.market.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import cc.icefire.market.R;
import cc.icefire.market.model.AppListType;
import cc.icefire.market.model.AppOrGame;
import cc.icefire.market.view.AppListView;
import cc.icefire.market.view.TitleBar;

public class SearchActivity extends BaseActivity {

	private TitleBar titleBar;
	private EditText searchBox;
	private ImageView searchBtn;
	private AppListView searchResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_app_list);
		initTitleBar();
		initSearchUi();
		showSoftInputWindow();
	}
	
	private void initTitleBar() {
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.hideSearchBtn();
		titleBar.setTitle(R.string.title_search);
	}
	
	private void initSearchUi() {
		searchBox = (EditText) findViewById(R.id.search_box);
		searchBtn = (ImageView) findViewById(R.id.search_btn);
		searchResult = (AppListView) findViewById(R.id.search_result);
		searchBtn.setOnClickListener(onSearchClicked);
	}

	private OnClickListener onSearchClicked = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String query = searchBox.getEditableText().toString().trim();
			if(!TextUtils.isEmpty(query)) {
				requestSearch(query);
				hideSoftInputWindow();
			}
		}
	};
	
	private void requestSearch(String query) {
		searchResult.requestSearchedApp(AppListType.SEARCH, AppOrGame.ANY, query);
	}
	
	private void showSoftInputWindow() {
		searchBox.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
				inputManager.showSoftInput(searchBox, InputMethodManager.SHOW_IMPLICIT);
			}
		}, 500);
	}
	
	private void hideSoftInputWindow() {
		InputMethodManager imm = (InputMethodManager) getSystemService(
				Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
	}
	
}
