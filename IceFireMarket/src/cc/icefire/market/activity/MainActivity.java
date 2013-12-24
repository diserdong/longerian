package cc.icefire.market.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import cc.icefire.market.IceFireApplication;
import cc.icefire.market.R;
import cc.icefire.market.view.TabView;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; getApplicationContext() adds items to the action
		// bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_exit:
			IceFireApplication.getInstance().exit();
			break;

		default:
			break;
		}
		return true;
	}

	private void initTabs() {
		TabHost tabHost = getTabHost();

		TabView view = null;

		view = new TabView(getApplicationContext());
		view.setIcon(R.drawable.home_menu_home_normal,
				R.drawable.home_menu_home_selected);
		TabSpec index = tabHost.newTabSpec("Index");
		index.setIndicator(view);
		Intent indexIntent = new Intent(getApplicationContext(),
				IndexActivity.class);
		indexIntent.putExtra("label", "Index");
		index.setContent(indexIntent);

		view = new TabView(getApplicationContext());
		view.setIcon(R.drawable.home_menu_category_normal,
				R.drawable.home_menu_category_selected);
		TabSpec app = tabHost.newTabSpec("App");
		app.setIndicator(view);
		Intent appIntent = new Intent(getApplicationContext(),
				AppListActivity.class);
		appIntent.putExtra("label", "App");
		app.setContent(appIntent);

		view = new TabView(getApplicationContext());
		view.setIcon(R.drawable.home_menu_category_normal,
				R.drawable.home_menu_category_selected);
		TabSpec game = tabHost.newTabSpec("Game");
		game.setIndicator(view);
		Intent gameIntent = new Intent(getApplicationContext(),
				GameListActivity.class);
		gameIntent.putExtra("label", "Game");
		game.setContent(gameIntent);
		
		view = new TabView(getApplicationContext());
		view.setIcon(R.drawable.home_menu_category_normal,
				R.drawable.home_menu_category_selected);
		TabSpec category = tabHost.newTabSpec("Category");
		category.setIndicator(view);
		Intent categoryIntent = new Intent(getApplicationContext(),
				CategoryActivity.class);
		categoryIntent.putExtra("label", "Category");
		category.setContent(categoryIntent);

		view = new TabView(getApplicationContext());
		view.setIcon(R.drawable.home_menu_more_normal,
				R.drawable.home_menu_more_selected);
		TabSpec setting = tabHost.newTabSpec("Setting");
		setting.setIndicator(view);
		Intent settingIntent = new Intent(getApplicationContext(),
				ManagementActivity.class);
		settingIntent.putExtra("label", "Setting");
		setting.setContent(settingIntent);

		tabHost.addTab(index);
		tabHost.addTab(app);
		tabHost.addTab(game);
		tabHost.addTab(category);
		tabHost.addTab(setting);

		tabHost.setCurrentTab(0);
	}

}
