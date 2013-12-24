package cc.icefire.market.model;

import com.google.gson.annotations.SerializedName;

/**
 * 用户信息，暂时未用到
 * 对应的JSON对象表示（JSON的key为Java字段上方的注解）：
 * {
    "uid": "qwertyuiop",
    "name": "hello",
    "avatar_url": "http://www.xxx.com"
	}
 *
 */
public class User {

	@SerializedName("uid")
	private String uid;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("avatar_url")
	private String avatarUrl;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	
	
}
