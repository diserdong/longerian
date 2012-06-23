package so.mp3.http.response;


import java.util.ArrayList;
import java.util.List;

import so.mp3.http.SougouResponse;
import so.mp3.type.Mp3;

public class SearchResponse extends SougouResponse {

	private List<Mp3> mp3s = new ArrayList<Mp3>();

	public List<Mp3> getMp3s() {
		return mp3s;
	}

	public void setMp3s(List<Mp3> mp3s) {
		this.mp3s = mp3s;
	}
	
}
