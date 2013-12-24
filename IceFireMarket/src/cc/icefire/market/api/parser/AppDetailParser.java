package cc.icefire.market.api.parser;

import cc.icefire.market.api.response.AppDetailResponse;
import cc.icefire.market.model.BasicAppItem;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

/**
 * 解析应用详情接口响应，获得一个BasicAppItem对象。服务器返回的数据格式应当为：
 * {
    "id": "1000001",
    "apk_name": "weibo",
    "pkg_name": "com.weibo.android",
    "version_code": 201,
    "version_name": "V2.0.0.1",
    "apk_md5": "1247FACBED5523732434275FEDA",
    "category_id": 2,
    "icon_url": "www.xxx.com",
    "score": 4.5,
    "download_count": 100000000,
    "download_url": "www.downloadxxx.com",
    "desp": "the twitter in China",
    "size": 11023847,
    "screenshots": [
        "www.downloadxxx.com",
        "www.downloadxxx.com",
        "www.downloadxxx.com",
        "www.downloadxxx.com"
    ]
}
 */
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
