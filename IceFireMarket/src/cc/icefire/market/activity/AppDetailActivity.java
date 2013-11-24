package cc.icefire.market.activity;

import android.os.Bundle;
import cc.icefire.market.R;
import cc.icefire.market.util.ActivityUtil;
import cc.icefire.market.util.ILog;

public class AppDetailActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_detail);
		ILog.d("App", getIntent().getParcelableExtra(ActivityUtil.EXTRA_APP).toString());
	}
	
}
