package so.mp3.http.response;


import java.util.ArrayList;
import java.util.List;

import so.mp3.http.SougouResponse;
import so.mp3.type.OnlineTrack;

public class SearchResponse extends SougouResponse {

	private List<OnlineTrack> mp3s = new ArrayList<OnlineTrack>();

	public List<OnlineTrack> getMp3s() {
		return mp3s;
	}

	public void setMp3s(List<OnlineTrack> mp3s) {
		this.mp3s = mp3s;
	}
	
}
