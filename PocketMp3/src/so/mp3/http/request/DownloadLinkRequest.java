package so.mp3.http.request;


import java.util.HashMap;
import java.util.Map;

import so.mp3.http.SougouRequest;

public class DownloadLinkRequest extends SougouRequest {

	private String link;
	
	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public Map<String, String> getAppParam() {
		Map<String, String> map = new HashMap<String, String>();
		return map;
	}

	@Override
	public String getUrl() {
		return link;
	}

}
