package cc.icefire.market.util;

import android.content.Context;
import android.content.Intent;
import cc.icefire.market.activity.CategoryAppListActivity;
import cc.icefire.market.activity.SearchActivity;
import cc.icefire.market.model.Category;

public class ActivityUtil {
	
	public static final String EXTRA_CATEGORY = "category";

	public static void gotoSearchActivity(Context context) {
		Intent intent = new Intent(context, SearchActivity.class);
		context.startActivity(intent);
	}
	
	public static void gotoCategoryAppListActivity(Context context, Category item) {
		Intent intent = new Intent(context, CategoryAppListActivity.class);
		intent.putExtra(EXTRA_CATEGORY, item);
		context.startActivity(intent);
	}
	
}
