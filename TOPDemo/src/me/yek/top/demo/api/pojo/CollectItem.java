package me.yek.top.demo.api.pojo;

import me.yek.top.demo.api.client.TaobaoTypePojo;

public class CollectItem extends TaobaoTypePojo {

	private String itemOwnerNick;
	private Long itemNumid;
	private String title;
	public String getItemOwnerNick() {
		return itemOwnerNick;
	}
	public void setItemOwnerNick(String itemOwnerNick) {
		this.itemOwnerNick = itemOwnerNick;
	}
	public Long getItemNumid() {
		return itemNumid;
	}
	public void setItemNumid(Long itemNumid) {
		this.itemNumid = itemNumid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
