package me.yek.top.demo;

import me.yek.top.demo.fragments.UserAuthorizationFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;


public class UserAuthorizationActivity extends FragmentActivity {
	
	public static int REQUEST_CODE = 0x100a;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		UserAuthorizationFragment authorization = new UserAuthorizationFragment();
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, authorization).commit();
	}
	
}
