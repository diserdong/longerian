package cc.icefire.market.api.parser;

import cc.icefire.market.api.response.TestResponse;
import cc.icefire.market.util.ILog;

public class TestParser extends JsonParseHandler<TestResponse> {

	@Override
	public void parse(String inputSource) {
		ILog.d("Longer", inputSource);
	}

	@Override
	public TestResponse getModel() {
		return new TestResponse();
	}

}
