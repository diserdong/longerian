package me.longerian.abcandroid.resourceuri;

import me.longerian.abcandroid.R;
import android.net.Uri;
import android.util.Log;

public class ResourceUri {

	public static void resUri() {
		Uri fileUri = Uri.parse("android.resource://me.longerian.abcandroid/" + R.drawable.tencent_q);
		Log.d("URIFILE", fileUri.toString());
	}
	
}
