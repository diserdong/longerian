package so.mp3.http.response;


import so.mp3.http.SougouResponse;

public class DownloadLinkResponse extends SougouResponse {

	private String link;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
