package me.yek.top.demo.api.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import me.yek.top.demo.api.client.TaobaoParser;
import me.yek.top.demo.api.client.TaobaoResponse;
import me.yek.top.demo.api.pojo.Location;
import me.yek.top.demo.api.pojo.User;
import me.yek.top.demo.api.pojo.UserCredit;
import me.yek.top.demo.api.response.UserGetResponse;
import me.yek.top.demo.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author Longer
 * here only parse unbinded user's response
 *
 * unbinded user response
 {
    "user_get_response": {
        "user": {
            "buyer_credit": {
                "good_num": 56,
                "level": 3,
                "score": 56,
                "total_num": 56
            },
            "created": "2008-10-05 13:35:29",
            "last_visit": "2011-12-17 19:08:44",
            "location": {},
            "nick": "一曲肠断",
            "seller_credit": {
                "good_num": 0,
                "level": 0,
                "score": 0,
                "total_num": 0
            },
            "type": "C",
            "uid": "b2482b750a37337ff3fa26facaaa4b5f",
            "user_id": 136333852
        }
    }
}

binded user response
{
    "user_get_response": {
        "user": {
            "alipay_account": "xlhongultimate@gmail.com",
            "alipay_bind": "bind",
            "alipay_no": "20880027741070910156",
            "buyer_credit": {
                "good_num": 56,
                "level": 3,
                "score": 56,
                "total_num": 56
            },
            "consumer_protection": false,
            "created": "2008-10-05 13:35:29",
            "email": "knight.st@163.com",
            "last_visit": "2011-12-17 19:08:44",
            "location": {},
            "nick": "一曲肠断",
            "seller_credit": {
                "good_num": 0,
                "level": 0,
                "score": 0,
                "total_num": 0
            },
            "status": "normal",
            "type": "C",
            "uid": "b2482b750a37337ff3fa26facaaa4b5f",
            "user_id": 136333852
        }
    }
}
 */
public class UserGetParser extends TaobaoParser {

	@Override
	public TaobaoResponse parse(String strResponse) {
		UserGetResponse resp = new UserGetResponse();
		if(strResponse != null) {
			LogUtil.d(this.getClass().getName(), strResponse);
			try {
				JSONObject jsonResponse = new JSONObject(strResponse).getJSONObject("user_get_response");
				JSONObject jsonUser = jsonResponse.getJSONObject("user");
				User user = new User();
				UserCredit buyerCredit = new UserCredit();
				JSONObject jsonBuyerCredit = jsonUser.getJSONObject("buyer_credit");
				buyerCredit.setGoodNum(jsonBuyerCredit.getInt("good_num"));
				buyerCredit.setLevel(jsonBuyerCredit.getInt("level"));
				buyerCredit.setScore(jsonBuyerCredit.getInt("score"));
				buyerCredit.setTotalNum(jsonBuyerCredit.getInt("total_num"));
				user.setBuyerCredit(buyerCredit);
				
				user.setCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonUser.getString("created")));
				user.setLastVisit(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonUser.getString("last_visit")));
				user.setLocation(new Location());//TODO parse this if necessary
				user.setNick(jsonUser.getString("nick"));
				
				UserCredit sellerCredit = new UserCredit();
				JSONObject jsonSellerCredit = jsonUser.getJSONObject("seller_credit");
				sellerCredit.setGoodNum(jsonSellerCredit.getInt("good_num"));
				sellerCredit.setLevel(jsonSellerCredit.getInt("level"));
				sellerCredit.setScore(jsonSellerCredit.getInt("score"));
				sellerCredit.setTotalNum(jsonSellerCredit.getInt("total_num"));
				user.setSellerCredit(sellerCredit);
				
				user.setType(jsonUser.getString("type"));
				user.setUid(jsonUser.getString("uid"));
				user.setUserId(jsonUser.getLong("user_id"));
				
				resp.setUser(user);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return resp;
	}

}
