package so.mp3.http.response;


import java.util.ArrayList;
import java.util.List;

import so.mp3.http.SougouResponse;
import so.mp3.type.OnlineMp3;

public class SearchResponse extends SougouResponse {

	private List<OnlineMp3> mp3s = new ArrayList<OnlineMp3>();

	public List<OnlineMp3> getMp3s() {
		return mp3s;
	}

	public void setMp3s(List<OnlineMp3> mp3s) {
		this.mp3s = mp3s;
	}
	
}
