package me.yek.top.demo.util;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import me.yek.top.demo.api.pojo.TokenRefreshedResponse;

import org.json.JSONException;
import org.json.JSONObject;


public class RefreshTokenUtil {

	 /**
     * 新的md5签名，尾部放secret。
     * @param secret 分配给您的APP_SECRET
     */
     public static String md5Signature(TreeMap<String, String> params, String secret) {
             String result = null;
             StringBuffer orgin = getBeforeSign(params, new StringBuffer());
             if (orgin == null)
            	 return result;
             orgin.append(secret);
             try {
                     MessageDigest md = MessageDigest.getInstance("MD5");
                     result = APIUtil.byte2hex(md.digest(orgin.toString().getBytes("utf-8")));
             } catch (Exception e) {
                     throw new java.lang.RuntimeException("sign error !");
             }
         return result;
     }
     
     /**
      * 添加参数的封装方法
      */
      private static StringBuffer getBeforeSign(TreeMap<String, String> params, StringBuffer orgin) {
              if (params == null)
                      return null;
              Map<String, String> treeMap = new TreeMap<String, String>();
              treeMap.putAll(params);
              Iterator<String> iter = treeMap.keySet().iterator();
              while (iter.hasNext()) {
                      String name = (String) iter.next();
                      orgin.append(name).append(params.get(name));
              }
              return orgin;
      }
      
      /**
       * 			{
					    "sign": "28D31FD25550B45CCD4A30A9AD428B85",
					    "re_expires_in": "15550666", //基本不变
					    "top_session": "6101202c4c18dd340f8e4a59eb8f3626a73edb102bb139b136333852",//新的session key
					    "expires_in": "86400",//不变
					    "refresh_token": "6101d02f3d16a52e340118e8cf946fb9ca900769d3b2883136333852"//新的refresh token
					}
     * @param jsonObj
     * @return
     * @throws JSONException 
     */
    public static TokenRefreshedResponse convertJson2Obj(JSONObject jsonObj) throws JSONException {
    	TokenRefreshedResponse resp = new TokenRefreshedResponse();
    	resp.setSign(jsonObj.getString("sign"));
    	resp.setReExpiresIn(jsonObj.getString("re_expires_in"));
    	resp.setTopSession(jsonObj.getString("top_session"));
    	resp.setExpiresIn(jsonObj.getString("expires_in"));
    	resp.setRefreshToken(jsonObj.getString("refresh_token"));
    	return resp;
      }
     
}
