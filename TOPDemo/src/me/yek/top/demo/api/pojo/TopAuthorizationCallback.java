package me.yek.top.demo.api.pojo;

/**
 * @author Longer
 * top获得用户授权后返回的认知信息，从应用上下文协议是TOP通过应用的回调地址解析得到，传递的url格式如下
http://callback.com/callback?top_appkey=xxx&top_parameters=xxxxx&top_session=xxxxx&top_sign=xxxxx
 */
public class TopAuthorizationCallback {

	/**
	 * TOP分配给应用的Key
	 */
	private String topAppkey;
	/**
	 * 上下文参数
	 */
	private String topParameters;
	/**
	 * 用户session key
	 */
	private String topSession;
	private String encode;
	/**
	 * 签名，签名规则为base64(md5(top_appkey+top_parameters+top_session+app_secret))
	 */
	private String topSign;
	public String getTopAppkey() {
		return topAppkey;
	}
	public void setTopAppkey(String topAppkey) {
		this.topAppkey = topAppkey;
	}
	public String getTopParameters() {
		return topParameters;
	}
	public void setTopParameters(String topParameters) {
		this.topParameters = topParameters;
	}
	public String getTopSession() {
		return topSession;
	}
	public void setTopSession(String topSession) {
		this.topSession = topSession;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	public String getTopSign() {
		return topSign;
	}
	public void setTopSign(String topSign) {
		this.topSign = topSign;
	}
	@Override
	public String toString() {
		return "top_appkey = " + topAppkey + "\n" 
		+ "top_parameters = " + topParameters + "\n"
		+ "top_session = " + topSession + "\n"
		+ "top_sign = " + topSign;
	}
}
