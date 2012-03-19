package me.yek.top.demo.api.response;

import me.yek.top.demo.api.client.TaobaoResponse;
import me.yek.top.demo.api.pojo.Trade;


public class TradeFullInfoGetResponse extends TaobaoResponse {

	private Trade trade;

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	
}
