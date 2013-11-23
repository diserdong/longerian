package cc.icefire.market.util;

import cc.icefire.market.activity.SearchActivity;
import cc.icefire.market.model.AppOrGame;
import android.content.Context;
import android.content.Intent;

public class ActivityUtil {

	public static void gotoSearchActivity(Context context) {
		Intent intent = new Intent(context, SearchActivity.class);
		context.startActivity(intent);
	}
	
	public static void gotoCategoryAppListActivity(Context context, int category, AppOrGame appOrGame) {
		
	}
	
}
