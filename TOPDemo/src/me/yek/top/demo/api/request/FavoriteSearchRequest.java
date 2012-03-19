package me.yek.top.demo.api.request;

import java.util.TreeMap;

import me.yek.top.demo.api.client.TaobaoRequest;


/**
 * @author Longer
 * 
 */
public class FavoriteSearchRequest extends TaobaoRequest {
	
	private String buyerNick;
	
	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	@Override
	public TreeMap<String, String> getAppParam() {
		TreeMap<String, String> appParam = new TreeMap<String, String>();
		appParam.put("user_nick", buyerNick);
		appParam.put("collect_type", "ITEM");
		appParam.put("page_no", "1");
		return appParam;
	}

	@Override
	public String getMethod() {
		return "taobao.favorite.search";
	}

}
