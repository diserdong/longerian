package cc.icefire.market.api.parser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cc.icefire.market.api.response.TrendResponse;
import cc.icefire.market.model.Trend;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class TrendParser extends JsonParseHandler<TrendResponse> {

	private TrendResponse trend = new TrendResponse();
	
	@Override
	public TrendResponse getModel() {
		return trend;
	}

	@Override
	public void parse(String inputSource) {
		try {
			Type type = new TypeToken<List<Trend>>(){}.getType();
			List<Trend> trends = JsonUtil.fromJsonArray(inputSource, type);
			trend.setTrends(trends);
			trend.setSuccess(true);
		} catch (JsonSyntaxException e) {
			trend.setSuccess(false);
			e.printStackTrace();
		} catch (JsonParseException e) {
			trend.setSuccess(false);
			e.printStackTrace();
		}
	}

}
