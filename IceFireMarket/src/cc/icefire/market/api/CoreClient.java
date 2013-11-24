package cc.icefire.market.api;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;

import android.content.Context;
import android.telephony.TelephonyManager;
import cc.icefire.market.api.request.BaseApiRequest;
import cc.icefire.market.api.response.BaseApiResponse;
import crow.api.ApiClient;
import crow.api.parser.Parser;
import crow.cache.Cache;
import crow.util.Util;

public class CoreClient extends
ApiClient<BaseApiContext, BaseApiResponse, BaseApiRequest<? extends BaseApiResponse>>
implements BaseApiConstant, BaseApiContext {

	private Context mContext;

	private Map<String, String> contextMap;

	
	volatile private Map<String, String> mustParam;

	public CoreClient(Context context, Parser parser,
			ThreadPoolExecutor threadPool, Cache cache) {
		super(context, parser, threadPool, cache, new BasicCookieStore());
		mContext = context;
		Map<String, String> map = new HashMap<String, String>();
		contextMap = initContextParam(map);
	}

	/**
	 * 初始化上下文参数
	 * 
	 * @param context
	 * @param param
	 * @return
	 */
	protected Map<String, String> initContextParam(Map<String, String> map) {
		TelephonyManager telManager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		// String sourceid = Util.getSourceId(mContext);
		String language = Locale.getDefault().getLanguage();
		// String imei = telManager.getDeviceId();
		String imei = Util.getDeviceId(mContext);
		String imsi = telManager.getSubscriberId();
		if (imei == null) {
			imei = "";
		}
		// header
		map.put(H_PLATFORM_N, "android");
		map.put(H_UDID, imei);
		map.put(H_CLIENT_V, "1.0.0");
		map.put(H_CONTENT_TYPE, "json");
		map.put(H_MODEL, android.os.Build.MODEL);
		map.put(H_IMSI, imsi);
		map.put(H_IMEI, imei);
		map.put(H_SOURCE, "icefire");
		map.put(H_LANGUAGE, language == null ? "" : language);
		map.put(H_CN_OPERATOR, Util.getCarrier(mContext));
		map.put(H_CONTENET_TYPE, "application/x-www-form-urlencoded");
		return map;
	}

	@Override
	public BaseApiContext getApiContext() {
		return this;
	}

	@Override
	protected Header[] makeRequestHeaders(
			BaseApiRequest<? extends BaseApiResponse> request) {
		HttpPost httpPost = new HttpPost();
		httpPost.setHeader(H_PLATFORM_N, contextMap.get(H_PLATFORM_N));
		httpPost.setHeader(H_UDID, contextMap.get(H_UDID));
		httpPost.setHeader(H_CLIENT_V, contextMap.get(H_CLIENT_V));
		httpPost.setHeader(H_CONTENT_TYPE, contextMap.get(H_CONTENT_TYPE));
		httpPost.setHeader(H_MODEL, contextMap.get(H_MODEL));
		httpPost.setHeader(H_IMSI, contextMap.get(H_IMSI));
		httpPost.setHeader(H_IMEI, contextMap.get(H_IMEI));
		httpPost.setHeader(H_SOURCE, contextMap.get(H_SOURCE));
		httpPost.setHeader(H_LANGUAGE, contextMap.get(H_LANGUAGE));
		httpPost.setHeader(H_CN_OPERATOR, contextMap.get(H_CN_OPERATOR));
		httpPost.setHeader(H_CONTENET_TYPE, contextMap.get(H_CONTENET_TYPE));
		return httpPost.getAllHeaders();
	}

	@Override
	protected Map<String, String> makeRequestParam(
			BaseApiRequest<? extends BaseApiResponse> request) {
		Map<String, String> param = new HashMap<String, String>();
		String timeStamp = request.getTimestamp();
		String appKey = getApiContext().getAppKey();
		param.put(P_AUTHTYPE, "md5");
		param.put(P_SIGN, Util.md5(appKey + timeStamp)); // 签名
		param.put(P_TIMESTAMP, request.getTimestamp()); // 时间戳
		param.put(P_APPKEY, appKey);
		Map<String, String> businessParam = request.getTextParams(getApiContext());
		if(businessParam != null && !businessParam.isEmpty()) {
			param.putAll(businessParam); // 如有与公共参数相同的参数会进行覆盖
		}
		return param;
	}

	/**
	 * APIContext 的方法
	 */
	@Override
	public Context getContext() {
		return mContext;
	}

	/**
	 * APIContext 的方法
	 */
	@Override
	public String getParam(String key) {
		return null;
	}

	@Override
	public String getServer() {
		return SERVER;
	}

	@Override
	public String getAppKey() {
		return APPKEY;
	}

	@Override
	public String getMockServer() {
		return MOCK_SERVER;
	}
	
}
