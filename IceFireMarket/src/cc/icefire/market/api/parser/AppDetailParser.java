package cc.icefire.market.api.parser;

import cc.icefire.market.api.response.AppDetailResponse;
import cc.icefire.market.model.BasicAppItem;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

public class AppDetailParser extends JsonParseHandler<AppDetailResponse> {

	private AppDetailResponse appDetail = new AppDetailResponse();
	
	@Override
	public AppDetailResponse getModel() {
		return appDetail;
	}

	@Override
	public void parse(String inputSource) {
		try {
			BasicAppItem appItem = JsonUtil.fromJsonObject(inputSource, BasicAppItem.class);
			appDetail.setAppItem(appItem);
			appDetail.setSuccess(true);
		} catch (JsonSyntaxException e) {
			appDetail.setSuccess(false);
			e.printStackTrace();
		} catch (JsonParseException e) {
			appDetail.setSuccess(false);
			e.printStackTrace();
		}
	}

}
