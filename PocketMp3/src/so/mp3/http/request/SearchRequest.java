package so.mp3.http.request;


import java.util.HashMap;
import java.util.Map;

import so.mp3.http.SougouRequest;

public class SearchRequest extends SougouRequest {

	private String pf = "mp3";
	private String query;
	private int page = 1;
	
	public void setQuery(String query) {
		this.query = query.trim();
	}

	@Override
	public Map<String, String> getAppParam() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pf", pf);
		map.put("query", query);
		map.put("page", page + "");
		return map;
	}

	@Override
	public String getUrl() {
		return "music.so";
	}

}
