package me.longerian.abcandroid;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

public class HtmlTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView note = (TextView) findViewById(R.id.hello);
		Log.d("longer", getString(R.string.note_qqlogin1));
		Spanned noteValue = Html.fromHtml(getString(R.string.note_qqlogin1));
//		Spanned noteValue = Html.fromHtml("This is <font color='red'>simple</font>.");
		note.setText(noteValue, TextView.BufferType.SPANNABLE);
	}

}
