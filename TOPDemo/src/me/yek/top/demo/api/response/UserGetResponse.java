package me.yek.top.demo.api.response;

import me.yek.top.demo.api.client.TaobaoResponse;
import me.yek.top.demo.api.pojo.User;


public class UserGetResponse extends TaobaoResponse {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
