package me.yek.top.demo.api.request;

import java.util.TreeMap;

import me.yek.top.demo.api.client.TaobaoRequest;

import android.content.Context;


public class UserGetRequest extends TaobaoRequest {

	private String buyerNick;
	
	public UserGetRequest() {
		super();
	}

	public UserGetRequest(Context ctx) {
		super(ctx);
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	@Override
	public TreeMap<String, String> getAppParam() {
		TreeMap<String, String> appParam = new TreeMap<String, String>();
		appParam.put("fields","user_id,uid,nick,sex,buyer_credit,seller_credit,location,created,last_visit,birthday,type,status,alipay_no,alipay_account,alipay_account,email,consumer_protection,alipay_bind");//需要获取的字段
		appParam.put("nick", buyerNick);
		return appParam;
	}

	@Override
	public String getMethod() {
		return "taobao.user.get";
	}

}
