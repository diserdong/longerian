package me.yek.top.demo.api.response;

import java.util.ArrayList;

import me.yek.top.demo.api.client.TaobaoResponse;
import me.yek.top.demo.api.pojo.CollectItem;


public class FavoriteSearchResponse extends TaobaoResponse {

	private ArrayList<CollectItem> collectItems;
	private int totalResults;
	public ArrayList<CollectItem> getCollectItems() {
		return collectItems;
	}
	public void setCollectItems(ArrayList<CollectItem> collectItems) {
		this.collectItems = collectItems;
	}
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	
}
