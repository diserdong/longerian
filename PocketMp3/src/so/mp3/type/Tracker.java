package so.mp3.type;

import android.net.Uri;

public interface Tracker {
	
	public Uri getUri();
	public String getTitle();
	public String getArtist();
	public String getAlbum();
	public int getDuration();
	public String getSize();

}
