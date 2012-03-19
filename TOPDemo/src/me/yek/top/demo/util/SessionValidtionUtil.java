package me.yek.top.demo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import me.yek.top.demo.api.client.TaobaoClient;
import me.yek.top.demo.api.pojo.TopAuthorizationCallback;
import me.yek.top.demo.api.pojo.TopParameters;


import android.util.Base64;
import android.util.Log;

/**
 * @author Longer
12-29 15:45:57.139: D/longer(19598): top_appkey = 12451689
12-29 15:45:57.139: D/longer(19598): top_parameters = ZXhwaXJlc19pbj04NjQwMCZpZnJhbWU9MSZyZV9leHBpcmVzX2luPTE1NTUyMDAwJnJlZnJlc2hfdG9rZW49NjEwMDQwMDkxYzI4NDE2NTE2MzI0ZDJlYjNjZTY4MDdkOGNlMDYxOGVmYWE0OTcxMzYzMzM4NTImdHM9MTMyNTE0NDUyNjAyNyZ2aXNpdG9yX2lkPTEzNjMzMzg1MiZ2aXNpdG9yX25pY2s95LiA5puy6IKg5pat
12-29 15:45:57.139: D/longer(19598): top_session = 6100100d1d2348e88bfcc631ff916feb79afaeffa45f1b2136333852
12-29 15:45:57.139: D/longer(19598): top_sign = ABFTGwBngNAGyRjoEZ/H1g==

12-29 15:45:57.217: D/longer(19598): prased top_parameters is: ts = 1325144526027
12-29 15:45:57.217: D/longer(19598): iframe = 1
12-29 15:45:57.217: D/longer(19598): visitor_id = 136333852
12-29 15:45:57.217: D/longer(19598): visitor_nick = 一曲肠断
12-29 15:45:57.217: D/longer(19598): visitor_role = null
12-29 15:45:57.217: D/longer(19598): expires_in = 86400
12-29 15:45:57.217: D/longer(19598): re_expires_in = 15552000
12-29 15:45:57.217: D/longer(19598): sub_visitor_id = null
12-29 15:45:57.217: D/longer(19598): sub_visitor_nick = null

 */
public class SessionValidtionUtil {
	
	/**
	 * 验证规则：
	 * 1.验证签名是否合法；判断base64(md5(top_appkey+top_parameters+top_session+app_secret))
	 * 2.top_parameters中解析出所需的上下文参数；验证时间戳是否在应用允许的误差范围；
	 * 3.得到上下文参数及对应的session（即上面的top_session） ， 就可以进行OpenAPI调用；
	 * @param top
	 * @return
	 */
	public static boolean validateTopAuthorizationCallback(TopAuthorizationCallback top) {
		//step 1
		boolean isSignValide = validateSign(top.getTopSign(), 
				top.getTopAppkey() + top.getTopParameters() + top.getTopSession(), 
				TaobaoClient.appSecret);
		if(!isSignValide) {
			Log.d("longer", "签名验证失败");
			return false;
		}
		//step 2
		boolean isTimeValide = validateTs(top.getTopParameters());
		if(!isTimeValide) {
			Log.d("longer", "回调参数过期失败");
			return false;
		}
		if(isSignValide && isTimeValide) {
			return true;
		} else {
			Log.d("longer", "未知错误");
			return false;
		}
	}

	/**
     * 签名运算
     * @param parameter
     * @param secret
     * @return
     */
    private static String sign(String parameter, String secret) {
       // 对参数+密钥做MD5运算
       MessageDigest md = null;
       try {
           md = MessageDigest.getInstance("MD5");
       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       }
       byte[] digest = md.digest((parameter + secret).getBytes());
       // 对运算结果做BASE64运算并加密
       return Base64.encodeToString(digest, Base64.DEFAULT).trim();
    }
  
    /**
     * 验证签名
     * @param sign
     * @param parameter
     * @param secret
     * @return
     */
    private static boolean validateSign(String sign, String parameter,
           String secret) {
    	//注意，这个parameter并不就是上面的top_paramater，而是指的待签名验证的参数，即上面的top_appkey+top_parameter+top_session
    	return sign!= null && parameter != null && secret != null && sign.equals(sign(parameter, secret));
    }

    public static TopParameters convertMap2TopParameters(Map<String, String> map) {
    	TopParameters topParameters = new TopParameters();
    	Iterator iterator = map.entrySet().iterator();
    	while(iterator.hasNext()) {
    		Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            Log.d("longer", key + " / " + value);
            if(key.equals("ts")) {
            	topParameters.setTs(Long.valueOf(value));
            } else if(key.equals("iframe")) {
            	topParameters.setIframe(value);
            } else if(key.equals("visitor_id")) {
            	topParameters.setVisitorId(value);
            } else if(key.equals("visitor_nick")) {
            	topParameters.setVisitorNick(value);
            } else if(key.equals("visitor_role")) {
            	topParameters.setVisitorRole(value);
            } else if(key.equals("expires_in")) {
            	topParameters.setExpiresIn(Long.valueOf(value));
            } else if(key.equals("re_expires_in")) {
            	topParameters.setReExpiresIn(Long.valueOf(value));
            } else if(key.equals("sub_visitor_id")) {
            	topParameters.setSubVisitorId(value);
            } else if(key.equals("sub_visitor_nick")) {
            	topParameters.setSubVisitorNick(value);
            } else if(key.equals("refresh_token")) {
            	topParameters.setRefreshToken(value);
            }
    	}
    	Log.d("longer", "prased top_parameters is: " + topParameters.toString());
    	return topParameters;
    }

    /**
     * 把经过BASE64编码的字符串转换为Map对象
     * @param str
     * @return
     * @throws Exception
     */
    public static Map<String, String> convertBase64String2Map(String str) {
        if (str == null)
             return null;
         String keyvalues = null;
         try {
             keyvalues = new String(Base64.decode(URLDecoder.decode(str, "utf-8").getBytes("utf-8"), Base64.DEFAULT));
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }
         String[] keyvalueArray = keyvalues.split("\\&");
         Map<String, String> map = new HashMap<String, String>();
         for (String keyvalue : keyvalueArray) {
             String[] s = keyvalue.split("\\=");
             if (s == null || s.length != 2)
                 return null;
             map.put(s[0], s[1]);
         }
         return map;
     }
    
    /**
     * @param topParameters 回调url中的参数
     * @return
     */
    private static boolean validateTs(String topParameters) {
    	TopParameters topParameter = convertMap2TopParameters(convertBase64String2Map(topParameters));
		long ts = topParameter.getTs();
		long now = System.currentTimeMillis();
		return Math.abs(ts - now) < 5 * 60 * 1000; 
    }
    
}
