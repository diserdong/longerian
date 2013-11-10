package cc.icefire.market.api.response;

import crow.api.ApiResponse;

public class BaseApiResponse extends ApiResponse {

	private String status;	
	private String message;
	private String weblogid;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getWeblogid() {
		return weblogid;
	}
	public void setWeblogid(String weblogid) {
		this.weblogid = weblogid;
	}
	
}
