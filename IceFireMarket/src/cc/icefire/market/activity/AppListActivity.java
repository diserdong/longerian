package cc.icefire.market.activity;

import android.os.Bundle;
import cc.icefire.market.IceFireApplication;
import cc.icefire.market.R;
import cc.icefire.market.api.request.TestRequest;
import cc.icefire.market.api.response.TestResponse;
import cc.icefire.market.util.ILog;
import crow.api.ApiCallback;
import crow.api.ApiException;

public class AppListActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);
		TestRequest test = new TestRequest();
		IceFireApplication.sharedInstance().getHttpEngine().request(test, new TestCallback());
		
	}
	
	
	private class TestCallback implements ApiCallback<TestResponse> {

		@Override
		public void onSuccess(TestResponse response) {
			ILog.d("TestCallback", "on success");
		}

		@Override
		public void onFail(TestResponse response) {
			ILog.d("TestCallback", "on fail");
		}

		@Override
		public void onException(ApiException e) {
			ILog.d("TestCallback", "on exveption " + e.getMessage());
		}
		
	}
	
}
