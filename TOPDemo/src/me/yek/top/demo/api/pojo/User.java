package me.yek.top.demo.api.pojo;

import java.util.Date;

import me.yek.top.demo.api.client.TaobaoTypePojo;


public class User extends TaobaoTypePojo {

	private UserCredit buyerCredit;
	private Date created;
	private Date lastVisit;
	private Location location;
	private String nick;
	private UserCredit sellerCredit;
	private String type;
	private String uid;
	private Long userId;
	public UserCredit getBuyerCredit() {
		return buyerCredit;
	}
	public void setBuyerCredit(UserCredit buyerCredit) {
		this.buyerCredit = buyerCredit;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getLastVisit() {
		return lastVisit;
	}
	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public UserCredit getSellerCredit() {
		return sellerCredit;
	}
	public void setSellerCredit(UserCredit sellerCredit) {
		this.sellerCredit = sellerCredit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
