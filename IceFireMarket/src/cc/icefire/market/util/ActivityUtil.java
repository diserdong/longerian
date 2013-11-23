package cc.icefire.market.util;

import cc.icefire.market.activity.SearchActivity;
import android.content.Context;
import android.content.Intent;

public class ActivityUtil {

	public static void gotoSearchActivity(Context context) {
		Intent intent = new Intent(context, SearchActivity.class);
		context.startActivity(intent);
	}
	
}
