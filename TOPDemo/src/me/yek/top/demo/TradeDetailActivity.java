package me.yek.top.demo;

import me.yek.top.demo.fragments.TradeDetailFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;


public class TradeDetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		TradeDetailFragment details = new TradeDetailFragment();
        details.setArguments(getIntent().getExtras().getBundle("tid"));
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
	}

}
