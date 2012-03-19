package me.yek.top.demo.api.pojo;

/**
 * @author long
 * 访问fresh url之后，服务器返回的json数据解析出来的对象
 */
public class TokenRefreshedResponse {

	/**
	 * 签名
	 */
	private String sign;
	/**
	 * Refresh token失效时间（long型）
	 * 基本不变
	 */
	private String reExpiresIn;
	/**
	 * 激活后的新sessionkey
	 */
	private String topSession;
	/**
	 * session过期时间（long型）
	 * 不变，24小时
	 */
	private String expiresIn;
	/**
	 * 激活后的新Refresh token
	 */
	private String refreshToken;
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getReExpiresIn() {
		return reExpiresIn;
	}
	public void setReExpiresIn(String reExpiresIn) {
		this.reExpiresIn = reExpiresIn;
	}
	public String getTopSession() {
		return topSession;
	}
	public void setTopSession(String topSession) {
		this.topSession = topSession;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
