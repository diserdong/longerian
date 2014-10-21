package me.longerian.abcandroid.shop;

import java.util.Arrays;

import me.longerian.abcandroid.R;
import me.longerian.abcandroid.shop.widget.InnerListView;
import me.longerian.abcandroid.shop.widget.InnerScrollView;
import me.longerian.abcandroid.shop.widget.OutterScrollView;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ShopActivity3 extends Activity {

	private String[] txts = new String[] {"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
		"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
		"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
		"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
		"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
		"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu",
		"Apple", "Google", "Amazon", "Intel", "AMD", "Facebook", "Twitter", "Childfire", "MicroSoft", "Pinterest", "Alibaba", "Tencent", "Baidu"};

	private OutterScrollView outterScrollView;
	private InnerScrollView innerScrollView;
	private TextView text;
	private InnerListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop_3);
		outterScrollView = (OutterScrollView) findViewById(R.id.outter);
		innerScrollView = (InnerScrollView) findViewById(R.id.inner);
		text = (TextView) findViewById(R.id.text);
		text.setText(Arrays.toString(txts));
		outterScrollView.setInnerScrollListener(innerScrollView);
		
//		list = (InnerListView) findViewById(R.id.list);
//		list.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.item_company, R.id.text, txts));
//		list.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				Log.d("Longer", "list clicked item " + arg0.getItemAtPosition(arg2) + " at " + arg3);
//			}
//		});
//		list.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1400));
//		outterScrollView.setInnerScrollListener(list);
//		outterScrollView.smoothScrollTo(0, 0);
	}
	
}
