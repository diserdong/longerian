package me.yek.top.demo.api.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import me.yek.top.demo.util.APIUtil;
import me.yek.top.demo.util.LogUtil;
import me.yek.top.demo.util.PreferenceUtil;

import android.content.SharedPreferences;


public class TaobaoClient {
	//TTID相关参数
	protected static final String sourceId = "400000";
	protected static final String appName = "Rocawear";
	protected static final String platform = "android";
	protected static final String version = "1.0.0";
	protected static final String partner = "rocawear官方旗舰店";
	
	public static final String sessionKeyUrl = "http://container.open.taobao.com/container"; 
	public static final String apiUrl = "http://gw.api.taobao.com/router/rest";//正式测试环境
	public static final String appkey = "12451689";
	public static final String appSecret = "918b14ef7c138aa72d882663e21e2405";
//	public static String sessionKey = "";

	public static final String TTID = sourceId + "_" + appkey + "_" + "@" + appName + "_" + platform + "_" + version + "_" + partner;
	
	private SharedPreferences sharedPreferences;
	
	public TaobaoClient(SharedPreferences pref) {
		sharedPreferences = pref;
	}
	
	private TreeMap<String, String> getSysParam(TaobaoRequest request) {
		TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();
        apiparamsMap.put("format", "json");
        apiparamsMap.put("method", request.getMethod());
        apiparamsMap.put("sign_method","md5");
        apiparamsMap.put("app_key",appkey);
        apiparamsMap.put("v", "2.0");
        String storedSession = PreferenceUtil.getSession(sharedPreferences);
        if(!storedSession.equals("")) {
        	apiparamsMap.put("session", storedSession); //他用型需要sessionkey
        }
        String timestamp =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        apiparamsMap.put("timestamp",timestamp);
        return apiparamsMap;
	}
	
	public TaobaoResponse excute(TaobaoRequest request, TaobaoParser parser) {
		TreeMap<String, String> apiParam = getSysParam(request);
		apiParam.putAll(request.getAppParam());
		 //生成签名
        String sign = APIUtil.md5Signature(apiParam, appSecret);
        apiParam.put("sign", sign);
        StringBuilder param = new StringBuilder();
        for (Iterator<Map.Entry<String, String>> it = apiParam.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> e = it.next();
            param.append("&").append(e.getKey()).append("=").append(e.getValue());
            LogUtil.d(this.getClass().getName(), e.getKey() + "/" + e.getValue());
        }
        return parser.parse(APIUtil.getResult(apiUrl, param.toString().substring(1)));
	}
	
}
