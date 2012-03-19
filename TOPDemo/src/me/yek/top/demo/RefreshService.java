package me.yek.top.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.TreeMap;

import me.yek.top.demo.api.client.TaobaoClient;
import me.yek.top.demo.api.pojo.TokenRefreshedResponse;
import me.yek.top.demo.util.APIUtil;
import me.yek.top.demo.util.LogUtil;
import me.yek.top.demo.util.PreferenceUtil;
import me.yek.top.demo.util.RefreshTokenUtil;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


public class RefreshService extends IntentService {

	private static final String freshUrl = "http://container.api.taobao.com/container/refresh";
	
	public RefreshService() {
		super("RefreshService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		SharedPreferences pref = getSharedPreferences(PreferenceUtil.PREF, Context.MODE_PRIVATE);
		long now = System.currentTimeMillis() / 1000;
		long expiresIn = PreferenceUtil.getSaftyExpiresTime(pref);
		long reExpiresIn = PreferenceUtil.getSaftyReExpiresTime(pref);
		if(now > expiresIn || now > reExpiresIn) {
			//FIXME 暂时未考虑用户调整时间、更改时区引起的bug
			//重新让用户授权
			Intent authorizationIntent = new Intent(getApplicationContext(), UserAuthorizationActivity.class);
			authorizationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(authorizationIntent);
			stopSelf();
		} else {
			refreshToken();
		}
	}
	
	private void refreshToken() {
		SharedPreferences pref = getSharedPreferences(PreferenceUtil.PREF, Context.MODE_PRIVATE);
		String sessionKey = PreferenceUtil.getSession(pref);
		String refreshToken = PreferenceUtil.getRefreshToken(pref);
		if(!"".equals(sessionKey)) {
			if("".equals(refreshToken)) {
				refreshToken = sessionKey;
			}
			TreeMap<String,String> signParams = new TreeMap<String, String>();       
			signParams.put("appkey", TaobaoClient.appkey);
			signParams.put("refresh_token", refreshToken);
			signParams.put("sessionkey", sessionKey);
			try {
				String sign = RefreshTokenUtil.md5Signature(signParams, TaobaoClient.appSecret).toUpperCase();
				String signEncoder = URLEncoder.encode(sign,"utf-8");
				String appkeyEncoder = URLEncoder.encode(TaobaoClient.appkey,"utf-8");
				String refreshTokenEncoder = URLEncoder.encode(refreshToken,"utf-8");
				String sessionkeyEncoder = URLEncoder.encode(sessionKey,"utf-8");
				String content = "appkey=" + appkeyEncoder
							+ "&refresh_token=" + refreshTokenEncoder
							+ "&sessionkey=" + sessionkeyEncoder
							+ "&sign=" + signEncoder;
				/**
				 * 
					{
					    "sign": "28D31FD25550B45CCD4A30A9AD428B85",
					    "re_expires_in": "15550666", //基本不变
					    "top_session": "6101202c4c18dd340f8e4a59eb8f3626a73edb102bb139b136333852",//新的session key
					    "expires_in": "86400",//不变
					    "refresh_token": "6101d02f3d16a52e340118e8cf946fb9ca900769d3b2883136333852"//新的refresh token
					}
				 */
				LogUtil.d(this.getClass().getName(), content);
				String result = APIUtil.getResult(freshUrl, content);
				if(result != null) {
					LogUtil.d(this.getClass().getName(), result);
					TokenRefreshedResponse resp = RefreshTokenUtil.convertJson2Obj(new JSONObject(result));
					PreferenceUtil.setSession(pref, resp.getTopSession());
					PreferenceUtil.setExpiresIn(pref, Long.valueOf(resp.getExpiresIn()));
					PreferenceUtil.setRefreshToken(pref, resp.getRefreshToken());
				} else {
					LogUtil.d(this.getClass().getName(), "there is something wrong with networking connection!!!");
				}
			} catch(UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				stopSelf();
			}
		} else {
			LogUtil.d(this.getClass().getName(), "no session key");
			stopSelf();
		}
	}
}
