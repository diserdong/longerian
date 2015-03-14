package me.longerian.abcandroid.customuri;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class CustomUri extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String customUri = "msg://tencent.com/?content=the is the detail";
        Uri uri = Uri.parse(customUri);
        String query = uri.getQueryParameter("content");
        Log.d("longer", query);
	}
}
