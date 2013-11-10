package cc.icefire.market.api.parser;

import android.util.Log;
import cc.icefire.market.api.response.TestResponse;

public class TestParser extends JsonParseHandler<TestResponse> {

	@Override
	public void parse(String inputSource) {
		Log.d("longer", inputSource);
	}

	@Override
	public TestResponse getModel() {
		return new TestResponse();
	}

}
