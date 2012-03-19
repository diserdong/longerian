package me.yek.top.demo.api.pojo;

/**
 * @author long
 * 授权回调地址http://callback.com/callback?top_appkey=xxx&top_parameters=xxxxx&top_session=xxxxx&top_sign=xxxxx中，
 * top_parameters解码出来的参数构造的对象
 */
public class TopParameters {
    //参数描述  参数类型  是否必需 说明 
	/**
	 * 当前时间戳 整数 Y 时间戳，插件需要对该时间戳进行验证
	 */
	private long ts;
	/**
	 * 应用输出是否在IFRAME中 编码串  Y 取值说明：0-和TOP页面集成在一起;1-输出到IFRAME中
	 */
	private String iframe;
	/**
	 * 当前用户ID 字符串 N 即uid，用户不登录则不传
	 */
	private String visitorId;
	/**
	 * 当前用户昵称 字符串 N 用户不登录则不传
	 */
	private String visitorNick;
	/**
	 * 当前用户角色 编码串 N 5-未登录用户
	 */
	private String visitorRole;
	/**
	 * Session_key 失效时间 整数 N 
	 */
	private long expiresIn;
	/**
	 * 
	 */
	private String refreshToken;
	/**
	 * refresh token 失效时间 整数 N 用于刷新session失效时间(session key type == 5时返回此参数)
	 */
	private long reExpiresIn;
	/**
	 * 子帐号ID 字符串 N 子帐号登录时返回
	 */
	private String subVisitorId;
	/**
	 * 子帐号nick 字符串 N 子帐号登录时返回
	 */
	private String subVisitorNick;
	public long getTs() {
		return ts;
	}
	public void setTs(long ts) {
		this.ts = ts;
	}
	public String getIframe() {
		return iframe;
	}
	public void setIframe(String iframe) {
		this.iframe = iframe;
	}
	public String getVisitorId() {
		return visitorId;
	}
	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}
	public String getVisitorNick() {
		return visitorNick;
	}
	public void setVisitorNick(String visitorNick) {
		this.visitorNick = visitorNick;
	}
	public String getVisitorRole() {
		return visitorRole;
	}
	public void setVisitorRole(String visitorRole) {
		this.visitorRole = visitorRole;
	}
	public long getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
	public long getReExpiresIn() {
		return reExpiresIn;
	}
	public void setReExpiresIn(long reExpiresIn) {
		this.reExpiresIn = reExpiresIn;
	}
	public String getSubVisitorId() {
		return subVisitorId;
	}
	public void setSubVisitorId(String subVisitorId) {
		this.subVisitorId = subVisitorId;
	}
	public String getSubVisitorNick() {
		return subVisitorNick;
	}
	public void setSubVisitorNick(String subVisitorNick) {
		this.subVisitorNick = subVisitorNick;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	@Override
	public String toString() {
		return "ts = " + ts + "\n" 
		+ "iframe = " + iframe + "\n"
		+ "visitor_id = " + visitorId + "\n"
		+ "visitor_nick = " + visitorNick + "\n"
		+ "visitor_role = " + visitorRole + "\n"
		+ "expires_in = " + expiresIn + "\n"
		+ "re_expires_in = " + reExpiresIn + "\n"
		+ "sub_visitor_id = " + subVisitorId + "\n"
		+ "sub_visitor_nick = " + subVisitorNick + "\n"
		+ "refresh_token = " + refreshToken
		;
	}
}
