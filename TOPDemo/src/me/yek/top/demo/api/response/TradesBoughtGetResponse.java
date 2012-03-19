package me.yek.top.demo.api.response;

import java.util.ArrayList;

import me.yek.top.demo.api.client.TaobaoResponse;
import me.yek.top.demo.api.pojo.Trade;


public class TradesBoughtGetResponse extends TaobaoResponse {

	private ArrayList<Trade> trades;
	private int totalResults;
	public ArrayList<Trade> getTrades() {
		return trades;
	}
	public void setTrades(ArrayList<Trade> trades) {
		this.trades = trades;
	}
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	
}
